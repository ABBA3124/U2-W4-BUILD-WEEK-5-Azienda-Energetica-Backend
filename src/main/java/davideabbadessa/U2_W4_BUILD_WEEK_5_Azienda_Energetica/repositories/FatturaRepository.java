package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.repositories;

import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities.Fattura;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.enums.StatusFattura;
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
public interface FatturaRepository extends JpaRepository<Fattura, UUID> {
    @Query("SELECT f FROM Fattura f " +
            "JOIN f.cliente fc " +
            "JOIN f.statoFattura fs " +
            "WHERE (:nome IS NULL OR fc.ragioneSociale LIKE %:nome%) " +
            "AND (:statoFattura IS NULL OR fs.stato LIKE :statoFattura) " +
            "AND (:dataMin IS NULL OR f.data >= :dataMin) " +
            "AND (:dataMax IS NULL OR f.data <= :dataMax) " +
            "AND (:annoMin IS NULL OR YEAR(f.data) >= :annoMin) " +
            "AND (:annoMax IS NULL OR YEAR(f.data) <= :annoMax) " +
            "AND (:importoMin IS NULL OR f.importo >= :importoMin) " +
            "AND (:importoMax IS NULL OR f.importo <= :importoMax)")
    Page<Fattura> findWithFilters(@Param("nome") String nome,
                                  @Param("statoFattura") StatusFattura statoFattura,
                                  @Param("dataMin") LocalDate dataMin,
                                  @Param("dataMax") LocalDate dataMax,
                                  @Param("annoMin") Integer annoMin,
                                  @Param("annoMax") Integer annoMax,
                                  @Param("importoMin") Double importoMin,
                                  @Param("importoMax") Double importoMax,
                                  Pageable pageable);

    Optional<Fattura> findByNumero(int numero);

}
