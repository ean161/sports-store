package fcc.sportsstore.entities;

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
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "voucher_id")
    private String id;

    @Column(length = 150)
    private String code;

    /**
     * ACTIVE: Can be apply if valid
     * DISABLED: Disabled voucher
     */
    private String status;

    /**
     * -1: Unlimit
     */
    private Integer maxUsedCount;

    /**
     * Default value: 0
     */
    private Integer usedCount;

    /**
     * PERCENT: Based on prod price percent
     * STATIC: Based on discount value
     */
    private String discountType;

    private Double discountValue;

    /**
     * -1: Unlimit
     */
    private Double maxDiscountValue;

    @CreatedDate
    private LocalDateTime expiredAt;

    @CreatedDate
    private Long createdAt;

    public Voucher(String code, String status, Integer maxUsedCount, String discountType, Double discountValue, Double maxDiscountValue) {
        this.code = code;
        this.status = status;
        this.maxUsedCount = maxUsedCount;
        this.usedCount = 0;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.maxDiscountValue = maxDiscountValue;
    }

    public String getCreatedAt() {
        Date date = new Date(createdAt);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        return formatter.format(date);
    }
}
