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

import co.com.juan.poly.data.model.OperativeOfficerEntity;
import co.com.juan.poly.service.BasicService;
import co.com.juan.poly.service.DistrictService;
import co.com.juan.poly.service.OperativeService;
import co.com.juan.poly.service.RankService;
import co.com.juan.poly.service.messages.MessagesService;
import co.com.juan.poly.service.messages.NotificationService;

@Controller
@RequestMapping("/operative")
public class OperativeController {

	@Autowired
	private OperativeService operativeService;

	@Autowired
	private RankService rankService;

	@Autowired
	private DistrictService districtService;

	@Autowired
	private BasicService basicService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private MessagesService messagesService;

	@GetMapping("/list")
	public String getDistrictsList(Model model) {
		model.addAttribute("ranksList", rankService.findAll());
		model.addAttribute("districtsList", districtService.findAll());
		String modalFormType = "operativeForm";
		model.addAttribute("formType", modalFormType);
		model.addAttribute("operativesList", operativeService.findAll());
		if (model.containsAttribute("errorValidacion")) {
			model.addAttribute("hidemsg", true);
			model.addAttribute("operative", model.asMap().get("errorValidacion"));
			model.addAttribute("org.springframework.validation.BindingResult.operative", model.asMap().get("resultado"));
			model.addAttribute("validationFailed", true); // used by javascript
			model.asMap().remove("errorValidacion");
			model.asMap().remove("resultado");
		} else {
			if (model.containsAttribute("errorData")) {
				model.addAttribute("hidemsg", true);
				model.addAttribute("operative", model.asMap().get("errorData"));
				model.addAttribute("validationFailed", true);
				model.asMap().remove("errorData");
			} else {
				if (model.containsAttribute("error")) {
					model.addAttribute("hidemsg", true);
					model.addAttribute("operative", model.asMap().get("error"));
					model.addAttribute("validationFailed", true);
					model.asMap().remove("error");
				}
			}
		}
		return "operative/list";
	}

	@GetMapping("/new")
	public String getAddNewOperativeView(Model model) {
		model.addAttribute("actionTitle", messagesService.get("poly.web.operative.add.title"));
		model.addAttribute("operative", new OperativeOfficerEntity());
		if (basicService.alreadyExist()) {
			model.addAttribute("ranksList", rankService.findAll());
			model.addAttribute("districtsList", districtService.findAll());
		} else {
			model.addAttribute("hideForm", true);
		}
		return "operative/add";
	}

	@PostMapping("/save")
	public String saveOperative(@ModelAttribute("operative") @Valid OperativeOfficerEntity operativeOfficerEntity,
			BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
		// when the operative already exists
		if (operativeService.exist(operativeOfficerEntity.getId())) {
			if (bindingResult.hasErrors()) {
				notificationService.addErrorMessage(messagesService.get("poly.web.save.validation.errormsg"));
				redirectAttributes.addFlashAttribute("errorValidacion", operativeOfficerEntity);
				redirectAttributes.addFlashAttribute("resultado", bindingResult);
			} else {
				final String[] params = { operativeOfficerEntity.getDni() };
				try {
					operativeOfficerEntity.setSalary(operativeService.salaryCalculation(operativeOfficerEntity));
					operativeService.saveOne(operativeOfficerEntity);
					notificationService.addInfoMessage(messagesService.get("poly.web.save.okmsg", params));
				} catch (DataIntegrityViolationException e) {
					notificationService
							.addErrorMessage(messagesService.get("poly.web.save.exception.duplicatemsg", params));
					redirectAttributes.addFlashAttribute("errorData", operativeOfficerEntity);
				} catch (Exception e) {
					notificationService.addErrorMessage(messagesService.get("poly.web.save.exceptionmsg"));
					redirectAttributes.addFlashAttribute("error", operativeOfficerEntity);
				}
			}
			return "redirect:/operative/list";
		} else {
			// when adding a new operative
			if (bindingResult.hasErrors()) {
				model.addAttribute("actionTitle", messagesService.get("poly.web.operative.add.title"));
				notificationService.addErrorMessage(messagesService.get("poly.web.save.validation.errormsg"));
				model.addAttribute("ranksList", rankService.findAll());
				model.addAttribute("districtsList", districtService.findAll());
				return "operative/add";
			} else {
				final String[] params = { operativeOfficerEntity.getDni() };
				try {
					operativeOfficerEntity.setSalary(operativeService.salaryCalculation(operativeOfficerEntity));
					operativeService.saveOne(operativeOfficerEntity);
					notificationService.addInfoMessage(messagesService.get("poly.web.save.okmsg", params));
				} catch (DataIntegrityViolationException e) {
					notificationService
							.addErrorMessage(messagesService.get("poly.web.save.exception.duplicatemsg", params));
					return "operative/add";
				} catch (Exception e) {
					notificationService.addErrorMessage(messagesService.get("poly.web.save.exceptionmsg"));
					return "operative/add";
				}
			}
			return "redirect:/operative/list";
		}
	}
	
	@GetMapping("/edit/{id}")
	public String getEditOperative(@PathVariable("id") Long id, Model model) {
		model.addAttribute("ranksList", rankService.findAll());
		model.addAttribute("districtsList", districtService.findAll());
		model.addAttribute("operative", operativeService.findOne(id));
		return "fragments/modal-forms :: edit_operative_form";
	}
	
	@GetMapping("/delete/{id}")
	public String getDeleteView(@PathVariable("id") Long id, Model model) {
		model.addAttribute("operative", operativeService.findOne(id));
		return "fragments/modal-forms :: delete_operative_form";
	}
	
	@PostMapping(value = "/delete")
	public String getDeleteOperative(HttpServletRequest request, Model model) {
		long id = Long.valueOf(request.getParameter("removeOperative"));
		try {
			operativeService.deleteOne(id);
			notificationService.addInfoMessage(messagesService.get("poly.web.delete.okmsg"));
		} catch (Exception e) {
			notificationService.addErrorMessage(messagesService.get("poly.web.delete.errormsg"));
		}
		return "redirect:/operative/list";
	}
	
}
