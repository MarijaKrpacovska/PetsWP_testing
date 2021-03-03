package mk.finki.ukim.milenichinja.Service;

import mk.finki.ukim.milenichinja.Models.Center;
import mk.finki.ukim.milenichinja.Models.Enums.City;

import java.util.List;
import java.util.Optional;

public interface CenterService {

    List<Center> listAll();

    Optional<Center> findById(int id);

    Optional<Center> save(String address, City city, String url);

    Optional<Center> delete(int id);

    Optional<Center> edit(int id, String address, City city, String url);

    //Optional<Center> edit(int id, String address, int idCity, String url);

    //Optional<Center> save(String address, int idCity, String url);

}
