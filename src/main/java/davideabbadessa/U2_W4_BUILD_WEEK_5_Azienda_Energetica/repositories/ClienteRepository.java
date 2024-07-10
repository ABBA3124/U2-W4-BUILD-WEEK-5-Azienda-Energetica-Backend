package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.repositories;

import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

    @Query("SELECT c " +
            "FROM Cliente c " +
            "JOIN c.indirizzoLegale i " +
            "ORDER BY i.localita ASC")
    Page<Cliente> findAllByOrderByIndirizzoLegaleLocalitaAsc(Pageable pageable);

    Optional<Cliente> findByRagioneSociale(String ragioneSociale);
}
