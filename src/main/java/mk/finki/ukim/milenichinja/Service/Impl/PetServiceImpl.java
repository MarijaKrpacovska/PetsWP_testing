package mk.finki.ukim.milenichinja.Service.Impl;

import mk.finki.ukim.milenichinja.Models.AppUser;
import mk.finki.ukim.milenichinja.Models.Center;
import mk.finki.ukim.milenichinja.Models.Enums.Gender;
import mk.finki.ukim.milenichinja.Models.Exceptions.CenterNotFoundException;
import mk.finki.ukim.milenichinja.Models.Exceptions.PetNotFoundException;
import mk.finki.ukim.milenichinja.Models.Pet;
import mk.finki.ukim.milenichinja.Models.Enums.Type;
import mk.finki.ukim.milenichinja.Repository.Jpa.AppUserRepository;
import mk.finki.ukim.milenichinja.Repository.Jpa.CenterRepository;
import mk.finki.ukim.milenichinja.Repository.Jpa.PetRepository;
import mk.finki.ukim.milenichinja.Service.PetService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.ZonedDateTime;

@Service
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final AppUserRepository appUserRepository;
    private final CenterRepository centerRepository;

    public PetServiceImpl(PetRepository petRepository, AppUserRepository appUserRepository, CenterRepository centerRepository) {
        this.petRepository = petRepository;
        this.appUserRepository = appUserRepository;
        this.centerRepository = centerRepository;
    }

    @Override
    public List<Pet> listAll() {
        return petRepository.findAll();
    }

    @Override
    public Optional<Pet> save(String ime, Type vid, String vozrast, String rasa, Gender pol, String opis, int id_centar, String url_slika, AppUser odgovorenVolonter) {
        ZonedDateTime currentDateTime = ZonedDateTime.now();
        Center center = centerRepository.findById(id_centar).orElseThrow( () -> new CenterNotFoundException(id_centar));
        return Optional.of(this.petRepository.save(new Pet(ime, vid, vozrast, rasa, pol, opis, center, currentDateTime, url_slika, odgovorenVolonter, false)));
    }

    @Override
    @Transactional
    public void adoptPet(AppUser user, int id) {
        ZonedDateTime currentDateTime = ZonedDateTime.now();
        Pet pet = petRepository.findById(id).orElseThrow( () -> new PetNotFoundException(id) );
        pet.setAdopted(true);
        pet.setOwner(user);
        pet.setAdoptionDate(currentDateTime);
        petRepository.save(pet);
    }

    @Override
    public List<Pet> nevdomeniMilenichinja() {
        /*List<Pet> allPets = petRepository.findAll();
        List<Pet> notAdoptedPets= new ArrayList<>();
        for (Pet pet : allPets) {
            if(pet.getAdoptionDate() == null){
                notAdoptedPets.add(pet);
            }
        }*/
        return this.petRepository.findAllByAdopted(false);
    }

    @Override
    public List<Pet> vdomeniMilenichinja() {
        /*List<Pet> allPets = petRepository.findAll();
        List<Pet> adoptedPets= new ArrayList<>();
        for (Pet pet : allPets) {
            if(pet.getAdoptionDate() != null){
                adoptedPets.add(pet);
            }
        }
        return adoptedPets;*/
        return this.petRepository.findAllByAdopted(true);
    }

    @Override
    public void delete(int id) {
        if(petRepository.findById(id) != null){
            petRepository.deleteById(id);
        }
    }

    @Override
    public Optional<Pet> edit(int id, String ime, Type vid, String vozrast, String rasa, Gender pol, String opis, int id_centar, String url_slika) {

        Pet pet= petRepository.findById(id).orElseThrow( () -> new PetNotFoundException(id) );
        Center center = centerRepository.findById(id_centar).orElseThrow( () -> new PetNotFoundException(id) );

        pet.setName(ime);
        pet.setType(vid);
        pet.setAge(vozrast);
        pet.setBreed(rasa);
        pet.setGender(pol);
        pet.setDescription(opis);
        pet.setCenter(center);
        pet.setUrl(url_slika);

        return Optional.of(this.petRepository.save(pet));
    }

    @Override
    public List<Pet> search(String age, String breed, Gender gender, Type type) {
        if(!age.equals("")  && !breed.equals("") && gender != null && type != null) {
            return this.petRepository.findAllByAgeLikeAndBreedLikeAndGenderAndTypeAndAdopted("%"+age+"%","%"+breed+"%",gender,type, false);
        }
        else if(age.equals("") && !breed.equals("") && gender != null && type != null){
            return this.petRepository.findAllByBreedLikeAndGenderAndTypeAndAdopted("%"+breed+"%",gender,type, false);
        }
        else if(!age.equals("") && breed.equals("") && gender != null && type != null){
            return this.petRepository.findAllByAgeLikeAndGenderAndTypeAndAdopted("%"+age+"%",gender,type, false);
        }
        else if(!age.equals("") && !breed.equals("") && gender == null && type != null){
            return this.petRepository.findAllByAgeLikeAndBreedLikeAndTypeAndAdopted("%"+age+"%","%"+breed+"%",type, false);
        }
        else if(!age.equals("") && !breed.equals("") && gender != null && type == null){
            return this.petRepository.findAllByAgeLikeAndBreedLikeAndGenderAndAdopted("%"+age+"%","%"+breed+"%",gender, false);
        }
        else if(age.equals("") && breed.equals("") && gender != null && type != null){
            return this.petRepository.findAllByGenderAndTypeAndAdopted(gender, type, false);
        }
        else if(age.equals("") && !breed.equals("") && gender == null && type != null){
            return this.petRepository.findAllByBreedLikeAndTypeAndAdopted("%"+breed+"%", type, false);
        }
        else if(age.equals("") && !breed.equals("") && gender != null && type == null){
            return this.petRepository.findAllByBreedLikeAndGenderAndAdopted("%"+breed+"%", gender, false);
        }
        else if(!age.equals("") && breed.equals("") && gender == null && type != null){
            return this.petRepository.findAllByAgeLikeAndTypeAndAdopted("%"+age+"%", type, false);
        }
        else if(!age.equals("") && breed.equals("") && gender != null && type == null){
            return this.petRepository.findAllByAgeLikeAndGenderAndAdopted("%"+age+"%", gender, false);
        }
        else if(!age.equals("") && !breed.equals("") && gender == null && type == null){
            return this.petRepository.findAllByAgeLikeAndBreedLikeAndAdopted(age,breed, false);
        }
        else if(age.equals("") && breed.equals("") && gender == null && type != null){
            return this.petRepository.findAllByTypeAndAdopted(type, false);
        }
        else if(age.equals("") && breed.equals("") && gender != null && type == null){
            return this.petRepository.findAllByGenderAndAdopted(gender, false);
        }
        else if(age.equals("") && !breed.equals("") && gender == null && type == null){
            return this.petRepository.findAllByBreedLikeAndAdopted("%"+breed+"%", false);
        }
        else if(!age.equals("") && breed.equals("") && gender == null && type == null){
            return this.petRepository.findAllByAgeLikeAndAdopted("%"+age+"%", false);
        }
        else{
            return nevdomeniMilenichinja();
        }
    }

    @Override
    public List<Pet> searchAdopted(Integer id, String username) {
       // AppUser user = appUserRepository.findByUsername(username).orElseThrow();
        if(id != null && !username.equals("")) {
            return this.petRepository.findAllByAdoptedAndOwnerUsernameAndId(true,"%"+username+"%",id);
        }
        else if(id == null && !username.equals("")){
            return this.petRepository.findAllByAdoptedAndOwnerUsername(true,"%"+username+"%");
        }
        else if(id != null && username .equals("")){
            return this.petRepository.findAllByAdoptedAndId(true,id);
        }
        else{
            return vdomeniMilenichinja();
        }
    }

    @Override
    public Optional<Pet> findById(int id) {
        return petRepository.findById(id);
    }

}
