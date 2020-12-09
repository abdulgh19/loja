package mz.co.devtec.loja.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mz.co.devtec.loja.models.data.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
	
	Categoria findBySlug(String slug);
	
	@Query("select c from Categoria c where c.id != :id and c.slug = :slug")
	Categoria findBySlug(int id, String slug);
	
//	Categoria findBySlugAndIdNot(String slug, int id);
	
	List<Categoria> findAllByOrderBySortingAsc();
	
	Categoria findByNome(String nome);


}
