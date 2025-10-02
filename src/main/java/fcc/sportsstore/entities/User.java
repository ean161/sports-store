package fcc.sportsstore.entities;

import fcc.sportsstore.utils.TimeUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class User {

    @Id
    @Column(name = "user_id")
    private String id;

    private String username, password, email;

    @Column(length = 1000)
    private String token;

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
