package ru.itfb.testproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import ru.itfb.testproject.service.PersonRoleService;
import ru.itfb.testproject.service.PersonService;
import ru.itfb.testproject.service.RoleService;
import ru.itfb.testproject.service.UserDetailsServiceImpl;

/**
 * Spring Security
 * Для авторизации пользователей
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Для считывания пользователей из БД
     */
    private final PersonService personService;
    private final PersonRoleService personRoleService;
    private final RoleService roleService;

    /**
     * Конструктор
     */
    @Autowired
    WebSecurityConfig(PersonService personService, PersonRoleService personRoleService, RoleService roleService) {
        this.personService = personService;
        this.personRoleService = personRoleService;
        this.roleService = roleService;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Передаем в конфигурацию собственные UserDetails и PasswordEncoder
     * @param auth куда передаем конфигурацию
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(new UserDetailsServiceImpl(personService, personRoleService, roleService)).passwordEncoder(new MyPasswordEncoder());
    }

    /**
     * Функция, описывающая разрешенные страницы и страницу авторизации. Так же можно указать доступ к запросам
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/books", "/books/{id}", "/authors", "/authors/{id}", "/about", "/login").permitAll()
                    .antMatchers("/users/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
                .and()
                    .formLogin().loginPage("/login").usernameParameter("username").passwordParameter("password").permitAll()
                    .successForwardUrl("/postLogin")
                .and()
                    .logout().logoutSuccessUrl("/logout").permitAll();
    }
}
