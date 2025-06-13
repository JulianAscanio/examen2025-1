package co.edu.ufps.examen20251.services;

import co.edu.ufps.examen20251.models.*;
import co.edu.ufps.examen20251.models.DTO.*;
import co.edu.ufps.examen20251.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Service
public class FacturaService {
    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private TiendaRepository tiendaRepository;

    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private CajeroRepository cajeroRepository;

    @Autowired
    private TipoPagoRepository tipoPagoRepository;

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private DetalleCompraRepository detalleCompraRepository;

    @Autowired
    private PagoRepository pagoRepository;

    @Transactional
    public FacturaResponseDTO procesarFactura(UUID tiendaId, FacturaRequestDTO facturaRequest) {
        // Validaciones iniciales
        if (facturaRequest.getCliente() == null) {
            throw new RuntimeException("No hay información del cliente");
        }
        if (facturaRequest.getVendedor() == null) {
            throw new RuntimeException("No hay información del vendedor");
        }
        if (facturaRequest.getCajero() == null) {
            throw new RuntimeException("No hay información del cajero");
        }
        if (facturaRequest.getProductos() == null || facturaRequest.getProductos().isEmpty()) {
            throw new RuntimeException("No hay productos asignados para esta compra");
        }
        if (facturaRequest.getMedios_pago() == null || facturaRequest.getMedios_pago().isEmpty()) {
            throw new RuntimeException("No hay medios de pagos asignados para esta compra");
        }

        // Validar tienda
        Tienda tienda = tiendaRepository.findByUuid(tiendaId.toString())
                .orElseThrow(() -> new RuntimeException("La tienda no existe"));

        // Validar y obtener/crear cliente
        Cliente cliente = procesarCliente(facturaRequest.getCliente());

        // Validar vendedor
        Vendedor vendedor = vendedorRepository.findByDocumento(facturaRequest.getVendedor().getDocumento())
                .orElseThrow(() -> new RuntimeException("El vendedor no existe en la tienda"));

        // Validar cajero
        Cajero cajero = cajeroRepository.findByToken(facturaRequest.getCajero().getToken())
                .orElseThrow(() -> new RuntimeException("El token no corresponde a ningún cajero en la tienda"));

        if (!cajero.getTienda().getId().equals(tienda.getId())) {
            throw new RuntimeException("El cajero no está asignado a esta tienda");
        }

        // Procesar productos y calcular total
        BigDecimal totalFactura = BigDecimal.ZERO;
        BigDecimal totalImpuestos = BigDecimal.ZERO;

        for (ProductoDTO productoDTO : facturaRequest.getProductos()) {
            Producto producto = productoRepository.findByReferencia(productoDTO.getReferencia())
                    .orElseThrow(() -> new RuntimeException("La referencia del producto " + productoDTO.getReferencia() + " no existe, por favor revisar los datos"));

            if (producto.getCantidad() < productoDTO.getCantidad()) {
                throw new RuntimeException("La cantidad a comprar supera el máximo del producto en tienda");
            }

            // Actualizar cantidad disponible
            producto.setCantidad(producto.getCantidad() - productoDTO.getCantidad());
            productoRepository.save(producto);

            // Calcular subtotal con descuento
            BigDecimal precio = producto.getPrecio();
            BigDecimal cantidad = new BigDecimal(productoDTO.getCantidad());
            BigDecimal descuento = new BigDecimal(productoDTO.getDescuento() != null ? productoDTO.getDescuento() : 0);

            BigDecimal subtotal = precio.multiply(cantidad);
            BigDecimal valorDescuento = subtotal.multiply(descuento).divide(new BigDecimal(100));
            BigDecimal subtotalConDescuento = subtotal.subtract(valorDescuento);

            totalFactura = totalFactura.add(subtotalConDescuento);
        }

        // Calcular impuestos
        if (facturaRequest.getImpuesto() != null && facturaRequest.getImpuesto() > 0) {
            totalImpuestos = totalFactura.multiply(new BigDecimal(facturaRequest.getImpuesto()))
                    .divide(new BigDecimal(100));
            totalFactura = totalFactura.add(totalImpuestos);
        }

        // Validar medios de pago y sumar total pagos
        BigDecimal totalPagos = BigDecimal.ZERO;

        for (MedioPagoDTO medioPagoDTO : facturaRequest.getMedios_pago()) {
            TipoPago tipoPago = tipoPagoRepository.findByNombre(medioPagoDTO.getTipo_pago())
                    .orElseThrow(() -> new RuntimeException("Tipo de pago no permitido en la tienda"));

            if (medioPagoDTO.getValor() == null || medioPagoDTO.getValor() <= 0) {
                throw new RuntimeException("Valor de pago inválido");
            }

            totalPagos = totalPagos.add(new BigDecimal(medioPagoDTO.getValor()));
        }

        // Validar que total factura coincida con total pagos
        if (totalFactura.compareTo(totalPagos) != 0) {
            throw new RuntimeException("El valor de la factura no coincide con el valor total de los pagos");
        }

        // Crear la compra (factura)
        Compra compra = new Compra();
        compra.setCliente(cliente);
        compra.setTienda(tienda);
        compra.setVendedor(vendedor);
        compra.setCajero(cajero);
        compra.setTotal(totalFactura);
        compra.setImpuestos(totalImpuestos);
        compra.setObservaciones("Factura generada automáticamente");

        compra = compraRepository.save(compra);

        // Guardar detalles de compra
        for (ProductoDTO productoDTO : facturaRequest.getProductos()) {
            Producto producto = productoRepository.findByReferencia(productoDTO.getReferencia()).get();

            DetalleCompra detalle = new DetalleCompra();
            detalle.setCompra(compra);
            detalle.setProducto(producto);
            detalle.setCantidad(productoDTO.getCantidad());
            detalle.setPrecio(producto.getPrecio());
            detalle.setDescuento(new BigDecimal(productoDTO.getDescuento() != null ? productoDTO.getDescuento() : 0));

            detalleCompraRepository.save(detalle);
        }

        // Guardar pagos
        for (MedioPagoDTO medioPagoDTO : facturaRequest.getMedios_pago()) {
            TipoPago tipoPago = tipoPagoRepository.findByNombre(medioPagoDTO.getTipo_pago()).get();

            Pago pago = new Pago();
            pago.setCompra(compra);
            pago.setTipoPago(tipoPago);
            pago.setTarjetaTipo(medioPagoDTO.getTipo_tarjeta());
            pago.setValor(new BigDecimal(medioPagoDTO.getValor()));
            pago.setCuotas(medioPagoDTO.getCuotas());

            pagoRepository.save(pago);
        }

        // Preparar respuesta
        FacturaResponseDTO response = new FacturaResponseDTO();
        response.setStatus("success");
        response.setMessage("La factura se ha creado correctamente con el número: " + compra.getId());

        FacturaDataDTO data = new FacturaDataDTO();
        data.setNumero(compra.getId().toString());
        data.setTotal(totalFactura.toString());
        data.setFecha(LocalDate.now().format(DateTimeFormatter.ISO_DATE));

        response.setData(data);

        return response;
    }

    private Cliente procesarCliente(ClienteDTO clienteDTO) {
        // Buscar tipo de documento
        TipoDocumento tipoDocumento = tipoDocumentoRepository.findByNombre(clienteDTO.getTipo_documento())
                .orElseThrow(() -> new RuntimeException("Tipo de documento no válido"));

        // Buscar cliente por documento y tipo
        Optional<Cliente> clienteExistente = clienteRepository.findByDocumentoAndTipoDocumento(
                clienteDTO.getDocumento(), tipoDocumento);

        if (clienteExistente.isPresent()) {
            return clienteExistente.get();
        } else {
            // Crear nuevo cliente
            Cliente nuevoCliente = new Cliente();
            nuevoCliente.setNombre(clienteDTO.getNombre());
            nuevoCliente.setDocumento(clienteDTO.getDocumento());
            nuevoCliente.setTipoDocumento(tipoDocumento);

            return clienteRepository.save(nuevoCliente);
        }
    }

    public FacturaResponseDTO consultarFactura(UUID tiendaId, ConsultaFacturaRequestDTO consultaRequest) {
        // Validar tienda
        Tienda tienda = tiendaRepository.findByUuid(tiendaId.toString())
                .orElseThrow(() -> new RuntimeException("La tienda no existe"));

        // Validar cajero
        Cajero cajero = cajeroRepository.findByToken(consultaRequest.getToken())
                .orElseThrow(() -> new RuntimeException("El token no corresponde a ningún cajero en la tienda"));

        if (!cajero.getTienda().getId().equals(tienda.getId())) {
            throw new RuntimeException("El cajero no está asignado a esta tienda");
        }

        // Validar cliente
        Cliente cliente = clienteRepository.findByDocumento(consultaRequest.getCliente())
                .orElseThrow(() -> new RuntimeException("El cliente no existe"));

        // Validar y obtener compra
        Compra compra = compraRepository.findByIdAndClienteIdAndTiendaId(
                        consultaRequest.getFactura(), cliente.getId(), tienda.getId())
                .orElseThrow(() -> new RuntimeException("La factura no existe para este cliente en esta tienda"));

        // Preparar respuesta
        FacturaResponseDTO response = new FacturaResponseDTO();
        response.setStatus("success");
        response.setMessage("Factura consultada correctamente");

        FacturaDataDTO data = new FacturaDataDTO();
        data.setNumero(compra.getId().toString());
        data.setTotal(compra.getTotal().toString());
        data.setFecha(compra.getFecha().toLocalDate().format(DateTimeFormatter.ISO_DATE));

        response.setData(data);

        return response;
    }
}
