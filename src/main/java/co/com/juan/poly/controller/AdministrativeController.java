package co.com.juan.poly.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.com.juan.poly.data.model.AdministrativeOfficerEntity;
import co.com.juan.poly.service.AdministrativeService;
import co.com.juan.poly.service.BasicService;
import co.com.juan.poly.service.PositionService;
import co.com.juan.poly.service.RankService;
import co.com.juan.poly.service.messages.MessagesService;
import co.com.juan.poly.service.messages.NotificationService;

/**
 * Controller used for tasks related to officers management.
 * 
 * @author Juan Hernández
 */
@Controller
@RequestMapping("/administrative")
public class AdministrativeController {
		
	@Autowired
	private AdministrativeService administrativeService;
	
	@Autowired
	private RankService rankService;
	
	@Autowired
	private PositionService positionService;
	
	@Autowired
	private BasicService basicService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private MessagesService messagesService;
	
	@GetMapping("/list")
	public String getDistrictsList(Model model) {
		model.addAttribute("ranksList", rankService.findAll());
		model.addAttribute("positionsList", positionService.findAll());
		String modalFormType = "administrativeForm";
		model.addAttribute("formType", modalFormType);
		model.addAttribute("administrativesList", administrativeService.findAll());
		if (model.containsAttribute("errorValidacion")) {
			model.addAttribute("hidemsg", true);
			model.addAttribute("administrative", model.asMap().get("errorValidacion"));
			model.addAttribute("org.springframework.validation.BindingResult.administrative", model.asMap().get("resultado"));
			model.addAttribute("validationFailed", true); // used by javascript
			model.asMap().remove("errorValidacion");
			model.asMap().remove("resultado");
		} else {
			if (model.containsAttribute("errorData")) {
				model.addAttribute("hidemsg", true);
				model.addAttribute("administrative", model.asMap().get("errorData"));
				model.addAttribute("validationFailed", true);
				model.asMap().remove("errorData");
			} else {
				if (model.containsAttribute("error")) {
					model.addAttribute("hidemsg", true);
					model.addAttribute("administrative", model.asMap().get("error"));
					model.addAttribute("validationFailed", true);
					model.asMap().remove("error");
				}
			}
		}
		return "administrative/list";
	}
	
	@GetMapping("/new")
	public String getAddNewAdministrativeView(Model model) {
		model.addAttribute("actionTitle", messagesService.get("poly.web.administrative.add.title"));
		model.addAttribute("administrative", new AdministrativeOfficerEntity());
		if (basicService.alreadyExist()) {
			model.addAttribute("ranksList", rankService.findAll());
			model.addAttribute("positionsList", positionService.findAll());
		} else {
			model.addAttribute("hideForm", true);
		}
		return "administrative/add";
	}
	
	@PostMapping("/save")
	public String saveAdministrative(@ModelAttribute("administrative") @Valid AdministrativeOfficerEntity administrativeOfficerEntity,
			BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
		// when the administrative already exists
		if (administrativeService.exist(administrativeOfficerEntity.getId())) {
			if (bindingResult.hasErrors()) {
				notificationService.addErrorMessage(messagesService.get("poly.web.save.validation.errormsg"));
				redirectAttributes.addFlashAttribute("errorValidacion", administrativeOfficerEntity);
				redirectAttributes.addFlashAttribute("resultado", bindingResult);
			} else {
				final String[] params = { administrativeOfficerEntity.getDni() };
				try {
					administrativeOfficerEntity.setSalary(administrativeService.salaryCalculation(administrativeOfficerEntity));
					administrativeService.saveOne(administrativeOfficerEntity);
					notificationService.addInfoMessage(messagesService.get("poly.web.save.okmsg", params));
				} catch (DataIntegrityViolationException e) {
					notificationService
							.addErrorMessage(messagesService.get("poly.web.save.exception.duplicatemsg", params));
					redirectAttributes.addFlashAttribute("errorData", administrativeOfficerEntity);
				} catch (Exception e) {
					notificationService.addErrorMessage(messagesService.get("poly.web.save.exceptionmsg"));
					redirectAttributes.addFlashAttribute("error", administrativeOfficerEntity);
				}
			}
			return "redirect:/administrative/list";
		} else {
			// when adding a new administrative
			if (bindingResult.hasErrors()) {
				model.addAttribute("actionTitle", messagesService.get("poly.web.operative.add.title"));
				notificationService.addErrorMessage(messagesService.get("poly.web.save.validation.errormsg"));
				model.addAttribute("ranksList", rankService.findAll());
				model.addAttribute("positionsList", positionService.findAll());
				return "administrative/add";
			} else {
				final String[] params = { administrativeOfficerEntity.getDni() };
				try {
					administrativeOfficerEntity.setSalary(administrativeService.salaryCalculation(administrativeOfficerEntity));
					administrativeService.saveOne(administrativeOfficerEntity);
					notificationService.addInfoMessage(messagesService.get("poly.web.save.okmsg", params));
				} catch (DataIntegrityViolationException e) {
					notificationService
							.addErrorMessage(messagesService.get("poly.web.save.exception.duplicatemsg", params));
					return "administrative/add";
				} catch (Exception e) {
					notificationService.addErrorMessage(messagesService.get("poly.web.save.exceptionmsg"));
					return "administrative/add";
				}
			}
			return "redirect:/administrative/list";
		}
	}	
	
	@GetMapping("/edit/{id}")
	public String getEditAdministrative(@PathVariable("id") Long id, Model model) {
		model.addAttribute("ranksList", rankService.findAll());
		model.addAttribute("positionsList", positionService.findAll());
		model.addAttribute("administrative", administrativeService.findOne(id));
		return "fragments/modal-forms :: edit_administrative_form";
	}
	
	@GetMapping("/delete/{id}")
	public String getDeleteView(@PathVariable("id") Long id, Model model) {
		model.addAttribute("administrative", administrativeService.findOne(id));
		return "fragments/modal-forms :: delete_administrative_form";
	}
	
	@PostMapping(value = "/delete")
	public String getDeleteAdministrative(HttpServletRequest request, Model model) {
		long id = Long.valueOf(request.getParameter("removeAdministrative"));
		try {
			administrativeService.deleteOne(id);
			notificationService.addInfoMessage(messagesService.get("poly.web.delete.okmsg"));
		} catch (Exception e) {
			notificationService.addErrorMessage(messagesService.get("poly.web.delete.errormsg"));
		}
		return "redirect:/administrative/list";
	}
	
}
