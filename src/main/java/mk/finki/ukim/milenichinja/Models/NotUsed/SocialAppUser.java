package mk.finki.ukim.milenichinja.Models.NotUsed;


import lombok.Data;
import mk.finki.ukim.milenichinja.Models.Enums.City;
import mk.finki.ukim.milenichinja.Models.Enums.SocialMediaService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.*;
/*
@Data
@Entity
@Table(name = "social_app_user", schema = "vdomuvanje_milenichinja_wp")
public class SocialAppUser extends SocialUser {
    @Id
    private String username;
    private String name;
    private String lastname;
    private  String email;
    private String password;

    @Enumerated(value = EnumType.STRING)
    private SocialMediaService socialSignInProvider;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Enumerated(value = EnumType.STRING)
    private City city;


    public SocialAppUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }


       public static class Builder {

           private Long id;

           private String username;

           private String firstName;

           private String lastName;

           private String password;

           private Role role;

           private SocialMediaService socialSignInProvider;

           private Set<GrantedAuthority> authorities;

           public Builder() {
               this.authorities = new HashSet<>();
           }

           public Builder firstName(String firstName) {
               this.firstName = firstName;
               return this;
           }

           public Builder id(Long id) {
               this.id = id;
               return this;
           }

           public Builder lastName(String lastName) {
               this.lastName = lastName;
               return this;
           }

           public Builder password(String password) {
               if (password == null) {
                   password = "SocialUser";
               }

               this.password = password;
               return this;
           }

           public Builder role(Role role) {
               this.role = role;

               SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.toString());
               this.authorities.add(authority);

               return this;
           }

           public Builder socialSignInProvider(SocialMediaService socialSignInProvider) {
               this.socialSignInProvider = socialSignInProvider;
               return this;
           }

           public Builder username(String username) {
               this.username = username;
               return this;
           }

           public SocialAppUser build() {
               SocialAppUser user = new SocialAppUser(username, password, authorities);

               user.username = username;
               user.name = firstName;
               user.lastname = lastName;
               user.role = role;
               user.socialSignInProvider = socialSignInProvider;

               return user;
           }
        }
}*/
