package fcc.sportsstore.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "feedback")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "feedback_id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Integer rating;


    @Column(name = "comment", length = 255)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "product_snapshot_id")
    private ProductSnapshot productSnapshot;

    @CreatedDate
    @Column(name = "created_at")
    private Long createdAt;

    private String reply;


    public Feedback( User user,Product product, Integer rating, String comment, String reply) {
        this.user = user;
        this.product = product;
        this.rating = rating;
        this.comment = comment;
        this.reply = reply;
    }

    public Feedback( User user,Product product, Integer rating, String comment) {
        this.user = user;
        this.product = product;
        this.rating = rating;
        this.comment = comment;
    }

}
