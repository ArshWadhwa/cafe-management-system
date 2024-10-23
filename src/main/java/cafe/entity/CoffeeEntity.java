package cafe.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
@Table(name = "coffee")
public class CoffeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column
    private Double price;

    @Column
    private String image;

    @Column(updatable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;
}