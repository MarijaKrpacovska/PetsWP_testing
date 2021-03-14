package mk.finki.ukim.milenichinja.Models;

import lombok.Data;
import mk.finki.ukim.milenichinja.Models.Enums.PaymentMethod;
import mk.finki.ukim.milenichinja.Models.Enums.Status;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "adoption", schema = "vdomuvanje_milenichinja_wp")
public class Adoption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private ZonedDateTime adoptionTime;

    private ZonedDateTime realLifeTime;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    @ManyToOne
    private AppUser user;

    @ManyToOne
    private Pet pet;


    public Adoption() {
    }

    public Adoption(ZonedDateTime adoptionTime, ZonedDateTime realLifeTime, Status status, AppUser user, Pet pet) {
        this.adoptionTime = adoptionTime;
        this.realLifeTime = realLifeTime;
        this.status = status;
        this.user = user;
        this.pet = pet;
    }
}
