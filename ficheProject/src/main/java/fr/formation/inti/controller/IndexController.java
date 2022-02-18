package fr.formation.inti.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {
	
	@RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
	public String welcome(Model model) {
		return "index";
	}
	
	@RequestMapping(value = "/results", method = RequestMethod.GET)
	public String results(Model model) {
		return "results";
	}
	
	@RequestMapping(value = "/displayPdf", method = RequestMethod.GET)
	public String displayPdf(Model model) {
		return "displayPdf";
	}
	
	@RequestMapping(value = "/fileUpload", method = RequestMethod.GET)
	public String uploadFile(Model model) {
		return "fileUpload";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model) {
		return "login";
	}
	
	
	
}
