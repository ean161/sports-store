package fcc.sportsstore.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Province {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "province_id")
    private String id;

    private String name;

    @Column(unique = true)
    private Integer vtpId;

    @OneToMany(mappedBy = "province", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Ward> awards;

    @CreatedDate
    private Long createdAt;

    public Province(String name, Integer vtpId) {
        this.name = name;
        this.vtpId = vtpId;
    }
}
