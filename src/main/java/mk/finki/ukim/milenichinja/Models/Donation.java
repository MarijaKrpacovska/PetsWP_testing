package mk.finki.ukim.milenichinja.Models;

import lombok.Data;
import mk.finki.ukim.milenichinja.Models.Enums.PaymentMethod;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(name = "donation", schema = "vdomuvanje_milenichinja_wp")
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private AppUser donator;
    private double sum;
    @Enumerated(value = EnumType.STRING)
    private PaymentMethod paymentMethod;
    private Long cardNumber;
    private ZonedDateTime donationTime;
    private String valute;
    @ManyToOne
    private DonationCause donationCause;

    public Donation() {
    }

    public Donation(AppUser donator, double sum, PaymentMethod paymentMethod, Long cardNumber, ZonedDateTime donationTime, String valute, DonationCause donationCause) {
        this.donator = donator;
        this.sum = sum;
        this.paymentMethod = paymentMethod;
        this.cardNumber = cardNumber;
        this.donationTime = donationTime;
        this.valute = valute;
        this.donationCause = donationCause;
    }
}
