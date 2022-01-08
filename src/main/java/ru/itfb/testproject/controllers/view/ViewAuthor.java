package ru.itfb.testproject.controllers.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itfb.testproject.controllers.AuthorsController;
import ru.itfb.testproject.model.Author;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("authors")
public class ViewAuthor {

    private final AuthorsController authorsController;

    public ViewAuthor(AuthorsController authorsController) {
        this.authorsController = authorsController;
    }

    @GetMapping
    public String getAuthors(Model model) {
        model.addAttribute("authors", authorsController.getAuthors());
        return "authors";
    }

    //@GetMapping("{id}")
    @RequestMapping (value = "{id}", method = RequestMethod.GET)
    public String getOne(@PathVariable String id, Model model) {
        Author author = authorsController.getOne(id);
        model.addAttribute("author", author);
        model.addAttribute("model", model);
        model.addAttribute("view", this);
        return "author";
    }

/*    @PostMapping
    public String create(@RequestBody Author author) {
        author.setId(-1L);
        authorService.create(author);
        return author.toMap();
    }

    @PutMapping("{id}")
    public String update(@PathVariable String id, @RequestBody Author author) {
        if (authorService.hasAuthor(author))
            if (authorService.getAuthorId(author) == Long.parseLong(id, 10))
                authorService.update(author, Long.parseLong(id, 10));
        return author.toMap();
    }*/

    @RequestMapping (value = "{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable String id, HttpServletRequest request) {
        authorsController.delete(id);

        Path link = Paths.get(request.getHeader("Referer")).getParent();

        return "redirect:"+ link;
    }
}
