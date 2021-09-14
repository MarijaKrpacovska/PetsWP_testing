package mk.finki.ukim.milenichinja.Models;

import lombok.Data;
import mk.finki.ukim.milenichinja.Models.Enums.Status;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "donation_cause", schema = "vdomuvanje_milenichinja_wp")
public class DonationCause {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    private String imageUrl;

    private double goal;

    @Column(nullable = true)
    private double currentSum;

    private int importance;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "pets_related_to_donationCause", schema = "vdomuvanje_milenichinja_wp" )
    private List<Pet> pets;

    public DonationCause() {
    }

    public DonationCause(String name, String description, String imageUrl, double goal, List<Pet> pets, Status status, int importance) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.goal = goal;
        this.pets = pets;
        this.status = status;
        this.importance = importance;
    }

    public DonationCause(String name, String description, String imageUrl, double goal, double currentSum, List<Pet> pets, Status status, int importance) {
        this.name = name;
        this.currentSum = currentSum;
        this.description = description;
        this.imageUrl = imageUrl;
        this.goal = goal;
        this.pets = pets;
        this.status = status;
        this.importance = importance;
    }
}
