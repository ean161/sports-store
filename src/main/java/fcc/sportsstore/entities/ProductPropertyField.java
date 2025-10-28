package fcc.sportsstore.entities;

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
public class ProductPropertyField {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_property_field_id")
    private String id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "product_type_id")
    @JsonIgnore
    private ProductType productType;

    @OneToMany(mappedBy = "productPropertyField", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ProductPropertyData> productPropertyData;

    private Long createdAt;

    public ProductPropertyField(String name, ProductType productType) {
        this.name = name;
        this.productType = productType;
    }
}
