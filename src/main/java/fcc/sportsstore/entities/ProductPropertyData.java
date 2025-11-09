package fcc.sportsstore.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
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

    private Integer price;

    @ManyToOne
    @JoinColumn(name = "product_quantity_id")
    @JsonIgnore
    private ProductQuantity productQuantity;

    @CreatedDate
    private Long createdAt;

    public ProductPropertyData(ProductPropertyField productPropertyField, Product product, String data, Integer price) {
        this.productPropertyField = productPropertyField;
        this.product = product;
        this.data = data;
        this.price = price;
    }

    public String getCreatedAt() {
        Date date = new Date(createdAt);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        return formatter.format(date);
    }
}
