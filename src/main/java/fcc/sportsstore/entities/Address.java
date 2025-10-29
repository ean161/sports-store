package fcc.sportsstore.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "address")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String note;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String addressDetail;

    @ManyToOne
    @JoinColumn(name = "province_id")
    private Province province;

    @ManyToOne
    @JoinColumn(name = "wards_id")
    private Wards wards;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Address(String note, String phoneNumber, String addressDetail, Province province, Wards wards, User user) {
        this.note = note;
        this.phoneNumber = phoneNumber;
        this.addressDetail = addressDetail;
        this.province = province;
        this.wards = wards;
        this.user = user;
    }
}
