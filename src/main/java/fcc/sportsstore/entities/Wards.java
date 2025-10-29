package fcc.sportsstore.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    public Wards(String name, int vtpReferrenceId) {
        this.name = name;
        this.vtpReferrenceId = vtpReferrenceId;
    }
}
