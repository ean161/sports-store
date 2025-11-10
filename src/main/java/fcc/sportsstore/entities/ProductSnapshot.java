package fcc.sportsstore.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ProductSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_snapshot_id")
    private String id;

    private String productId;

    private String title;

    private Integer price;

    private boolean isAvailable;

    @CreatedDate
    private Long createdAt;

    @OneToMany(mappedBy = "productSnapshot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductPropertySnapshot> productPropertySnapshots;

    public ProductSnapshot(String productId, String title, Integer price) {
        this.productId = productId;
        this.title = title;
        this.price = price;
        this.isAvailable = true;
    }

    public Integer getPrice() {
        Integer total = this.price;
        for (ProductPropertySnapshot item : productPropertySnapshots) {
            total += item.getPrice();
        }

        return total;
    }

    public String getCreatedAt() {
        Date date = new Date(createdAt);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        return formatter.format(date);
    }
}
