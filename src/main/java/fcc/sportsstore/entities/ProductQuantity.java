package fcc.sportsstore.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProductQuantity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_quantity_id")
    private String id;

    private Integer amount;

    @OneToMany(mappedBy = "productQuantity")
    private List<ProductPropertyData> productPropertyData;
}
