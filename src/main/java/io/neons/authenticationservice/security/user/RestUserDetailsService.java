package io.neons.authenticationservice.security.user;

import io.neons.authenticationservice.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.client.RestOperations;

public class RestUserDetailsService implements UserDetailsService {
    private final RestOperations restOperations;

    @Value("security.url.load_user_by_username")
    private String loadUserByUsernameUrl;

    public RestUserDetailsService(RestOperations restOperations)
    {
        this.restOperations = restOperations;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.restOperations.getForObject(loadUserByUsernameUrl, User.class);
        return new RestUserDetails();
    }
}
