package fcc.sportsstore.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Product {

    @Id
    @Column(name = "product_id")
    private String id;

    private String title;

    @Column(length = 500)
    private String description;

    @ManyToOne
    @JoinColumn(name = "product_type")
    private ProductType productType;

    private Long createdAt;
}
