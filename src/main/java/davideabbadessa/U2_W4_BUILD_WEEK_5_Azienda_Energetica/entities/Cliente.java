package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities;

import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.enums.TipoClienti;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
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
    @Enumerated(EnumType.STRING)
    private TipoClienti tipoClienti;

    @OneToOne
    @JoinColumn(name = "indirizzo_legale_id")
    private Indirizzo indirizzoLegale;

    @OneToOne
    @JoinColumn(name = "indirizzo_operativo_id")
    private Indirizzo indirizzoOperativo;

    @OneToMany(mappedBy = "cliente")
    private List<Fattura> fatture;

    public Cliente(String ragioneSociale, TipoClienti tipoClienti, String partitaIva, double fatturatoAnnuale, String pec, String telefono, String emailContatto, String nomeContatto, String cognomeContatto, String telefonoContatto, Indirizzo indirizzoLegale, Indirizzo indirizzoOperativo) {
        this.ragioneSociale = ragioneSociale;
        this.tipoClienti = tipoClienti;
        this.partitaIva = partitaIva;
        this.dataInserimento = LocalDate.now();
        this.fatturatoAnnuale = fatturatoAnnuale;
        this.pec = pec;
        this.telefono = telefono;
        this.emailContatto = emailContatto;
        this.nomeContatto = nomeContatto;
        this.cognomeContatto = cognomeContatto;
        this.telefonoContatto = telefonoContatto;
        this.logoAziendale = "https://ui-avatars.com/api/?name=" + nomeContatto + "+" + cognomeContatto;
        this.indirizzoLegale = indirizzoLegale;
        this.indirizzoOperativo = indirizzoOperativo;
        this.fatture = new ArrayList<>();
    }
}
