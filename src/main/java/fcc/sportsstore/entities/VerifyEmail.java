package fcc.sportsstore.entities;

import fcc.sportsstore.utils.TimeUtil;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "verify_email")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class VerifyEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "verify_email_id")
    private String id;

    @Column(length = 150, unique = true)
    private String code;

    /**
     * VERIFIED, NOT_VERIFIED_YET
     */
    private String status;

    @ManyToOne
    @JoinColumn(name = "email_id")
    private Email email;

    private Long expiredAt;

    @CreatedDate
    private Long createdAt;

    public VerifyEmail(Email email, String code) {
        this.email = email;
        this.code = code;
        this.status = "NOT_VERIFIED_YET";

        TimeUtil time = new TimeUtil();
        Long nowTimestamp = time.getCurrentTimestamp();

        this.expiredAt = nowTimestamp + (60 * 10);
    }

    public boolean isExpired() {
        TimeUtil time = new TimeUtil();
        Long nowTimestamp = time.getCurrentTimestamp();

        return nowTimestamp > expiredAt;
    }
}
