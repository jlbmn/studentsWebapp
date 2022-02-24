package fr.formation.inti.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.formation.inti.entity.Fiche;
import fr.formation.inti.entity.User;
import fr.formation.inti.service.FicheService;
import fr.formation.inti.service.UserService;
import fr.formation.inti.dao.FicheDao;

@Controller
public class IndexController {
	
	private Log log = LogFactory.getLog(IndexController.class);
	
	@Autowired
	private UserService userService ; 
	
	@Autowired
	private FicheService ficheService;
	
	@Autowired
	FicheDao ficheDao;
	
	/**
	 * acceder a la page principale du projet 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
	public String welcome(Model model, @RequestParam("message") Optional<String> message) {
		log.info("--------- in index GET ---------");
		
		// check if request param "message" exists
		if(message.isPresent()) {
			model.addAttribute("message", message.get());
		}
		
		Set<String> fields = ficheDao.listFields();
		model.addAttribute("fields", fields);
		
		List<Fiche> fiches = ficheService.findAll();
		model.addAttribute("fiches", fiches);
		List<User> users = userService.findAll();
		model.addAttribute("authors", users);
		
		HashMap<User, Integer> dicoAuthorLike = new HashMap<User, Integer>();
		for(User u : users) {
			dicoAuthorLike.put(u, ficheService.getTotalLikes(u));
		}
		model.addAttribute("dicoAuthorLike", dicoAuthorLike);
		
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
	public String postResults(Model model, @RequestParam String field) {
		List<Fiche> list = ficheService.findByField(field);
		model.addAttribute("list", list);
		long nb = ficheDao.countFiche(field);
		String message ="";
		if(nb==1) {
			message = nb + " fiche a été trouvée pour la matière " + field;
		}else if(nb>1) {
			message = nb + " fiches ont été trouvées pour la matière " + field;
		}
		model.addAttribute("messageResults",message);
		
		return "results";
	}
	
	
	/**
	 * afficher la page de la fiche sélectionnée
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/displayPdf", method = RequestMethod.POST)
	public String displayPdf(Model model, @ModelAttribute Fiche fiche) {
		Optional<Fiche> f = ficheService.findById(fiche.getFicheId());
		if(f.isPresent()) {
			model.addAttribute("fiche", f.get());
		} 
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
	public String getLoginPage(Model model,
			@RequestParam("messageLogout") Optional<String> messageLogout) {
		
		// check if request param "message" exists
		if(messageLogout.isPresent()) {
			log.info("---------> message logout is present");
			model.addAttribute("message", "Vous êtes bien déconnecté!");
		}
		
		return "login";
	}
	
	/** 
	 * vérifier la connexion
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView postCheckLogin(Model model, 
			@ModelAttribute User user) {
		log.info("--------- in login POST ---------");
		ModelAndView modelAndView = new ModelAndView();
		Optional<User> u = userService.findByEmailAndPassword(user.getEmail(), user.getPassword());
		if(!u.isPresent()) {
			log.info("--------- User is not present ---------");
			modelAndView.setViewName("login");
			return modelAndView;
		}
		log.info("--------- User is present, redirect to index  ---------");
		modelAndView.addObject("user", u.get());
		modelAndView.addObject("message", "Bienvenue "+ u.get().getPseudo());
		modelAndView.setViewName("redirect:/index");
		return modelAndView;
	}

	/**
	 * logout
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public ModelAndView logout (Model model, 
    		HttpServletRequest request, 
    		HttpServletResponse response,
    		RedirectAttributes redirectAttributes) {
    	log.info("--------- logout GET  ---------");
    	ModelAndView modelAndView = new ModelAndView();
    	modelAndView.setViewName("redirect:/login");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
        	log.info("--------- close user session ---------");
        	modelAndView.addObject("messageLogout", "logout");
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return modelAndView;
    }
    
    /**
	 * accéder à la page fileEdit
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/fileEdit", method = RequestMethod.GET)
	public String getEditFile(Model model, @RequestParam String ficheId) {
		Integer id = Integer.parseInt(ficheId);		
		Optional<Fiche> f = ficheService.findById(id);
	
			if(f.isPresent()) {
				model.addAttribute("fiche", f.get());
			}
		return "fileEdit";
	}
	
	/**
	 * modifier une fiche
	 * via formulaire 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/fileEdit", method = RequestMethod.POST)
	public String postEditFile(Model model, @RequestParam String ficheId,@RequestParam String title, @RequestParam String abstractText, @RequestParam String field, @RequestParam String pdfFile) {
		
		System.out.println("-------------------------------------" +ficheId);
		if(ficheId != null) {
			Integer id = Integer.parseInt(ficheId);		
			Optional<Fiche> f = ficheService.findById(id);
			
			f.get().setTitle(title);
			f.get().setAbstractText(abstractText);
			f.get().setField(field);
			
			if(!pdfFile.equals("")) {
				f.get().setPdfFile(pdfFile);
			}
				
			System.out.println("------------------------------------- 2 " +ficheId);	

			ficheService.UpdateFiche(f.get());
			
			model.addAttribute("fiche", f.get());
			
		}
		return "displayPdf";
	}
	
	/**
	 * accéder à la page fileEdit
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/fileDelete", method = RequestMethod.GET)
	public String getDeleteFile(Model model, @RequestParam String ficheId) {
		if(ficheId != null) {
			Integer id = Integer.parseInt(ficheId);		
			ficheService.deleteFiche(id);
		}
	
		return "index";
	}
	
	/**
	 * aimer une fiche
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/likeButton", method = RequestMethod.POST)
	public String postLike(Model model, @RequestParam String ficheId) {
		
		if(ficheId != null) {
			Integer id = Integer.parseInt(ficheId);		
			Optional<Fiche> f = ficheService.findById(id);
			ficheService.likeFiche(f.get());
			model.addAttribute("fiche", f.get());	
		}
		
		return "displayPdf";
	}
	
	

	
}
