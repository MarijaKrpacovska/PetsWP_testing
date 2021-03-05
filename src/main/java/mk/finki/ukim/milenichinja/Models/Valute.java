package mk.finki.ukim.milenichinja.Models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "valute", schema = "vdomuvanje_milenichinja_wp")
public class Valute {
    @Id
    private String shortName;

    private String country;

    private String longName;

    private double valueToMKD;

    public Valute() {
    }

    public Valute(String shortName, String country, String longName, double valueToMKD) {
        this.shortName = shortName;
        this.country = country;
        this.longName = longName;
        this.valueToMKD = valueToMKD;
    }
}
