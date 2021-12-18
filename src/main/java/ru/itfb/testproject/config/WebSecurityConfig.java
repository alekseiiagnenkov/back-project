package ru.itfb.testproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import ru.itfb.testproject.service.PersonRoleService;
import ru.itfb.testproject.service.PersonService;
import ru.itfb.testproject.service.RoleService;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private PersonService personService;
    private PersonRoleService personRoleService;
    private RoleService roleService;

    WebSecurityConfig(PersonService personService, PersonRoleService personRoleService, RoleService roleService) {
        this.personService = personService;
        this.personRoleService = personRoleService;
        this.roleService = roleService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/booksJSON", "/booksJSON/{id}", "/authorsJSON", "/authorsJSON/{id}", "/about", "/login", "/logout").permitAll()
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

/*        Long j = Long.valueOf(1);
        for (int i = 0; i < personService.readAll().size(); i++, j++) {
            UserDetails participant =
                    User.withDefaultPasswordEncoder()
                            .username(personService.readAll().get(i).getUsername())
                            .password(personService.readAll().get(i).getPassword())
                            .roles(roleService.read(personRoleService.getIdRoleByIdPerson(personService.read(j).getId())).toString())
                            .build();
            participants.add(participant);
        }*/

        personService.readAll()
                .forEach(participant -> participants.add(
                        User.withDefaultPasswordEncoder()
                                .username(participant.getUsername())
                                .password(participant.getPassword())
                                .roles(roleService.read(personRoleService.getIdRoleByIdPerson(participant.getId())).toString())
                                .build()));

        UserDetails r1 = participants.get(0);
        UserDetails r2 = participants.get(1);
/*        UserDetails admin =
                User.withDefaultPasswordEncoder()
                        .username("admin")
                        .password("admin")
                        .roles("ADMIN")
                        .build();
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("user")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(admin, user);*/

        return new InMemoryUserDetailsManager(participants);
    }
}
