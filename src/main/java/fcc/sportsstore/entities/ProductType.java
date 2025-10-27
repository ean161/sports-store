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
@Setter @Getter
@AllArgsConstructor @NoArgsConstructor
public class ProductType {

    @Id
    @Column(name = "product_type_id")
    private String id;

    private String name;

    @OneToMany(mappedBy = "productType", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<ProductPropertyField> productPropertyFields;

    @OneToMany(mappedBy = "productType", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Product> products;

    private Long createdAt;

    public ProductType(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
