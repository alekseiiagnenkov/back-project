package ru.itfb.testproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import ru.itfb.testproject.service.PersonRoleService;
import ru.itfb.testproject.service.PersonService;
import ru.itfb.testproject.service.RoleService;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PersonService personService;
    private final PersonRoleService personRoleService;
    private final RoleService roleService;

    @Autowired
    WebSecurityConfig(PersonService personService, PersonRoleService personRoleService, RoleService roleService) {
        this.personService = personService;
        this.personRoleService = personRoleService;
        this.roleService = roleService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers( "/books", "/books/{id}", "/authors", "/authors/{id}", "/about", "/login").permitAll()
                .antMatchers(HttpMethod.POST, "/**").permitAll()
                .antMatchers(HttpMethod.GET, "/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/**").permitAll()

                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        List<UserDetails> participants = new ArrayList<>();

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        personService.readAll()
                .forEach(participant -> participants.add(
                                User.builder()
                                        .username(participant.getUsername())
                                        .password(encoder.encode(participant.getPassword()))
                                        .roles(roleService.read(personRoleService.getIdRoleByIdPerson(participant.getId())).toString())
                                        .build()
                        )
                );

        return new InMemoryUserDetailsManager(participants);
    }
}
