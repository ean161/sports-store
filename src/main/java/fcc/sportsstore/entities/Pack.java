package fcc.sportsstore.entities;

import fcc.sportsstore.entities.Item;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Pack {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /**
     * PENDING_APPROVAL: Waiting for staff confirm
     * PENDING_ORDER: Preparing the pack
     * IN_TRANSIT: Moved to delivery
     * SUCCESS: Delivered pack
     */
    private String status;

    /**
     * COD: Cost on delivery
     * OB: Via online banking
     */
    private String paymentType;

    /**
     * DRAFT: Waiting for online banking transaction
     * NOT_YET: Not pay yet
     * PAID: Paid pack
     */
    private String paymentStatus;

    private Double shippingFee;

    @OneToMany(mappedBy = "pack", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @CreatedDate
    private Long createdAt;
}
