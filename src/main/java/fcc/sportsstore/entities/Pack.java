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
@Table(name = "pack")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Pack {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * PENDING_PAYMENT: Waiting for payment./ => To Pay
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
    private List<ProductSnapshot> productSnapshots;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;

    @ManyToOne
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;

    private String sign;

    @CreatedDate
    private Long createdAt;

    public Pack(User user, String sign, String status, String paymentType, Integer shippingFee, List<ProductSnapshot> productSnapshots, Address address, Voucher voucher) {
        this.user = user;
        this.sign = sign;
        this.status = status;
        this.paymentType = paymentType;
        this.paymentStatus = "NOT_PAY_YET";
        this.shippingFee = shippingFee;
        this.productSnapshots = productSnapshots;
        this.address = address;
        this.voucher = voucher;
    }

    public Integer getProductCount() {
        int count = 0;
        for (ProductSnapshot i : productSnapshots) {
            count += i.getQuantity();
        }

        return count;
    }

    public Integer getTotalPrice() {
        int total = shippingFee;

        for (ProductSnapshot i : productSnapshots) {
            total += i.getTotalPrice();
        }
        return total - getDiscount();
    }

    public Integer getTotalProductCost() {
        int total = 0;

        for (ProductSnapshot i : productSnapshots) {
            total += i.getTotalPrice();
        }
        return total;
    }

    public String getCreatedAt() {
        Date date = new Date(createdAt);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        return formatter.format(date);
    }

    public Integer getDiscount() {
        if (voucher == null) {
            return 0;
        }
        Integer discount = 0;

        Integer discountVal = voucher.getDiscountValue(),
                maxDiscountVal = voucher.getMaxDiscountValue();
        if (voucher.getDiscountType().equals("PERCENT")) {
            discount = (int) Math.round(getTotalProductCost() * discountVal / 100);
        } else {
            discount = discountVal;
        }

        if (maxDiscountVal != -1) {
            if (discount > maxDiscountVal) {
                discount = maxDiscountVal;
            }
        }

        return discount < 0 ? 0 : discount;
    }
}


