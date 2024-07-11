package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.services;

import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.Cliente;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.Fattura;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.StatoFattura;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.enums.StatusFattura;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.exceptions.NotFoundException;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.payloads.NewFatturaDTO;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.repositories.FatturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class FatturaService {
    @Autowired
    private FatturaRepository fatturaRepository;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private StatoFatturaService statoFatturaService;

    public Fattura save(NewFatturaDTO body) {
        Cliente clienteFattura = clienteService.findByRagioneSociale(body.cliente());
        StatoFattura statoFattura = statoFatturaService.findByStatus(StatusFattura.getStatoFattura(body.statoFattura()));
        Fattura nuovaFattura = new Fattura(LocalDate.parse(body.data()), body.importo(), body.numero(), clienteFattura, statoFattura);
        return fatturaRepository.save(nuovaFattura);
    }

    public Page<Fattura> getFatture(int pageNumber, int pageSize, String sortby) {
        if (pageSize > 100) pageSize = 100;
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortby));
        return fatturaRepository.findAll(pageable);
    }

    public void deleteById(UUID id) {
        fatturaRepository.deleteById(id);
    }

    public Fattura findById(UUID id) {
        return fatturaRepository.findById(id).orElseThrow(() -> new NotFoundException("Fattura non trovata!"));
    }


    public Fattura findByNumero(int numero) {
        return fatturaRepository.findByNumero(numero).orElseThrow(() -> new NotFoundException("Fattura non trovata!"));
    }

    public Fattura findByIdAndUpdate(UUID id, NewFatturaDTO body) {
        Fattura found = this.findById(id);
        found.setData(LocalDate.parse(body.data()));
        found.setImporto(body.importo());
        found.setNumero(body.numero());
        found.setStatoFattura(statoFatturaService.findByStatus(StatusFattura.getStatoFattura(body.statoFattura())));
        return fatturaRepository.save(found);
    }

}
