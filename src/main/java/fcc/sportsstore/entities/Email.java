package fcc.sportsstore.entities;

import fcc.sportsstore.utils.TimeUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Email {

    @Id
    @Column(name = "email_id")
    private String id;

    private String address;

    private boolean isVerified;

    @Column(length = 1000)
    private String code;

    @OneToOne(mappedBy = "email")
    private User user;

    private Long verifiedAt, createdAt;

    public Email(String id, String address) {
        this.id = id;
        this.address = address;
        this.isVerified = false;

        TimeUtil time = new TimeUtil();
        this.createdAt = time.getCurrentTimestamp();
    }
}
