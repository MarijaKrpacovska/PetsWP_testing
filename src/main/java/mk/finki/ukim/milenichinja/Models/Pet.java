package mk.finki.ukim.milenichinja.Models;

import lombok.Data;
import mk.finki.ukim.milenichinja.Models.Enums.Gender;
import mk.finki.ukim.milenichinja.Models.Enums.Type;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "pet", schema = "vdomuvanje_milenichinja_wp")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String age;

    private String breed;

    private String description;

    private String url;

    private ZonedDateTime arivalDate;

    private ZonedDateTime adoptionDate;

    private boolean adopted;

    @Enumerated(value = EnumType.STRING)
    private Type type;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @ManyToOne
    private AppUser savedBy;

    @ManyToOne
    private AppUser owner;

    @ManyToOne
    private Center center;

    public Pet() {
    }

    public Pet(String name, Type type, String age, String breed, Gender gender, String description, Center center, ZonedDateTime arivalDate, String url, AppUser volunteer, boolean adopted) {
        this.name = name;
        this.type = type;
        this.age = age;
        this.breed = breed;
        this.gender = gender;
        this.description = description;
        this.center = center;
        this.arivalDate = arivalDate;
        this.url = url;
        this.savedBy = volunteer;
        this.adopted = adopted;
    }

}
