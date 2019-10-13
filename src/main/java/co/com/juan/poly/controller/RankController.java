package co.com.juan.poly.controller;

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

import co.com.juan.poly.data.model.RankEntity;
import co.com.juan.poly.service.AdministrativeService;
import co.com.juan.poly.service.OperativeService;
import co.com.juan.poly.service.RankService;
import co.com.juan.poly.service.messages.MessagesService;
import co.com.juan.poly.service.messages.NotificationService;

@Controller
@RequestMapping("/rank")
public class RankController {
	
	@Autowired
	private RankService rankService;
	
	@Autowired
	private OperativeService operativeService;
	
	@Autowired
	private AdministrativeService administrativeService;
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private MessagesService messagesService;
	
	@GetMapping("/list")
	public String getRanksList(Model model) {
		model.addAttribute("ranksList", rankService.findAll());
		model.addAttribute("confirmMsg", messagesService.get("poly.web.confirm.delete"));
		return "rank/list";
	}
	
	@GetMapping("/new")
	public String getAddNewRankView(Model model) {
		model.addAttribute("actionTitle", messagesService.get("poly.web.rank.add.title"));
		model.addAttribute("rank", new RankEntity());
		return "rank/addupdate";
	}
	
	@PostMapping("/save")
	public String saveRank(@ModelAttribute("rank") @Valid RankEntity rankEntity, BindingResult bindingResult,
			Model model) {
		// when the rank already exists
		if (rankService.exist(rankEntity.getId())) {
			model.addAttribute("actionTitle", messagesService.get("poly.web.rank.edit.title"));
		} else {
			model.addAttribute("actionTitle", messagesService.get("poly.web.rank.add.title"));
		}
		
		if (bindingResult.hasErrors()) {
			notificationService.addErrorMessage(messagesService.get("poly.web.save.validation.errormsg"));
			System.out.println("\n\n" + bindingResult.toString() + "\n\n");
			return "/rank/addupdate";
		} else {
			final String[] params = { rankEntity.getName() };
			try {
				rankService.saveOne(rankEntity);
				
				// re-calculate all officers' salary
				operativeService.recalculateSalaries();
				administrativeService.recalculateSalaries();
				
				notificationService.addInfoMessage(messagesService.get("poly.web.save.okmsg", params));
			} catch (DataIntegrityViolationException e) {
				notificationService
						.addErrorMessage(messagesService.get("poly.web.save.exception.duplicatemsg", params));
				return "/rank/addupdate";
			} catch (Exception e) {
				notificationService.addErrorMessage(messagesService.get("poly.web.save.exceptionmsg"));
				return "/rank/addupdate";
			}
			return "redirect:/rank/list";
		}
		
	}
	
	@GetMapping("/edit/{id}")
	public String getEditRank(@PathVariable("id") Long id, Model model) {
		model.addAttribute("actionTitle", messagesService.get("poly.web.rank.edit.title"));
		model.addAttribute("rank", rankService.findOne(id));	
		model.addAttribute("flagEdit", true);
		return "rank/addupdate";
	}
	
	@GetMapping("/delete/{id}")
	public String getDeleteRank(@PathVariable("id") Long id, Model model) {
		try {
			rankService.deleteOne(id);
			notificationService.addInfoMessage(messagesService.get("poly.web.delete.okmsg"));
		} catch (Exception e) {
			notificationService.addErrorMessage(messagesService.get("poly.web.delete.errormsg"));
		}
		return "redirect:/rank/list";
	}
	
}
