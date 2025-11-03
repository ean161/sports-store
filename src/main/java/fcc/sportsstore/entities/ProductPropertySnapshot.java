package fcc.sportsstore.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ProductPropertySnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_property_snapshot_id")
    private String id;

    private String productPropertyFieldId;

    private String productPropertyDataId;

    private String name;

    private String data;

    private Double price;

    @CreatedDate
    private Long createdAt;

    @ManyToOne
    @JoinColumn(name = "product_snapshot_id")
    private ProductSnapshot productSnapshot;

    public ProductPropertySnapshot(String productPropertyFieldId,
                                   String productPropertyDataId,
                                   String name,
                                   String data,
                                   Double price,
                                   ProductSnapshot productSnapshot) {
        this.productPropertyFieldId = productPropertyFieldId;
        this.productPropertyDataId = productPropertyDataId;
        this.name = name;
        this.data = data;
        this.price = price;
        this.productSnapshot = productSnapshot;
    }
}