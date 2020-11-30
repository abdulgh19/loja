package mz.co.devtec.loja.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/paginas")
public class PaginaAdminController {
	
	@GetMapping
	public String index() {
		return "admin/paginas/index";
	}

}
