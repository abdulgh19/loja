package mz.co.devtec.loja.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mz.co.devtec.loja.models.data.Categoria;
import mz.co.devtec.loja.repository.CategoriaRepository;

@Controller
@RequestMapping("/admin/categorias")
public class CategoriaAdminController {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@GetMapping
	public String index(Model model) {

		List<Categoria> categorias = categoriaRepository.findAllByOrderBySortingAsc();

		model.addAttribute("categorias", categorias);

		return "admin/categorias/index";
	}

	@GetMapping("/adicionar")
	public String adicionar(Categoria categoria) {

		return "admin/categorias/adiciona";
	}

	@PostMapping("/adicionar")
	public String adicionar(@Valid Categoria categoria, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, Model model) {

		if (bindingResult.hasErrors()) {

			return "admin/categorias/adiciona";
		}

		redirectAttributes.addFlashAttribute("message", "Categoria adicionada com Sucesso");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");

		String slug = categoria.getNome().toLowerCase().replace(" ", "-");

		Categoria categoriaExiste = categoriaRepository.findByNome(categoria.getNome());

		if (categoriaExiste != null) {

			redirectAttributes.addFlashAttribute("message", "A Categoria existe, escolha outra");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
			redirectAttributes.addFlashAttribute("categoriaInfo", categoria);
		} else {
			categoria.setSlug(slug);
			categoria.setSorting(100);

			categoriaRepository.save(categoria);
		}

		return "redirect:/admin/categorias/adicionar";
	}

	@GetMapping("/actualizar/{id}")
	public String actualizar(@PathVariable int id, Model model) {

		Categoria categoria = categoriaRepository.getOne(id);

		model.addAttribute("categoria", categoria);

		return "admin/categorias/actualiza";
	}

	@PostMapping("/actualizar")
	public String actualizar(@Valid Categoria categoria, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, Model model) {

		Categoria categoriaActual = categoriaRepository.getOne(categoria.getId());

		if (bindingResult.hasErrors()) {
			model.addAttribute("nomeDaCategoria", categoriaActual.getNome());

			return "admin/categorias/actualiza";
		}

		redirectAttributes.addFlashAttribute("message", "Categoria actualizada com Sucesso");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");

		String slug = categoria.getNome().toLowerCase().replace(" ", "-");

		Categoria categoriaExiste = categoriaRepository.findByNome(categoria.getNome());

		if (categoriaExiste != null) {

			redirectAttributes.addFlashAttribute("message", "A Categoria existe, escolha outra");
			redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
		} else {
			categoria.setSlug(slug);

			categoriaRepository.save(categoria);
		}

		return "redirect:/admin/categorias/actualizar/" + categoria.getId();
	}

	@GetMapping("/remover/{id}")
	public String remover(@PathVariable int id, RedirectAttributes redirectAttributes) {

		categoriaRepository.deleteById(id);

		redirectAttributes.addFlashAttribute("message", "Categoria removida com Sucesso");
		redirectAttributes.addFlashAttribute("alertClass", "alert-success");

		return "redirect:/admin/categorias";
	}

	@PostMapping("/reorder")
	public @ResponseBody String reorder(@RequestParam("id[]") int[] id) {

		int count = 1;
		Categoria categoria;

		for (int idCategoria : id) {
			categoria = categoriaRepository.getOne(idCategoria);
			categoria.setSorting(count);
			categoriaRepository.save(categoria);
			count++;

		}
		return "ok";
	}

}
