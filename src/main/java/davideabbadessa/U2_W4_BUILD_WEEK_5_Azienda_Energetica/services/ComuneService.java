package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.services;

import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.Comune;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.exceptions.NotFoundException;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.payloads.NewComuneDTO;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.repositories.ComuneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ComuneService {

    @Autowired
    private ComuneRepository comuneRepository;

    @Autowired
    private ProvinciaService provinciaService;


    public Comune saveWithDTO(NewComuneDTO nuovoComunePayload) {
        Comune nuovoComune = new Comune(nuovoComunePayload.nome(), provinciaService.findByName(nuovoComunePayload.provincia()));
        return comuneRepository.save(nuovoComune);
    }

    public Comune save(Comune nuovoComune) {
        return comuneRepository.save(nuovoComune);
    }


    public boolean esistonoComuni() {
        return comuneRepository.count() > 0;
    }

    public Page<Comune> trovaTuttiIComuni(int page, int size, String sortBy) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return comuneRepository.findAll(pageable);
    }

    public void deleteById(UUID id) {
        comuneRepository.deleteById(id);
    }

    public Comune trovaConId(UUID id) {
        return comuneRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Comune aggiornaComune(UUID id, NewComuneDTO nuovoComune) {
        Comune comune = trovaConId(id);
        comune.setProvincia(provinciaService.findByName(nuovoComune.provincia()));
        comune.setNome(nuovoComune.nome());
        return comuneRepository.save(comune);
    }

}
