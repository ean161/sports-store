package fcc.sportsstore.entities;

import fcc.sportsstore.utils.Time;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "recovery_password")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class RecoveryPassword {

    @Id
    @Column(name = "recovery_password_id")
    private String id;

    @Column(length = 100)
    private String code;

    private String status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Long expiredAt, createdAt;

    public RecoveryPassword(String id, String code, User user) {
        this.id = id;
        this.code = code;
        this.status = "NOT_USED_YET";
        this.user = user;

        Time time = new Time();
        Long nowTimestamp = time.getCurrentTimestamp();

        this.createdAt = nowTimestamp;
        this.expiredAt = nowTimestamp + (60 * 10);
    }
}
