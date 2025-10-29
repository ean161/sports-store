package fcc.sportsstore.entities;

import fcc.sportsstore.utils.TimeUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "manager")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "manager_id")
    private String id;

    @Column(unique = true)
    private String username;

    private String password, fullName, status;

    @Column(length = 550, unique = true)
    private String token;

    private String role;

    @CreatedDate
    private LocalDateTime createdAt;

    public Manager(String username, String fullName, String password, String token, String role) {
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.status = "ACTIVE";
        this.token = token;
        this.role = role;
    }
}
