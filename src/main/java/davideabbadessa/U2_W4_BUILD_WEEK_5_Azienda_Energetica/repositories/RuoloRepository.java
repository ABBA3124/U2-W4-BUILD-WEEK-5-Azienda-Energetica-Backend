package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.repositories;

import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.Ruolo;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RuoloRepository extends JpaRepository<Ruolo, UUID> {

    boolean existsByRole(Role role);

    Optional<Ruolo> findByRole(Role role);
}
