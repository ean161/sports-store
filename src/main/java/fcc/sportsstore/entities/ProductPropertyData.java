package fcc.sportsstore.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ProductPropertyData {

    @Id
    @Column(name = "product_property_data_id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "product_property_field_id")
    private ProductPropertyField productPropertyField;

    private String data;

    private Long createdAt;
}
