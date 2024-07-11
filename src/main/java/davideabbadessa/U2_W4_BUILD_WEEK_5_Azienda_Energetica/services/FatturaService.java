package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.services;

import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.Cliente;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.Fattura;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.StatoFattura;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.enums.StatusFattura;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.payloads.NewFatturaDTO;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.repositories.FatturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class FatturaService {
    @Autowired
    private FatturaRepository fatturaRepository;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private StatoFatturaService statoFatturaService;

    public Page<Fattura> getFattura(int pageNumber, int pageSize, String sortby) {
        if (pageSize > 100) pageSize = 100;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortby));
        return fatturaRepository.findAll(pageable);
    }

    public Fattura save(NewFatturaDTO body) {
        Cliente clienteFattura = clienteService.findByRagioneSociale(body.cliente());
        StatoFattura statoFattura = statoFatturaService.findByStatus(StatusFattura.getStatoFattura(body.statoFattura()));
        Fattura nuovaFattura = new Fattura(LocalDate.parse(body.data()), body.importo(), body.numero(), clienteFattura, statoFattura);
        return fatturaRepository.save(nuovaFattura);
    }

    public Page<Fattura> trovaTutteLeFattureConFiltri(String nome,
                                                      StatoFattura statoFattura,
                                                      LocalDate dataMin,
                                                      LocalDate dataMax,
                                                      Integer annoMin,
                                                      Integer annoMax,
                                                      Double importoMin,
                                                      Double importoMax,
                                                      int pageNumber,
                                                      int pageSize,
                                                      String sortby) {
        if (pageSize > 100) pageSize = 100;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortby));
        return fatturaRepository.findWithFilters(nome, statoFattura,
                dataMin, dataMax, annoMin, annoMax, importoMin, importoMax, pageable);
    }
}
