package fcc.sportsstore.entities;

import fcc.sportsstore.utils.TimeUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "forget_password")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ForgetPassword {

    @Id
    @Column(name = "forget_password_id")
    private String id;

    @Column(length = 150)
    private String code;

    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Long expiredAt, createdAt;

    public ForgetPassword(String id, String code, User user) {
        this.id = id;
        this.code = code;
        this.status = "NOT_USED_YET";
        this.user = user;

        TimeUtil time = new TimeUtil();
        Long nowTimestamp = time.getCurrentTimestamp();

        this.createdAt = nowTimestamp;
        this.expiredAt = nowTimestamp + (60 * 10);
    }
}
