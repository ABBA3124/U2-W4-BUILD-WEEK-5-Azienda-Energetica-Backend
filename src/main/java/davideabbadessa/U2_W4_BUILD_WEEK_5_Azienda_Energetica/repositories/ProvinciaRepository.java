package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.repositories;

import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia, UUID> {

    @Query("SELECT p FROM Provincia p WHERE LOWER(p.nome) = LOWER(:nome)")
    Optional<Provincia> findByNome(@Param("nome") String name);

    @Query("SELECT p FROM Provincia p WHERE p.comuni IS EMPTY")
    List<Provincia> trovaProvinceSenzaComuni();
}
