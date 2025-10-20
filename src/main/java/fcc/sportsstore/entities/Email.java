package fcc.sportsstore.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @Column(unique = true)
    private String address;

    private boolean isVerified;

    @Column(length = 150)
    private String code;

    @OneToOne(mappedBy = "email")
    @JsonManagedReference
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
