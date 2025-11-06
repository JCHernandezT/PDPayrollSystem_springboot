package co.com.juan.poly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.com.juan.poly.data.model.OperativeOfficerEntity;
import co.com.juan.poly.service.OperativeService;

@RestController
@RequestMapping("/operative/rest")
public class OperativeRestController {

	@Autowired
	private OperativeService operativeService;

	@GetMapping("/find")
	public OperativeOfficerEntity getOfficer(@RequestParam(value = "id") Long id) {
		System.out.println("OperativeRestController getOfficer id = " + id);
		OperativeOfficerEntity officer = operativeService.findOne(id);
		System.out.println("OperativeRestController getOfficer officer = " + officer.getDni());
		return officer;
	}

}
