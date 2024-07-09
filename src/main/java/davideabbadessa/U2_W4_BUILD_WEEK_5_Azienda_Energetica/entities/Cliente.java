package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String ragioneSociale;
    private String partitaIva;
    private LocalDate dataInserimento;
    private LocalDate dataUltimoContatto;
    private double fatturatoAnnuale;
    private String pec;
    private String telefono;
    private String emailContatto;
    private String nomeContatto;
    private String cognomeContatto;
    private String telefonoContatto;
    private String logoAziendale;

    @OneToOne
    @JoinColumn(name = "indirizzo_legale_id")
    private Indirizzo indirizzoLegale;

    @OneToOne
    @JoinColumn(name = "indirizzo_operativo_id")
    private Indirizzo indirizzoOperativo;

    @OneToMany(mappedBy = "cliente")
    private List<Fattura> fatture;
}
