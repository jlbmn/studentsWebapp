package fr.formation.inti.controller;

import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestAttribute;
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
	 * à partir de la page user "profile.html"
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/fileUploadPage", method = RequestMethod.POST)
	public String getUploadFilePage(Model model,
			@RequestParam Integer userId) {
		Optional<User> user = userService.findById(userId);
		if(user.isPresent()) {
			model.addAttribute("author", user.get());
		}
		return "fileUpload";
	}
	
	
	
	/**
	 * ajouter une nouvelle fiche
	 * via formulaire 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/addNewFile", method = RequestMethod.POST)
	public String postUploadFile(Model model,
			@ModelAttribute Fiche fiche,
			@RequestParam Integer userId) {
		User author = userService.findById(userId).get();
		// +1 au nombre de publication de l'auteur
		author.setPublicationNb(author.getPublicationNb()+1);
		
		fiche.setPdfFile("media/civilisation_anglosaxonne.pdf");
		fiche.setUser(author);
		fiche.setPublicationDate(new Date());
		fiche.setUpdateDate(new Date());
		fiche.setLike(0);
		
		ficheService.save(fiche);
		
		model.addAttribute("message", "La fiche a bien été enregistrée");
		model.addAttribute("author", author);
		
		return "profile";
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

    @RequestMapping(value="/register", method = RequestMethod.GET)
    public String register (Model model) {
    	return "register";
    }
    
    @RequestMapping(value="/register", method = RequestMethod.POST)
    public String postRegister (Model model, 
    		@ModelAttribute User user,
    		@RequestParam String confirm_password) {
      
        if(user == null) {
    		  log.info("user is null");
    		return "register";
    	}
    	
    	if(!user.getPassword().equals(confirm_password)){
    		log.info("passwords are different");
    		return "register";
    	}
    	
    	user.setPublicationNb(0);
    	user.setSubscribeDate(new Date());
    	userService.save(user);
    	model.addAttribute("message", "Votre inscription est un succès !");
    	return "login";
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
			User author = ficheService.findById(Integer.parseInt(ficheId)).get().getUser();
			// mettre à jour le nombre de publications de l'auteur
			author.setPublicationNb(author.getPublicationNb()-1);
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
    
    @RequestMapping(value="/profile", method = RequestMethod.GET)
    public String getMyPage (Model model) {
		return "profile";
    }
    
    @RequestMapping(value="/profile", method = RequestMethod.POST)
    public String getAuthorProfile (Model model, 
    		@ModelAttribute User user ) {
    	Optional<User> author = userService.findById(user.getUserId());
		if(author.isPresent()) {
			model.addAttribute("author", author.get());
			model.addAttribute("nbLikes", ficheService.getTotalLikes(author.get()));
		} 
    	log.info("-------------- redirecting to "+author.get().getPseudo() +"'s page");
    	
    	return "profile";
    }   
    
    
    @RequestMapping(value = "/userEditPage", method = RequestMethod.POST)
	public String getUserEditPage(Model model, 
    		@RequestParam Integer userId) {
    	log.info("------> in userEditPage post");
    	Optional<User> u = userService.findById(userId);
		if(u.isPresent()) {
			model.addAttribute("user", u.get());
		}
    	return "userEdit";
    }
    
    @RequestMapping(value = "/userEdit", method = RequestMethod.POST)
	public String editUser(Model model, 
			@ModelAttribute User user,
			@RequestParam String confirm_password,
			@RequestParam Integer userId) {
    	
    	log.info("------> in userEdit post");
    	
    	if(user == null || 
    			userId == null || 
    			userService.findById(userId) == null) {
  		  return "index";
	  	}
	  	
	  	if(!user.getPassword().equals(confirm_password)){
	  		log.info("passwords are different");
	  		return "index";
	  	}
	  	
	  	User userModif = userService.findById(userId).get();
	  	
	  	
	  	userModif.setEmail(user.getEmail());
	  	userModif.setPassword(user.getPassword());
	  	userModif.setPseudo(user.getPseudo());
	  	
	  	userService.UpdateUser(userModif);
	  	
	  	model.addAttribute("message", "Les informations ont bien été modifiées");
	  	model.addAttribute("author", userModif);
    	return "profile";
    }
    
    
    
    /**
     * supprimer un utilisateur 
     * @param model
     * @param userId
     * @return
     */
	@RequestMapping(value = "/userDelete", method = RequestMethod.POST)
	public String deleteUser(Model model, @RequestParam Integer userId) {
		if(userId != null) {
			userService.deleteUser(userService.findById(userId).get());
		}
		model.addAttribute("message", "Le compte a été supprimé");
		return "index";
	}
	
}
