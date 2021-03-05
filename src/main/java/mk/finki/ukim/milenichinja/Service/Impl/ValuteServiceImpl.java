package mk.finki.ukim.milenichinja.Service.Impl;

import mk.finki.ukim.milenichinja.Models.Valute;
import mk.finki.ukim.milenichinja.Repository.Jpa.ValuteRepository;
import mk.finki.ukim.milenichinja.Service.ValuteService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ValuteServiceImpl implements ValuteService {

    private final ValuteRepository valuteRepository;

    public ValuteServiceImpl(ValuteRepository valuteRepository) {
        this.valuteRepository = valuteRepository;
    }

    @Override
    public List<Valute> listAll() {
        return this.valuteRepository.findAll();
    }

    @Override
    public Optional<Valute> findByShortName(String valute) {
        return this.valuteRepository.findByShortName(valute);
    }

    @Override
    public double ConvertToMKD(double value, Valute valute) {
        double convertValue = valute.getValueToMKD();
        double newValue = value / convertValue;
        return newValue;
    }
}
