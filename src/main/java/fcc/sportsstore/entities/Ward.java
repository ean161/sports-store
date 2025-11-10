package fcc.sportsstore.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "ward")
@NoArgsConstructor @AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Ward {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ward_id")
    private String id;

    private String name;

    @Column(unique = true)
    private int vtpId;

    @ManyToOne
    @JoinColumn(name = "province_id")
    private Province province;

    @CreatedDate
    private Long createdAt;

    public Ward(String name, int vtpId, Province province) {
        this.name = name;
        this.vtpId = vtpId;
        this.province = province;
    }

    public String getCreatedAt() {
        Date date = new Date(createdAt);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        return formatter.format(date);
    }
}
