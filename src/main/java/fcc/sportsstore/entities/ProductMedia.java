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
@AllArgsConstructor @NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ProductMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String position, type, dir;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @CreatedDate
    private Long createdAt;

    public ProductMedia(Product product, String dir) {
        this.product = product;
        this.dir = dir;
        this.type = "IMAGE";
    }
}
