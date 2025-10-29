package fcc.sportsstore.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ProductCollection {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_collection_id")
    private String id;

    private String name;

    @OneToMany(mappedBy = "productCollection", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Product> products;

    @CreatedDate
    private LocalDateTime createdAt;

    public ProductCollection(String name) {
        this.name = name;
    }
}
