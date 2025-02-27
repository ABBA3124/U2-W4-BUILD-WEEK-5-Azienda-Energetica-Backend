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
@JsonIgnoreProperties({"indirizzi"})
public class Comune {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "provincia_id")
    private Provincia provincia;

    @OneToMany(mappedBy = "comune")
    private List<Indirizzo> indirizzi;

    public Comune(String nome, Provincia provincia) {
        this.nome = nome;
        this.provincia = provincia;
    }
}
