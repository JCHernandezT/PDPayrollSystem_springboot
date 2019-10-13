package co.com.juan.poly.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import co.com.juan.poly.service.AdministrativeService;
import co.com.juan.poly.service.OperativeService;

@Controller
@Secured("ROLE_ADMIN")
@RequestMapping("/payroll")
public class PayrollController {
	
	@Autowired
	private OperativeService operativeService;
	
	@Autowired
	private AdministrativeService administrativeService;

	@GetMapping("/calculate")
	public String ViewPayrollStats(Model model) {
		BigDecimal payAdm = administrativeService.calculateAdministrativePayroll();
		//System.out.println("\n\n" + administrativeService.calculateAdministrativePayroll() + "\n\n");
		BigDecimal payOpt = operativeService.calculateOperativePayroll();
		model.addAttribute("administrativePayroll", payAdm);
		model.addAttribute("operativePayroll", payOpt);
		model.addAttribute("totalPayroll", payAdm.add(payOpt));
		return "payroll/stats";
	}
}
