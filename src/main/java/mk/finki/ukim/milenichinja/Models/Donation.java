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

    private double sum;

    private Long cardNumber;

    private ZonedDateTime donationTime;

    @Enumerated(value = EnumType.STRING)
    private PaymentMethod paymentMethod;

    @ManyToOne
    private AppUser donator;

    @ManyToOne
    private DonationCause donationCause;

    @ManyToOne
    private DonationCause initialCause;

    @ManyToOne
    private Valute valute;

    public Donation() {
    }

    public Donation(AppUser donator, double sum, PaymentMethod paymentMethod, Long cardNumber, ZonedDateTime donationTime, Valute valute, DonationCause donationCause) {
        this.donator = donator;
        this.sum = sum;
        this.paymentMethod = paymentMethod;
        this.cardNumber = cardNumber;
        this.donationTime = donationTime;
        this.valute = valute;
        this.donationCause = donationCause;
    }

    public Donation(double sum, Long cardNumber, ZonedDateTime donationTime, PaymentMethod paymentMethod, AppUser donator, DonationCause donationCause, DonationCause initialCause, Valute valute) {
        this.sum = sum;
        this.cardNumber = cardNumber;
        this.donationTime = donationTime;
        this.paymentMethod = paymentMethod;
        this.donator = donator;
        this.donationCause = donationCause;
        this.initialCause = initialCause;
        this.valute = valute;
    }
}
