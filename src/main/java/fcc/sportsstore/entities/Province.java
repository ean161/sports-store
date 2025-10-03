package fcc.sportsstore.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Province {

    @Id
    @Column(name = "province_id")
    private String id;

    private String name;

    private Integer vtpReferrenceId;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Wards> awardsList;
}
