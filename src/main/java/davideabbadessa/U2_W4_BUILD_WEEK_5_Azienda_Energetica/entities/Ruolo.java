package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({"utenti"})
public class Ruolo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(mappedBy = "ruoli")
    private List<Utente> utenti;

    public Ruolo(Role role) {
        this.role = role;
    }
}
