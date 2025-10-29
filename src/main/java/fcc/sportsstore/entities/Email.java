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
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "email_id")
    private String id;

    @Column(unique = true)
    private String address;

    private boolean isVerified;

    @Column(length = 150)
    private String code;

    @OneToOne(mappedBy = "email")
    @JsonIgnore
    private User user;

    private Long verifiedAt;

    @CreatedDate
    private LocalDateTime createdAt;

    public Email(String address) {
        this.address = address;
        this.isVerified = false;
    }
}
