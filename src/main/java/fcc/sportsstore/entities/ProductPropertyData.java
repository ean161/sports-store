package fcc.sportsstore.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ProductPropertyData {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_property_data_id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "product_property_field_id")
    private ProductPropertyField productPropertyField;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonIgnore
    private Product product;

    private String data;

    private Double price;

    @CreatedDate
    private LocalDateTime createdAt;

    public ProductPropertyData(ProductPropertyField productPropertyField, Product product, String data, Double price) {
        this.productPropertyField = productPropertyField;
        this.product = product;
        this.data = data;
        this.price = price;
    }
}
