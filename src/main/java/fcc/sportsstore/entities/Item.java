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
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "item_id")
    private String id;

    /**
     * CART: Pending for payment, in cart
     * ORDER: Order processed item
     */
    private String type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_snapshot_id")
    private ProductSnapshot productSnapshot;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "pack_id")
    @JsonIgnore
    private Pack pack;

    @CreatedDate
    private Long createdAt;

    public Item(String type, User user, ProductSnapshot productSnapshot, Integer quantity) {
        this.type = type;
        this.user = user;
        this.productSnapshot = productSnapshot;
        this.quantity = quantity;
    }

    public Integer getTotalPrice() {
        return productSnapshot.getPrice() * quantity;
    }

    public String getCreatedAt() {
        Date date = new Date(createdAt);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        return formatter.format(date);
    }
}
