package fcc.sportsstore.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ProductCollection {

    @Id
    @Column(name = "product_collection_id")
    private String id;

    private String name;

    @OneToMany(mappedBy = "productCollection", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;

    private Long createdAt;
}
