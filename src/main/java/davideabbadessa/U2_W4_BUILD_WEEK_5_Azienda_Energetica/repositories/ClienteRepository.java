package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.repositories;

import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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

    Optional<Cliente> findByPartitaIva(String partitaIva);

    @Query("SELECT c FROM Cliente c " +
            "WHERE (:nome IS NULL OR c.ragioneSociale LIKE %:nome%) " +
            "AND (:fatturatoAnnualeMin IS NULL OR c.fatturatoAnnuale >= :fatturatoAnnualeMin) " +
            "AND (:fatturatoAnnualeMax IS NULL OR c.fatturatoAnnuale <= :fatturatoAnnualeMax) " +
            "AND (:dataInserimentoMin IS NULL OR c.dataInserimento >= :dataInserimentoMin) " +
            "AND (:dataInserimentoMax IS NULL OR c.dataInserimento <= :dataInserimentoMax) " +
            "AND (:dataUltimoContattoMin IS NULL OR c.dataUltimoContatto >= :dataUltimoContattoMin) " +
            "AND (:dataUltimoContattoMax IS NULL OR c.dataUltimoContatto <= :dataUltimoContattoMax)")
    Page<Cliente> findWithFilters(@Param("nome") String nome,
                                  @Param("fatturatoAnnualeMin") Double fatturatoAnnualeMin,
                                  @Param("fatturatoAnnualeMax") Double fatturatoAnnualeMax,
                                  @Param("dataInserimentoMin") LocalDate dataInserimentoMin,
                                  @Param("dataInserimentoMax") LocalDate dataInserimentoMax,
                                  @Param("dataUltimoContattoMin") LocalDate dataUltimoContattoMin,
                                  @Param("dataUltimoContattoMax") LocalDate dataUltimoContattoMax,
                                  Pageable pageable);
}
