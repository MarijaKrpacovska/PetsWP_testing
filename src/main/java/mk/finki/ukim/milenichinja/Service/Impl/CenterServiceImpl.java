package mk.finki.ukim.milenichinja.Service.Impl;

import mk.finki.ukim.milenichinja.Models.Center;
import mk.finki.ukim.milenichinja.Models.Enums.City;
import mk.finki.ukim.milenichinja.Models.Exceptions.CenterNotFoundException;
import mk.finki.ukim.milenichinja.Models.Exceptions.DeleteConstraintViolationException;
import mk.finki.ukim.milenichinja.Repository.Jpa.CenterRepository;
//import mk.finki.ukim.milenichinja.Repository.Jpa.CityRepository;
import mk.finki.ukim.milenichinja.Repository.Jpa.PetRepository;
import mk.finki.ukim.milenichinja.Service.CenterService;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CenterServiceImpl implements CenterService {

    private final CenterRepository centerRepository;
    private final PetRepository petRepository;

    public CenterServiceImpl(CenterRepository centerRepository, PetRepository petRepository) {
        this.centerRepository = centerRepository;
        this.petRepository = petRepository;
    }

    @Override
    public Optional<Center> findById(int id) {
        return this.centerRepository.findById(id);
    }

    @Override
    public List<Center> listAll() {
        return centerRepository.findAll();
    }

    @Override
    public Optional<Center> save(String address, City city, String url) {
        return Optional.of(this.centerRepository.save(new Center(address,city, url)));
    }

    @Override
    public Optional<Center> delete(int id) {
        Center center = this.centerRepository.findById(id).orElseThrow( () -> new CenterNotFoundException(id) );
        if(!petRepository.findAllByCenter(center).isEmpty()){
            throw new DeleteConstraintViolationException(id);
        }
        else
            this.centerRepository.delete(center);
        return Optional.of(center);
    }

    @Override
    public Optional<Center> edit(int id, String address, City city, String url) {
        Center center = this.centerRepository.findById(id).orElseThrow( () -> new CenterNotFoundException(id) );

        center.setAddress(address);
        center.setCity(city);
        center.setUrl(url);

        centerRepository.save(center);
        return Optional.of(center);
    }

    /*@Override
    public Optional<Center> edit(int id, String address, int idCity, String url) {
        Center center = this.centerRepository.findById(id).orElseThrow( () -> new CenterNotFoundException(id) );
        City city = this.cityRepository.findById(idCity).orElseThrow( () -> new CityNotFoundException(idCity) );

        center.setAddress(address);
        center.setCity(city);
        center.setUrl(url);

        centerRepository.save(center);
        return Optional.of(center);
    }*/

    /*@Override
    public Optional<Center> save(String address, int idCity, String url) {
        City city = cityRepository.findById(idCity).orElseThrow( () -> new CityNotFoundException(idCity) );
        return Optional.of(this.centerRepository.save(new Center(address,city, url)));
    }*/

}
