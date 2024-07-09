package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Entity
public class Ruolo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(mappedBy = "ruoli")
    private Set<Utente> utenti;

    public Ruolo(Role role) {
        this.role = role;
    }
}
