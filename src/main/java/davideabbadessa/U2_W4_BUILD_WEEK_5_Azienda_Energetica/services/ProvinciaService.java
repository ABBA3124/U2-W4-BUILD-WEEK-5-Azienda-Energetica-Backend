package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.services;

import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.Provincia;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.exceptions.NotFoundException;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.payloads.NuovaProvinciaDTO;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.repositories.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProvinciaService {
    @Autowired
    private ProvinciaRepository provinciaRepository;

    public Provincia save(Provincia nuovaProvincia) {
        return provinciaRepository.save(nuovaProvincia);
    }

    public Provincia findByName(String name) {
        return provinciaRepository.findByNome(name).orElseThrow(() -> new NotFoundException("Provincia non trovata"));
    }

    public boolean esistonoProvince() {
        return provinciaRepository.count() > 0;
    }

    public void delete(Provincia provincia) {
        provinciaRepository.delete(provincia);
    }

    public List<Provincia> listaProvince() {
        return provinciaRepository.findAll();
    }

    public List<Provincia> trovaProvinceSenzaComuni() {
        return provinciaRepository.trovaProvinceSenzaComuni();
    }

    public Page<Provincia> trovaTutteProvince(int page, int size, String sortBy) {
        if (size > 50) size = 50;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return provinciaRepository.findAll(pageable);
    }

    public Provincia trovaConId(UUID id) {
        return provinciaRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void deleteById(UUID id) {
        provinciaRepository.deleteById(id);
    }

    public Provincia saveWithDTO(NuovaProvinciaDTO nuovaProvinciaPayload) {
        Provincia nuovaProvincia = new Provincia(nuovaProvinciaPayload.nome(), nuovaProvinciaPayload.sigla(), nuovaProvinciaPayload.regione());
        return provinciaRepository.save(nuovaProvincia);
    }

    public Provincia aggiornaProvincia(UUID id, NuovaProvinciaDTO nuovaProvincia) {
        Provincia prov = trovaConId(id);
        prov.setNome(nuovaProvincia.nome());
        prov.setSigla(nuovaProvincia.sigla());
        prov.setRegione(nuovaProvincia.regione());
        return provinciaRepository.save(prov);
    }


}
