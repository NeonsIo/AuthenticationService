package io.neons.authenticationservice.security.user;

import io.neons.authenticationservice.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;

public class RestUserDetails implements UserDetails {

    public RestUserDetails() {
        RestTemplate restTemplate = new RestTemplate();
        User quote = restTemplate.getForObject("http://www.mocky.io/v2/59b54b092500006f0c48d722", User.class);
        System.out.print(quote);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return "$2a$04$HfHdv8wopgWsGmK5hRJdeOgCzMCWlltRpECDRVUwBimj1riEbY0Um";
    }

    @Override
    public String getUsername() {
        return "test";
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
