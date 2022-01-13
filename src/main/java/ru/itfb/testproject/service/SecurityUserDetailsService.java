/*package ru.itfb.testproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itfb.testproject.entity.Person;
import ru.itfb.testproject.repositories.PersonRepository;

@Service
public class SecurityUserDetailsService implements UserDetailsService {
    @Autowired
    private PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return personRepository.findUserByUsername(username).get();
    }

    public void createUser(UserDetails user) {
        personRepository.save((Person) user);
    }
}*/
