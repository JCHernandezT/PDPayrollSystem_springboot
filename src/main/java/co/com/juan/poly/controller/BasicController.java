package co.com.juan.poly.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import co.com.juan.poly.data.model.BasicSalary;
import co.com.juan.poly.service.BasicService;
import co.com.juan.poly.service.messages.MessagesService;
import co.com.juan.poly.service.messages.NotificationService;

@Controller
@Secured("ROLE_ADMIN")
@RequestMapping("/basic")
public class BasicController {
	
	@Autowired
	private BasicService basicService;
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private MessagesService messagesService;
	
	@GetMapping("/new")
	public String getAddNewRankView(Model model) {
		model.addAttribute("actionTitle", messagesService.get("poly.web.basic.add.title"));	
		if (basicService.alreadyExist()) {
			model.addAttribute("basic", basicService.findBasicSalary());
		} else {
			model.addAttribute("basic", new BasicSalary());
		}
		
		return "basic/addupdate";
	}
	
	@PostMapping("/save")
	public String saveBasicSalary(@ModelAttribute("basic") @Valid BasicSalary basicSalary,
			BindingResult bindingResult, Model model) {
		model.addAttribute("actionTitle", messagesService.get("poly.web.basic.add.title"));
		if (bindingResult.hasErrors()) {
			notificationService.addErrorMessage(messagesService.get("poly.web.save.validation.errormsg"));
		} else {
			final String[] params = { basicSalary.getAmount().toString() };
			try {
				basicService.saveOne(basicSalary);
				notificationService.addInfoMessage(messagesService.get("poly.web.save.okmsg", params));
			} catch (DataIntegrityViolationException e) {
				notificationService
						.addErrorMessage(messagesService.get("poly.web.save.exception.duplicatemsg", params));
			} catch (Exception e) {
				notificationService.addErrorMessage(messagesService.get("poly.web.save.exceptionmsg"));
				
				System.out.println("\n\n" + basicSalary.getAmount() + "\n" + basicSalary.getId() + "\n\n");
				System.out.println("\n\n" + e.getMessage()  + "\n\n");
			}
		}
		return "basic/addupdate";
	}
	
}
