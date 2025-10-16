package fcc.sportsstore.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ProductCollection {

    @Id
    @Column(name = "product_collection_id")
    private String id;

    private String name;

    private Long createdAt;
}
