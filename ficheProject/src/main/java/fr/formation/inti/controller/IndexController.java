package fr.formation.inti.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.formation.inti.entity.User;
import fr.formation.inti.service.UserService;

@Controller
public class IndexController {
	
	private Log log = LogFactory.getLog(IndexController.class);
	
	@Autowired
	private UserService userService ; 
	
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
	public String postCheckLogin(Model model, @ModelAttribute User user) {
		log.info("--------- in login POST ---------");
		Optional<User> u = userService.findByEmailAndPassword(user.getEmail(), user.getPassword());
		if(!u.isPresent()) {
			log.info("--------- User is not present ---------");
			return "login";
		}
		log.info("--------- User is present, redirect to index  ---------");
		model.addAttribute("user", u.get());
		model.addAttribute("message", "Bienvenue "+ u.get().getPseudo());
		return "index";
	}
	
	/**
	 * logout
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logout (Model model, HttpServletRequest request, HttpServletResponse response,
    		RedirectAttributes redirectAttributes) {
    	log.info("--------- logout GET  ---------");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
        	log.info("--------- close user session ---------");
        	log.info(auth);
        	model.addAttribute("message", "Vous avez bien été déconnecté.");
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "login";
    }

	
}
