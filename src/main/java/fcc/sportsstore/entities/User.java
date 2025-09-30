package fcc.sportsstore.entities;

import fcc.sportsstore.utils.Time;
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

    private String email, password;

    private Long createdAt;

    /**
     * Constructor
     * @param id User ID
     * @param email User email address
     * @param password User password
     */
    public User(String id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;

        // Get current timestamp for "created_at" column
        Time time = new Time();
        this.createdAt = time.getCurrentTimestamp();
    }
}
