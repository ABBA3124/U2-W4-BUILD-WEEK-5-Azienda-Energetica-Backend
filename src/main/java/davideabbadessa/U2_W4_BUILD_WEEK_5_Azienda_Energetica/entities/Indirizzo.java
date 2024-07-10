package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities;

import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.enums.TipoIndirizzo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
public class Indirizzo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String via;
    private String civico;
    private String localita;
    private String cap;
    @Enumerated(EnumType.STRING)
    private TipoIndirizzo tipoIndirizzo;

    @ManyToOne
    @JoinColumn(name = "comune_id")
    private Comune comune;

    public Indirizzo(String via, String civico, String cap, Comune comune, TipoIndirizzo tipoIndirizzo) {
        this.via = via;
        this.civico = civico;
        this.cap = cap;
        this.comune = comune;
        this.tipoIndirizzo = tipoIndirizzo;
    }
}
