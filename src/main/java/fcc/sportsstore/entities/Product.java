package fcc.sportsstore.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Product {

    @Id
    @Column(name = "product_id")
    private String id;

    private String title;

    @Column(length = 500)
    private String description;

    private double price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<ProductMedia> productMedia;

    @ManyToOne
    @JoinColumn(name = "product_type_id")
    @JsonBackReference
    private ProductType productType;

    @OneToMany(mappedBy = "product")
    @JsonBackReference
    private List<ProductPropertyData> productPropertyData;

    @ManyToOne
    @JoinColumn(name = "product_collection_id")
    @JsonBackReference
    private ProductCollection productCollection;

    private Long createdAt;
}
