package cafe.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
@Entity
@Data
@Table(name = "cart")
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    private Integer quantity;

    @Column()
    private Long coffee_id;

    @Column(updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;
}

