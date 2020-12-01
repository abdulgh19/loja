package mz.co.devtec.loja.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import mz.co.devtec.loja.models.data.Pagina;
import mz.co.devtec.loja.repository.PaginaRepository;

@Controller
@RequestMapping("/admin/paginas")
public class PaginaAdminController {
	
	@Autowired
	private PaginaRepository paginaRepository;
	
	public PaginaAdminController(PaginaRepository paginaRepository) {
		this.paginaRepository = paginaRepository;
	} 
	
	
	@GetMapping
	public String index(Model model) {
		
		List<Pagina> paginas = paginaRepository.findAll();
		
		model.addAttribute("paginas",paginas);
		
		return "admin/paginas/index";
	}
	
	@GetMapping("/adicionar")
	public String adicionar(@ModelAttribute Pagina pagina) {
		
		return "admin/paginas/adiciona";
	}
	
	
	

}
