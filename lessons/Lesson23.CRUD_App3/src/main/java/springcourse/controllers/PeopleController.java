package springcourse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import springcourse.dao.PersonDAO;
import springcourse.models.Person;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonDAO personDAO;

    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()// /people
    public String index(Model model){
        //получим всех людей из дао и передадим в view
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){//означает, что мы берем id из аргументов строки адреса
        //получим человека по id из дао и передадим его в view
        model.addAttribute("person", personDAO.show(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(Model model){ //передаем тот объект, для которого эта форма нужна
        model.addAttribute("person", new Person());
        return "people/new";
        //здесь можно было использовать @ModelAttribute, как в след. методе
    }

    @PostMapping()
    public String create(@ModelAttribute("person") Person person){//берем объект из формы
        personDAO.save(person);
        //редирект
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id){//означает, что мы берем id из аргументов строки адреса
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") Person person, @PathVariable("id") int id){
        personDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        personDAO.delete(id);
        return "redirect:/people";
    }
}
