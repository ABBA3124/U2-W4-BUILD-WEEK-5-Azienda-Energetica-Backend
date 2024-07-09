package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.services;

import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.Provincia;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.repositories.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProvinciaService {
    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Autowired
    private ComuneService comuneService;


    public Provincia save(Provincia nuovaProvincia) {
        return provinciaRepository.save(nuovaProvincia);
    }

    public Optional<Provincia> findByName(String name) {
        return provinciaRepository.findByNome(name);
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


}
