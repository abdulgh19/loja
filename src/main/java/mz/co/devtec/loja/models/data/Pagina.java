package mz.co.devtec.loja.models.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "pagina")
public class Pagina {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Size(min = 3, max = 25, message = "O titulo deve ter entre {min} e {max} caracteres")
	private String titulo;
	
	private String slug;
	
	@Size(min = 3, max = 205, message = "O conteudo deve ter entre {min} e {max} caracteres")
	private String conteudo;
	
	private int sorting;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String context) {
		this.conteudo = context;
	}

	public int getSorting() {
		return sorting;
	}

	public void setSorting(int sorting) {
		this.sorting = sorting;
	}
	
	
	
	

}
