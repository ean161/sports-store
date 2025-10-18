package fcc.sportsstore.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ProductMedia {

    @Id
    private String id;

    private String position, type, dir;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
