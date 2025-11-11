package fcc.sportsstore.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private Integer quantity;

    /**
     * CART: Pending for payment, in cart
     * ORDER: Order processed item
     */
    private String type;

    private boolean isAvailable;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "pack_id")
    @JsonIgnore
    private Pack pack;

    @OneToMany(mappedBy = "productSnapshot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductPropertySnapshot> productPropertySnapshots;

    @CreatedDate
    private Long createdAt;

    public ProductSnapshot(String productId, String title, Integer price) {
        this.productId = productId;
        this.title = title;
        this.price = price;
        this.isAvailable = true;
    }

    public ProductSnapshot(String type, User user, List<ProductPropertySnapshot> productPropertySnapshots, Integer quantity, String productId, String title, Integer price, boolean isAvailable) {
        this.type = type;
        this.user = user;
        this.productPropertySnapshots = productPropertySnapshots;
        this.quantity = quantity;
        this.productId = productId;
        this.title = title;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    public Integer getTotalPrice() {
        return getPrice() * quantity;
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
