package fcc.sportsstore.entities;

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
public class Pack {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * PENDING_PAYMENT: Waiting for payment.///
     * PENDING_APPROVAL: Waiting for staff confirm.
     * PENDING_ORDER: Preparing the pack.
     * IN_TRANSIT: Moved to delivery.
     * SUCCESS: Delivered pack.
     * CANCELLED: Cancelled order.
     * REFUNDING: Refunding order.
     */
    private String status;

    /**
     * COD: Cost on delivery
     * OB: Via online bankingx
     */
    private String paymentType;

    /**
     * DRAFT: Waiting for online banking transaction
     * PAID: Paid pack
     */
    private String paymentStatus;

    private Integer shippingFee;

    @OneToMany(mappedBy = "pack", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;

    private String sign;

    @CreatedDate
    private Long createdAt;

    public Pack(User user, String sign, String status, String paymentType, Integer shippingFee, List<Item> items, Address address) {
        this.user = user;
        this.sign = sign;
        this.status = status;
        this.paymentType = paymentType;
        this.paymentStatus = "NOT_PAY_YET";
        this.shippingFee = shippingFee;
        this.items = items;
        this.address = address;
    }

    public Integer getItemCount() {
        int count = 0;
        for (Item i : items) {
            count += i.getQuantity();
        }

        return count;
    }

    public Integer getTotalPrice() {
        int total = shippingFee;
        for(Item i : items) {
            total += i.getTotalPrice();
        }

        return total;
    }

    public String getCreatedAt() {
        Date date = new Date(createdAt);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        return formatter.format(date);
    }

}
