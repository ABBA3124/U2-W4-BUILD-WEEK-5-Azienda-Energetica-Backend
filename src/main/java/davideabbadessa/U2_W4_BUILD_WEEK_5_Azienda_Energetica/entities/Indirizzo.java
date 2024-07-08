package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Indirizzo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String via;
    private String civico;
    private String localita;
    private String cap;

    @ManyToOne
    @JoinColumn(name = "comune_id")
    private Comune comune;
}
