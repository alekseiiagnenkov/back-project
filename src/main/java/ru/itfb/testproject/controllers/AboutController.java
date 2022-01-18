package ru.itfb.testproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itfb.testproject.entity.Person;
import ru.itfb.testproject.entity.dto.PersonRoleDTO;
import ru.itfb.testproject.service.PersonRoleService;
import ru.itfb.testproject.service.PersonService;
import ru.itfb.testproject.service.RoleService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AboutController {

    private final PersonService personService;
    private final PersonRoleService personRoleService;
    private final RoleService roleService;

    public AboutController(PersonService personService, PersonRoleService personRoleService, RoleService roleService) {
        this.personService = personService;
        this.personRoleService = personRoleService;
        this.roleService = roleService;
    }

    /**
     * Дает информацию об авторизированном пользователе
     * @param request чтобы получить информацию о пользователе
     * @param model чтобы прокинуть Person в html код
     * @return страницу about.html
     */
    @RequestMapping(value = "about", method = RequestMethod.GET)
    public String about(HttpServletRequest request, Model model) {
        Person person = personService.findPersonByUsername(request.getRemoteUser());

        if (person != null) {
            PersonRoleDTO personRoleDTO = new PersonRoleDTO(person.getUsername(), person.getPassword(), roleService.read(personRoleService.getIdRoleByIdPerson(person.getId())).getRole());
            model.addAttribute("person", personRoleDTO);
        } else{
            PersonRoleDTO personRoleDTO = new PersonRoleDTO("", "", "");
            model.addAttribute("person", personRoleDTO);
        }
        return "about";
    }

    /**
     * Приветствие
     * @return welcome.html
     */
    @RequestMapping( method = RequestMethod.GET)
    public String main() {
        return "welcome";
    }
}
