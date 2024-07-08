package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Provincia {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String nome;
    private String sigla;
    private String regione;

    @OneToMany(mappedBy = "provincia")
    private List<Comune> comuni;
}
