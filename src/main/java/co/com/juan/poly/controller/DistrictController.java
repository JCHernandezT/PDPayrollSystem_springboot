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

import co.com.juan.poly.data.model.DistrictEntity;
import co.com.juan.poly.service.DistrictService;
import co.com.juan.poly.service.OperativeService;
import co.com.juan.poly.service.messages.MessagesService;
import co.com.juan.poly.service.messages.NotificationService;

/**
 * Controller used for managing tasks related to Districts in the application.
 * 
 * @author Juan Camilo Hernández
 * 		
 */
@Controller
@RequestMapping("/district")
public class DistrictController {
	
	/**
	 * Reference to District Entities Service.
	 */
	@Autowired
	private DistrictService districtService;
	
	@Autowired
	private OperativeService operativeService;
	
	/**
	 * Reference to Notifications Service.
	 */
	@Autowired
	private NotificationService notificationService;
	
	/**
	 * Reference to Messages Service.
	 */
	@Autowired
	private MessagesService messagesService;
	
	/**
	 * Gets the view with a list of districts.
	 * 
	 * @param model
	 *            the model.
	 * @return the districts list view.
	 */
	@GetMapping("/list")
	public String getDistrictsList(Model model) {
		String modalFormType = "districtForm";
		model.addAttribute("formType", modalFormType);
		model.addAttribute("districtsList", districtService.findAll());
		if (model.containsAttribute("errorValidacion")) {
			model.addAttribute("hidemsg", true);
			model.addAttribute("district", model.asMap().get("errorValidacion"));
			model.addAttribute("org.springframework.validation.BindingResult.district", model.asMap().get("resultado"));
			model.addAttribute("validationFailed", true);	// used by javascript
			model.asMap().remove("errorValidacion");
			model.asMap().remove("resultado");
		} else {
			if (model.containsAttribute("errorData")) {
				model.addAttribute("hidemsg", true);
				model.addAttribute("district", model.asMap().get("errorData"));
				model.addAttribute("validationFailed", true);
				model.asMap().remove("errorData");
			} else {
				if (model.containsAttribute("error")) {
					model.addAttribute("hidemsg", true);
					model.addAttribute("district", model.asMap().get("error"));
					model.addAttribute("validationFailed", true);
					model.asMap().remove("error");
				}
			}
		}
		return "district/list";
	}
	
	/**
	 * Gets the view for adding a new district.
	 * 
	 * @param model
	 *            the model.
	 * @return the add district view.
	 */
	@GetMapping("/new")
	public String getAddNewDistrictView(Model model) {
		model.addAttribute("actionTitle", messagesService.get("poly.web.district.add.title"));
		model.addAttribute("district", new DistrictEntity());
		return "district/add";
	}
	
	/**
	 * Saves a district.
	 * 
	 * @param districtEntity
	 *            the district information.
	 * @param bindingResult
	 *            validation outcomes.
	 * @param model
	 *            the model.
	 * @return the add new district view if validation fails or the districts
	 *         list view otherwise.
	 */
	@PostMapping("/save")
	public String saveDistrict(@ModelAttribute("district") @Valid DistrictEntity districtEntity,
			BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
		// when the district already exists
		if (districtService.exist(districtEntity.getId())) {
			if (bindingResult.hasErrors()) {
				notificationService.addErrorMessage(messagesService.get("poly.web.save.validation.errormsg"));
				redirectAttributes.addFlashAttribute("errorValidacion", districtEntity);
				redirectAttributes.addFlashAttribute("resultado", bindingResult);
			} else {
				final String[] params = { districtEntity.getName() };
				try {
					districtService.saveOne(districtEntity);
					
					// re-calculate operative officers' salary
					operativeService.recalculateSalaries();
					
					notificationService.addInfoMessage(messagesService.get("poly.web.save.okmsg", params));
				} catch (DataIntegrityViolationException e) {
					notificationService
							.addErrorMessage(messagesService.get("poly.web.save.exception.duplicatemsg", params));
					redirectAttributes.addFlashAttribute("errorData", districtEntity);
				} catch (Exception e) {
					notificationService.addErrorMessage(messagesService.get("poly.web.save.exceptionmsg"));
					redirectAttributes.addFlashAttribute("error", districtEntity);
				}
			}
			return "redirect:/district/list";
		} else {
			// when adding a new district
			if (bindingResult.hasErrors()) {
				model.addAttribute("actionTitle", messagesService.get("poly.web.district.add.title"));
				notificationService.addErrorMessage(messagesService.get("poly.web.save.validation.errormsg"));
				return "district/add";
			} else {
				final String[] params = { districtEntity.getName() };
				try {
					districtService.saveOne(districtEntity);
					notificationService.addInfoMessage(messagesService.get("poly.web.save.okmsg", params));
				} catch (DataIntegrityViolationException e) {
					notificationService
							.addErrorMessage(messagesService.get("poly.web.save.exception.duplicatemsg", params));
					return "district/add";
				} catch (Exception e) {
					notificationService.addErrorMessage(messagesService.get("poly.web.save.exceptionmsg"));
					return "district/add";
				}
			}
			return "redirect:/district/list";
		}
	}
	
	/**
	 * Gets the view to edit a district.
	 * 
	 * @param id
	 *            the identifier of the district.
	 * @param model
	 *            the model.
	 * @return the edit district view.
	 */
	@GetMapping("/edit/{id}")
	public String getEditDistrict(@PathVariable("id") Long id, Model model) {
		model.addAttribute("district", districtService.findOne(id));
		return "fragments/modal-forms :: edit_district_form";
	}
	
	/**
	 * Gets the view to district deletion confirmation.
	 * 
	 * @param id
	 *            the identifier of the district.
	 * @param model
	 *            the model.
	 * @return the confirm district deletion view.
	 */
	@GetMapping("/delete/{id}")
	public String getDeleteView(@PathVariable("id") Long id, Model model) {
		model.addAttribute("district", districtService.findOne(id));
		return "fragments/modal-forms :: delete_district_form";
	}
	
	/**
	 * Deletes a district.
	 * 
	 * @param id
	 *            the identifier of the district.
	 * @param model
	 *            the model.
	 * @return the districts list view.
	 */
	@PostMapping(value = "/delete")
	public String getDeleteDistrict(HttpServletRequest request, Model model) {
		long id = Long.valueOf(request.getParameter("removeDistrict"));
		try {
			districtService.deleteOne(id);
			notificationService.addInfoMessage(messagesService.get("poly.web.delete.okmsg"));
		} catch (Exception e) {
			notificationService.addErrorMessage(messagesService.get("poly.web.delete.errormsg"));
		}
		return "redirect:/district/list";
	}
	
}
