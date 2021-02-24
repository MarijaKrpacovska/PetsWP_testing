package mk.finki.ukim.milenichinja.Models;

import lombok.Data;
import mk.finki.ukim.milenichinja.Models.Enums.City;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "center", schema = "vdomuvanje_milenichinja_wp")
public class Center {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String address;
    private String url;
    //@ManyToOne
    //private City city;
    @Enumerated(value = EnumType.STRING)
    private City city;

    //@ManyToMany(mappedBy = "worksAt")
    //private List<AppUser> workers = new ArrayList<>();

    public Center() {
    }

    public Center(String address) {
        this.address = address;
    }

    public Center(String address, City city, String url) {
        this.address = address;
        this.city = city;
        this.url = url;
    }
}
