/*package mk.finki.ukim.milenichinja.Service.Impl;

import mk.finki.ukim.milenichinja.Models.City;
import mk.finki.ukim.milenichinja.Models.Exceptions.CityNotFoundException;
import mk.finki.ukim.milenichinja.Repository.Jpa.CityRepository;
import mk.finki.ukim.milenichinja.Service.CityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;

    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public List<City> listAll() {
        return cityRepository.findAll();
    }

    @Override
    public Optional<City> save(String name) {
        City city = new City(name);
        return Optional.of(this.cityRepository.save(city));
    }

    @Override
    public Optional<City> delete(int id) {
        City city = cityRepository.findById(id).orElseThrow( () -> new CityNotFoundException(id) );
        cityRepository.delete(city);
        return Optional.of(city);
    }

    @Override
    public Optional<City> edit(int id, String name) {
        City city = cityRepository.findById(id).orElseThrow( () -> new CityNotFoundException(id) );
        city.setName(name);
        cityRepository.save(city);
        return Optional.of(city);
    }

    @Override
    public Optional<City> findById(int id) {
        return cityRepository.findById(id);
    }

}*/
