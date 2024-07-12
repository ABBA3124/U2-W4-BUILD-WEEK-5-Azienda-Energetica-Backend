package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({"comuni"})
public class Provincia {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String nome;
    private String sigla;
    private String regione;

    @OneToMany(mappedBy = "provincia")
    private List<Comune> comuni;

    public Provincia(String nome, String sigla, String regione) {
        this.nome = nome;
        this.sigla = sigla;
        this.regione = regione;
    }
}
