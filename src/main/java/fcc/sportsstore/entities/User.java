package fcc.sportsstore.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fcc.sportsstore.utils.TimeUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
public class User {

    @Id
    @Column(name = "user_id")
    private String id;

    @Column(unique = true)
    private String username;

    private String password, fullName, status;

    @Column(length = 550)
    private String token;

    private boolean gender;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "email_id")
    @JsonBackReference
    private Email email;

    private Long createdAt;

    public User(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = username;
        this.gender = true;
        this.status = "ACTIVE";

        TimeUtil time = new TimeUtil();
        this.createdAt = time.getCurrentTimestamp();
    }
}
