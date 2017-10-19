package io.neons.authenticationservice.security.oauth2;

import io.neons.authenticationservice.security.user.RestUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${security.oauth2.jwt.key_path}")
    private String securityOauth2JwtKeyPath;

    @Value("${security.oauth2.jwt.password}")
    private String securityOauth2JwtPassword;

    @Value("${security.oauth2.jwt.key_pair}")
    private String securityOauth2JwtKeyPair;

    @Autowired
    private DataSource dataSourceAuthorization;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(authenticationProvider)
                .jdbcAuthentication()
                .dataSource(this.dataSourceAuthorization);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        List<AuthenticationProvider> list = new ArrayList<>();
        list.add(authenticationProvider);

        return new ProviderManager(list);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new RestUserDetailsService();
    }

    @Bean
    @Autowired
    public AuthenticationProvider userAuthenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        provider.setUserDetailsService(userDetailsService);

        return provider;
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
                new FileSystemResource(securityOauth2JwtKeyPath), securityOauth2JwtPassword.toCharArray()
        );
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair(securityOauth2JwtKeyPair));

        return converter;
    }

    @Bean
    @Autowired
    public JwtTokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter) {
        return new JwtTokenStore(jwtAccessTokenConverter);
    }
}
