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
@Table(name = "manager")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Manager {
    @Id
    @Column(name = "manager_id")
    private String id;

    @Column(unique = true)
    private String username;

    private String password, fullName, status;

    @Column(length = 550)
    private String token;

    private String role;

    private Long createdAt;

    public Manager(String id, String username, String fullName, String password, String token, String role) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.status = "ACTIVE";
        this.token = token;
        this.role = role;

        TimeUtil time = new TimeUtil();
        this.createdAt = time.getCurrentTimestamp();
    }
}
