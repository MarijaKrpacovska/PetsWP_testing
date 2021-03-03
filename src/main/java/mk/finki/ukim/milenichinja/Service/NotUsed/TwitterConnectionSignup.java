package mk.finki.ukim.milenichinja.Service.NotUsed;

import mk.finki.ukim.milenichinja.Models.AppUser;
import mk.finki.ukim.milenichinja.Repository.Jpa.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
/*
@Service
public class TwitterConnectionSignup implements ConnectionSignUp {

    @Autowired
    private AppUserRepository userRepository;

    @Override
    public String execute(Connection<?> connection) {
        AppUser user = new AppUser();
        user.setUsername(connection.getDisplayName());
        user.setPassword(randomAlphabetic(8));
        userRepository.save(user);
        return user.getUsername();
    }

}*/
