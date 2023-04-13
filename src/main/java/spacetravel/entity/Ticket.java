package spacetravel.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @ManyToOne()
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToOne()
    @JoinColumn(name = "from_planet_id")
    private Planet fromPlanetId;

    @OneToOne
    @JoinColumn(name = "to_planet_id")
    private Planet toPlanetId;
}
