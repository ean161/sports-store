package fcc.sportsstore.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
@NoArgsConstructor @AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Wards {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "wards_id")
    private String id;

    private String name;

    private int vtpReferrenceId;

    @ManyToOne
    @JoinColumn(name = "province_id")
    private Province province;

    @CreatedDate
    private Long createdAt;

    public Wards(String name, int vtpReferrenceId) {
        this.name = name;
        this.vtpReferrenceId = vtpReferrenceId;
    }
}
