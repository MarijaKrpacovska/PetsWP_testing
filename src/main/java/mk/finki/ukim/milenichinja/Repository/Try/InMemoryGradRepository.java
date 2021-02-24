/*package mk.finki.ukim.milenichinja.Repository.Try;

import mk.finki.ukim.milenichinja.Models.City;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemoryGradRepository {
    List<City> cityList = new ArrayList<>();
    @PostConstruct
    public void init(){
        cityList.add(new City("Skopje"));
        cityList.add(new City("Bitola"));
    }
    public List<City> findAll(){
        return cityList;
    }
}*/
