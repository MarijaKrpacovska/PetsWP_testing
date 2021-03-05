package mk.finki.ukim.milenichinja.Repository.Jpa;

import mk.finki.ukim.milenichinja.Models.Enums.AgeGroup;
import mk.finki.ukim.milenichinja.Models.Enums.Gender;
import mk.finki.ukim.milenichinja.Models.Enums.Type;
import mk.finki.ukim.milenichinja.Models.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet,Integer> {

    void deleteById(int id);

    Optional<Pet> findById(int id);

    List<Pet> findAllByAdopted(Boolean adopted);


    List<Pet> findAllByAgeGroupAndAdopted(AgeGroup age, Boolean adopted);

    List<Pet> findAllByBreedLikeAndAdopted(String breed, Boolean adopted);

    List<Pet> findAllByGenderAndAdopted(Gender gender, Boolean adopted);

    List<Pet> findAllByTypeAndAdopted(Type type, Boolean adopted);


    List<Pet> findAllByAgeGroupAndBreedLikeAndAdopted(AgeGroup age,String breed, Boolean adopted);

    List<Pet> findAllByAgeGroupAndGenderAndAdopted(AgeGroup age,Gender gender, Boolean adopted);

    List<Pet> findAllByAgeGroupAndTypeAndAdopted(AgeGroup age,Type type, Boolean adopted);


    List<Pet> findAllByBreedLikeAndGenderAndAdopted(String breed,Gender gender, Boolean adopted);

    List<Pet> findAllByBreedLikeAndTypeAndAdopted(String breed,Type type, Boolean adopted);

    List<Pet> findAllByGenderAndTypeAndAdopted(Gender gender,Type type, Boolean adopted);

    List<Pet> findAllByAgeGroupAndBreedLikeAndGenderAndAdopted(AgeGroup age, String breed, Gender gender, Boolean adopted);


    List<Pet> findAllByAgeGroupAndBreedLikeAndTypeAndAdopted(AgeGroup age, String breed, Type type, Boolean adopted);

    List<Pet> findAllByAgeGroupAndGenderAndTypeAndAdopted(AgeGroup age, Gender gender, Type type, Boolean adopted);

    List<Pet> findAllByBreedLikeAndGenderAndTypeAndAdopted(String breed, Gender gender, Type type, Boolean adopted);

    List<Pet> findAllByAgeGroupAndBreedLikeAndGenderAndTypeAndAdopted(AgeGroup age, String breed, Gender gender, Type type, Boolean adopted);


    List<Pet> findAllByAdoptedAndId(Boolean adopted, Integer id);


}
