package springcourse.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/first") //все адреса будут иметь префикс first
public class FirstController {

    @GetMapping("/hello")
    //public String helloPage(HttpServletRequest request){
    public String helloPage(@RequestParam(value = "name", required = false) String name,
                            @RequestParam(value = "surname", required = false) String surname){//в таком случае в параметры нельзя передавать null
        //String name = request.getParameter("name");
        //String surname = request.getParameter("surname");
        System.out.printf("Name: %s, surname: %s\n", name, surname);
        return "first/hello";
    }

    @GetMapping("/goodbye")
    public String goodbyePage(){
        return "first/goodbye";
    }
}
