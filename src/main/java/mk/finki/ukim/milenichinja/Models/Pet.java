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

    @Enumerated(value = EnumType.STRING)
    private Type type;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    private String age;

    private String breed;

    private String description;

    private ZonedDateTime arivalDate;

    private ZonedDateTime adoptionDate;

    private String url;

    private boolean adopted;

    @ManyToOne
    private AppUser savedBy;

    @ManyToOne
    private AppUser owner;

    @ManyToOne
    private Center center;

    public Pet() {
    }

    //POCHETEN KONSTRUKTOR
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

    //KONSTRUKTOR + PODATOCI POSLE VDOMUVANJE
    public Pet(Integer id, String name, Type type, String age, String breed, Gender gender, String description, Center center, ZonedDateTime arivalDate, ZonedDateTime adoptionDate, String url, AppUser volunteer, AppUser owner, boolean adopted) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.age = age;
        this.breed = breed;
        this.gender = gender;
        this.description = description;
        this.center = center;
        this.arivalDate = arivalDate;
        this.adoptionDate = adoptionDate;
        this.url = url;
        this.savedBy = volunteer;
        this.owner = owner;
        this.adopted = adopted;
    }

}
