package fcc.sportsstore.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fcc.sportsstore.utils.HashUtil;
import fcc.sportsstore.utils.TimeUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "manager")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
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

    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Pack> packs;

    @CreatedDate
    private Long createdAt;

    // constructor for create staff in manage
    public Manager(String username, String fullName, String password, String token) {
        this.username = username;
        this.fullName = fullName;

        HashUtil hash = new HashUtil();
        this.password = hash.md5(password);

        this.token = token;

        this.status = "ACTIVE";
        this.role = "STAFF";
    }

    public String getCreatedAt() {
        Date date = new Date(createdAt);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        return formatter.format(date);
    }
}
