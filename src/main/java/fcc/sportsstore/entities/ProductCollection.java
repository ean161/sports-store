package fcc.sportsstore.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonBackReference
    private List<Product> products;

    private Long createdAt;

    public ProductCollection(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
