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
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id")
    private String id;

    private String title;

    @Column(length = 500)
    private String description;

    private Double price;

    private Integer quantity;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ProductMedia> productMedia;

    @ManyToOne
    @JoinColumn(name = "product_type_id")
    private ProductType productType;

    @OneToMany(mappedBy = "product")
    private List<ProductPropertyData> productPropertyData;

    @ManyToOne
    @JoinColumn(name = "product_collection_id")
    private ProductCollection productCollection;

    @CreatedDate
    private LocalDateTime createdAt;

    public Product(String title, String description, Double price, ProductType productType, ProductCollection productCollection, Integer quantity) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.productType = productType;
        this.productCollection = productCollection;
        this.quantity = quantity;
    }
}
