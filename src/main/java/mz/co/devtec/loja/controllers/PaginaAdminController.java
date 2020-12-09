package mz.co.devtec.loja.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mz.co.devtec.loja.models.data.Pagina;
import mz.co.devtec.loja.repository.PaginaRepository;

@Controller
@RequestMapping("/admin/paginas")
public class PaginaAdminController {
	
	@Autowired
	private PaginaRepository paginaRepository;
	
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
	
		
	@PostMapping("/adicionar")
	public String adicionar(@Valid Pagina pagina, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
		
		if (bindingResult.hasErrors()) {
			
			return "admin/paginas/adiciona";			
		}
		
		redirectAttributes.addFlashAttribute("message", "Pagina adicionada com Sucesso");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		
		String slug = pagina.getSlug() == "" ? pagina.getTitulo().toLowerCase().replace(" ", "-") : pagina.getSlug().toLowerCase().replace(" ", "-");
		
		Pagina oSlugExiste = paginaRepository.findBySlug(slug);
		
		if (oSlugExiste != null) {
			
			redirectAttributes.addFlashAttribute("message", "O slug existe, escolha outro");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			redirectAttributes.addFlashAttribute("pagina", pagina);
		}
		else {
			pagina.setSlug(slug);
			pagina.setSorting(100);
			
			paginaRepository.save(pagina);
		}
		
		return "redirect:/admin/paginas/adicionar";
	}
	
	
	@GetMapping("/actualizar/{id}")
	public String actualizar(@PathVariable int id, Model model) {
		
		Pagina pagina = paginaRepository.getOne(id);
		
		model.addAttribute("pagina", pagina);
		
		return "admin/paginas/actualiza";
	}
	
	@PostMapping("/actualizar")
	public String actualizar(@Valid Pagina pagina, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
		
		Pagina paginaActual = paginaRepository.getOne(pagina.getId());
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("tituloDaPagina", paginaActual.getTitulo());
			
			return "admin/paginas/actualiza";			
		}
		
		redirectAttributes.addFlashAttribute("message", "Pagina actualizada com Sucesso");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
		
		String slug = pagina.getSlug() == "" ? pagina.getTitulo().toLowerCase().replace(" ", "-") : pagina.getSlug().toLowerCase().replace(" ", "-");
		
		Pagina oSlugExiste = paginaRepository.findBySlug(pagina.getId(),slug);
		
		if (oSlugExiste != null) {
			
			redirectAttributes.addFlashAttribute("message", "O slug existe, escolha outro");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			redirectAttributes.addFlashAttribute("pagina", pagina);
		}
		else {
			pagina.setSlug(slug);
		
			paginaRepository.save(pagina);
		}
		
		return "redirect:/admin/paginas/actualizar/" + pagina.getId();
	}
	
	@GetMapping("/remover/{id}")
	public String remover(@PathVariable int id, RedirectAttributes redirectAttributes) {
		
		paginaRepository.deleteById(id);
		
		redirectAttributes.addFlashAttribute("message", "Pagina removida com Sucesso");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");
				
		return "redirect:/admin/paginas";
	}
	
	

}
