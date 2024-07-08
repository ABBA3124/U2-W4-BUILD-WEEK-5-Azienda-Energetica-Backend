package davideabbadessa.U2_W4_BUILD_WEEK_5_Azienda_Energetica.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String email;
    private String password;
    private String nome;
    private String cognome;
    private String avatar;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "utente_ruoli", joinColumns = @JoinColumn(name = "utente_id"), inverseJoinColumns = @JoinColumn(name = "ruolo_id"))
    private Set<Ruolo> ruoli;
}
