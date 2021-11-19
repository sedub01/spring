package com.sedub01.blog.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**Отвечает за обработку всех переходов на сайте */
@Controller
public class MainController {
    //указывается, какой именно URL адрес мы сейчас обрабатываем
	@GetMapping("/") //"/" - главная страница
    //при переходе на главную страницу вызывается данный url
	public String home(Model model) {
		model.addAttribute("title", "Главная страница");
		return "home"; //если мы перешли по вот такому url-адресу,
		//то вызывается шаблон "home"
	}

}
