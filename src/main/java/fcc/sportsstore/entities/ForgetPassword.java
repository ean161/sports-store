package fcc.sportsstore.entities;

import fcc.sportsstore.utils.TimeUtil;
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

    public String getCreatedAt() {
        Date date = new Date(createdAt);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        return formatter.format(date);
    }
}
