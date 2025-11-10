package fcc.sportsstore.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ProductPropertySnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_property_snapshot_id")
    private String id;

    // id goc cua field
    private String productPropertyFieldId;

    // id goc cua data
    private String productPropertyDataId;

    // field = Size, Color
    private String name;

    // data = X, M, Red,...
    private String data;

    private Integer price;

    @CreatedDate
    private Long createdAt;

    @ManyToOne
    @JoinColumn(name = "product_snapshot_id")
    @JsonIgnore
    private ProductSnapshot productSnapshot;

    public ProductPropertySnapshot(String productPropertyFieldId,
                                   String productPropertyDataId,
                                   String name,
                                   String data,
                                   Integer price,
                                   ProductSnapshot productSnapshot) {
        this.productPropertyFieldId = productPropertyFieldId;
        this.productPropertyDataId = productPropertyDataId;
        this.name = name;
        this.data = data;
        this.price = price;
        this.productSnapshot = productSnapshot;
    }

    public String getCreatedAt() {
        Date date = new Date(createdAt);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        return formatter.format(date);
    }
}