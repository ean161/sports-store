package fcc.sportsstore.entities;

import fcc.sportsstore.utils.TimeUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "forget_password")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ForgetPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "forget_password_id")
    private String id;

    @Column(length = 150)
    private String code;

    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Long expiredAt;

    @CreatedDate
    private Long createdAt;

    public ForgetPassword(String code, User user) {
        this.code = code;
        this.status = "NOT_USED_YET";
        this.user = user;

        TimeUtil time = new TimeUtil();
        Long nowTimestamp = time.getCurrentTimestamp();

        this.expiredAt = nowTimestamp + (60 * 10);
    }
}
