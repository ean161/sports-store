package fcc.sportsstore.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "product_quantity")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProductQuantity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_quantity_id")
    private String id;

    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinTable(name = "product_quantity_product_property_data",
            joinColumns = @JoinColumn(name = "product_quantity_id"),
            inverseJoinColumns = @JoinColumn(name = "product_property_data_id"))
    private Set<ProductPropertyData> productPropertyData = new HashSet<>();

    public ProductQuantity(Product product, Set<ProductPropertyData> productPropertyData, Integer amount) {
        this.product = product;
        this.productPropertyData = productPropertyData;
        this.amount = amount;
    }
}
