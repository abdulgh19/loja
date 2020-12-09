package mz.co.devtec.loja.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mz.co.devtec.loja.models.data.Pagina;

public interface PaginaRepository extends JpaRepository<Pagina, Integer> {
	
	Pagina findBySlug(String slug);
	
	@Query("select p from Pagina p where p.id != :id and p.slug = :slug")
	Pagina findBySlug(int id, String slug);
	
//	Pagina findBySlugAndIdNot(String slug, int id);
	
	List<Pagina> findAllByOrderBySortingAsc();

}
