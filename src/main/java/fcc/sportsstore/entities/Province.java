package fcc.sportsstore.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Wards> awards;
}
