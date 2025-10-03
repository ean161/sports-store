package fcc.sportsstore.entities;

import fcc.sportsstore.utils.TimeUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "user")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class User {

    @Id
    @Column(name = "user_id")
    private String id;

    private String username, password;

    @Column(length = 550)
    private String token;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "email_id")
    private Email email;

    private Long createdAt;

    /**
     * Constructor
     * @param id User ID
     * @param username User username address
     * @param password User password
     */
    public User(String id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;

        TimeUtil time = new TimeUtil();
        this.createdAt = time.getCurrentTimestamp();
    }
}
