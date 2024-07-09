package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
public class StatoFattura {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String stato;

    @OneToMany(mappedBy = "statoFattura")
    private List<Fattura> fatture;
}
