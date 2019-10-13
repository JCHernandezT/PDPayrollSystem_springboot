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

import co.com.juan.poly.data.model.PositionEntity;
import co.com.juan.poly.service.AdministrativeService;
import co.com.juan.poly.service.PositionService;
import co.com.juan.poly.service.messages.MessagesService;
import co.com.juan.poly.service.messages.NotificationService;

/**
 * Controller used for managing tasks related to positions in the application.
 * 
 * @author Juan Camilo Hernández.
 */
@Controller
@RequestMapping("/position")
public class PositionController {

	@Autowired
	private PositionService positionService;
	
	@Autowired
	private AdministrativeService administrativeService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private MessagesService messagesService;

	@GetMapping("/list")
	public String getPositionsList(Model model) {
		String modalFormType = "positionForm";
		model.addAttribute("formType", modalFormType);
		model.addAttribute("positionsList", positionService.findAll());
		if (model.containsAttribute("errorValidacion")) {
			model.addAttribute("hidemsg", true);
			model.addAttribute("position", model.asMap().get("errorValidacion"));
			model.addAttribute("org.springframework.validation.BindingResult.position", model.asMap().get("resultado"));
			model.addAttribute("validationFailed", true); // used by javascript
			model.asMap().remove("errorValidacion");
			model.asMap().remove("resultado");
		} else {
			if (model.containsAttribute("errorData")) {
				model.addAttribute("hidemsg", true);
				model.addAttribute("position", model.asMap().get("errorData"));
				model.addAttribute("validationFailed", true);
				model.asMap().remove("errorData");
			} else {
				if (model.containsAttribute("error")) {
					model.addAttribute("hidemsg", true);
					model.addAttribute("position", model.asMap().get("error"));
					model.addAttribute("validationFailed", true);
					model.asMap().remove("error");
				}
			}
		}
		return "position/list";
	}

	@GetMapping("/new")
	public String getAddNewPositionView(Model model) {
		model.addAttribute("actionTitle", messagesService.get("poly.web.position.add.title"));
		model.addAttribute("position", new PositionEntity());
		return "position/add";
	}

	@PostMapping("/save")
	public String savePosition(@ModelAttribute("position") @Valid PositionEntity positionEntity,
			BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
		// when the position already exists
		if (positionService.exist(positionEntity.getId())) {
			if (bindingResult.hasErrors()) {
				notificationService.addErrorMessage(messagesService.get("poly.web.save.validation.errormsg"));
				redirectAttributes.addFlashAttribute("errorValidacion", positionEntity);
				redirectAttributes.addFlashAttribute("resultado", bindingResult);
			} else {
				final String[] params = { positionEntity.getName() };
				try {
					positionService.saveOne(positionEntity);

					// re-calculate administrative officers' salary
					administrativeService.recalculateSalaries();

					notificationService.addInfoMessage(messagesService.get("poly.web.save.okmsg", params));
				} catch (DataIntegrityViolationException e) {
					notificationService
							.addErrorMessage(messagesService.get("poly.web.save.exception.duplicatemsg", params));
					redirectAttributes.addFlashAttribute("errorData", positionEntity);
				} catch (Exception e) {
					notificationService.addErrorMessage(messagesService.get("poly.web.save.exceptionmsg"));
					redirectAttributes.addFlashAttribute("error", positionEntity);
				}
			}
			return "redirect:/position/list";
		} else {
			// when adding a new position
			if (bindingResult.hasErrors()) {
				model.addAttribute("actionTitle", messagesService.get("poly.web.position.add.title"));
				notificationService.addErrorMessage(messagesService.get("poly.web.save.validation.errormsg"));
				return "position/add";
			} else {
				final String[] params = { positionEntity.getName() };
				try {
					positionService.saveOne(positionEntity);
					notificationService.addInfoMessage(messagesService.get("poly.web.save.okmsg", params));
				} catch (DataIntegrityViolationException e) {
					notificationService
							.addErrorMessage(messagesService.get("poly.web.save.exception.duplicatemsg", params));
					return "position/add";
				} catch (Exception e) {
					notificationService.addErrorMessage(messagesService.get("poly.web.save.exceptionmsg"));
					return "position/add";
				}
			}
			return "redirect:/position/list";
		}
	}
	
	@GetMapping("/edit/{id}")
	public String getEditPosition(@PathVariable("id") Long id, Model model) {
		model.addAttribute("position", positionService.findOne(id));
		return "fragments/modal-forms :: edit_position_form";
	}
	
	@GetMapping("/delete/{id}")
	public String getDeleteView(@PathVariable("id") Long id, Model model) {
		model.addAttribute("position", positionService.findOne(id));
		return "fragments/modal-forms :: delete_position_form";
	}
	
	@PostMapping(value = "/delete")
	public String getDeletePosition(HttpServletRequest request, Model model) {
		long id = Long.valueOf(request.getParameter("removePosition"));
		try {
			positionService.deleteOne(id);
			notificationService.addInfoMessage(messagesService.get("poly.web.delete.okmsg"));
		} catch (Exception e) {
			notificationService.addErrorMessage(messagesService.get("poly.web.delete.errormsg"));
		}
		return "redirect:/position/list";
	}
	
}
