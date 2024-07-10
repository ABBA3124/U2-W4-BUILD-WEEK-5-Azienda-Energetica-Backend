package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.enums.StatusFattura;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({"fatture"})
public class StatoFattura {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Enumerated(EnumType.STRING)
    private StatusFattura stato;

    @OneToMany(mappedBy = "statoFattura")
    private List<Fattura> fatture;

    public StatoFattura(StatusFattura stato) {
        this.stato = stato;
    }
}
