package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.config;


import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.Role;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.Ruolo;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.repositories.RuoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {
    @Autowired
    private RuoloRepository ruoloRepository;

    @Bean
    public Ruolo initAdmin() {
        if (!ruoloRepository.existsByRole(Role.ADMIN)) {
            return ruoloRepository.save(new Ruolo(Role.ADMIN));
        }
        return null;
    }

    @Bean
    public Ruolo initUser() {
        if (!ruoloRepository.existsByRole(Role.USER)) {
            return ruoloRepository.save(new Ruolo(Role.USER));
        }
        return null;
    }

}
