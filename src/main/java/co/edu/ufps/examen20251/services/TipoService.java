package co.edu.ufps.examen20251.services;

import co.edu.ufps.examen20251.models.TipoDocumento;
import co.edu.ufps.examen20251.models.TipoPago;
import co.edu.ufps.examen20251.models.TipoProducto;
import co.edu.ufps.examen20251.repository.TipoDocumentoRepository;
import co.edu.ufps.examen20251.repository.TipoPagoRepository;
import co.edu.ufps.examen20251.repository.TipoProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoService {
    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;

    @Autowired
    private TipoProductoRepository tipoProductoRepository;

    @Autowired
    private TipoPagoRepository tipoPagoRepository;

    // Métodos para TipoDocumento
    public List<TipoDocumento> listarTodosTiposDocumento() {
        return tipoDocumentoRepository.findAll();
    }

    public TipoDocumento guardarTipoDocumento(TipoDocumento tipoDocumento) {
        return tipoDocumentoRepository.save(tipoDocumento);
    }

    // Métodos para TipoPago
    public List<TipoPago> listarTodosTiposPago() {
        return tipoPagoRepository.findAll();
    }

    public TipoPago guardarTipoPago(TipoPago tipoPago) {
        return tipoPagoRepository.save(tipoPago);
    }
}
