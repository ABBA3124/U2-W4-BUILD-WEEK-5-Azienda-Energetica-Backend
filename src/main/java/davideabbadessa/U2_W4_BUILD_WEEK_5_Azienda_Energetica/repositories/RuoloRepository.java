package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.repositories;

import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.Ruolo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RuoloRepository extends JpaRepository<Ruolo, UUID> {
}
