package fr.formation.inti.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fr.formation.inti.dao.FicheDao;
import fr.formation.inti.entity.Fiche;
import fr.formation.inti.entity.User;
import fr.formation.inti.service.FicheService;

@Controller
public class IndexController {
	
	@Autowired
	FicheService ficheService;
	
	@Autowired
	FicheDao ficheDao;
	
	/**
	 * acceder a la page principale du projet 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
	public String welcome(Model model) {
		return "index";
	}
	
	/**
	 * (à supprimer par la suite)
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/results", method = RequestMethod.GET)
	public String getResults(Model model) {
		return "results";
	}
	
	/**
	 * afficher les résultats de la recherche par matière
	 * obtenue via formulaire
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/results", method = RequestMethod.POST)
	public String postResults(Model model) {
		List<Fiche> list = ficheService.findAll();
		Set<User> listAuthor = ficheDao.listAuthors();
		Set<String> listFields = ficheDao.listFields();
		model.addAttribute("list", list);
		model.addAttribute("listAuthors", listAuthor);
		model.addAttribute("listFields", listFields);
		return "results";
	}
	
	@RequestMapping(value= "/listFilteredAuthor", method=RequestMethod.POST)
	public String listCustomerByAuthor(Model model,@RequestParam String author, @RequestParam String field) {
		
		
//		List<Fiche> listAll= ficheService.findAll();
		List<Fiche> listAuthor = ficheService.findByAuthor(author);
//		List<Fiche> listField = ficheService.findByField(field);
		model.addAttribute("list", listAuthor);
//		model.addAttribute("list", listField);
		
		return "results";
	}
	
	//mettre un bouton pour le filtrage par matiere
	@RequestMapping(value= "/listFilteredField", method=RequestMethod.POST)
	public String listCustomerByField(Model model,@RequestParam String field) {

		List<Fiche> list = ficheService.findByField(field);
		model.addAttribute("list", list);
		return "results";
	}
	
	/**
	 * afficher la page de la fiche sélectionnée
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/displayPdf", method = RequestMethod.GET)
	public String displayPdf(Model model) {
		return "displayPdf";
	}
	
	/**
	 * accéder à la page fileUpload
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/fileUpload", method = RequestMethod.GET)
	public String getUploadFile(Model model) {
		return "fileUpload";
	}
	
	/**
	 * ajouter une nouvelle fiche
	 * via formulaire 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	public String postUploadFile(Model model) {
		return "fileUpload";
	}
	
	/**
	 * accéder à la page login
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getLoginPage(Model model) {
		return "login";
	}
	
	/** 
	 * vérifier la connexion
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String postCheckLogin(Model model) {
		return "login";
	}
	
	
	
}
