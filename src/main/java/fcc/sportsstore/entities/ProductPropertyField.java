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
public class ProductPropertyField {

    @Id
    @Column(name = "product_property_field_id")
    private String id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "product_type_id")
    private ProductType productType;

    @OneToMany(mappedBy = "productPropertyField", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductPropertyData> productPropertyData;

    private Long createdAt;
}
