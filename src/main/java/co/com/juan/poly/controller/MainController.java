package co.com.juan.poly.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import co.com.juan.poly.data.model.UserEntity;

/**
 * Controller used for managing tasks related to the application start, login
 * and logout.
 * 
 * @author Juan Camilo Hernández
 */
@Controller
public class MainController {
	
	/**
	 * Gets the login view.
	 * 
	 * @param model
	 *            the model.
	 * 			
	 * @return the login view.
	 */
	@GetMapping({ "/", "/login" })
	public String getLoginView(Model model) {
		model.addAttribute("user", new UserEntity());
		return "login";
	}
	
	/**
	 * Gets the menu view.
	 * 
	 * @param model
	 *            the model.
	 * 			
	 * @return the menu view.
	 */
	@GetMapping("/menu")
	public String getMenuView(Model model) {
		return "menu";
	}
	
	@GetMapping("/access-denied")
	public String getAccessDeniedView(Model model) {
		return "access-denied";
	}
	
}