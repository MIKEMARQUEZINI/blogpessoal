package com.generation.blogpessoal.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="tb_postagens")

public class Postagem {
	
	@Id // indica que este atributo será uma chave primaria na minha tabela
	@GeneratedValue(strategy = GenerationType.IDENTITY)//indica que terá um auto-increment
	private Long id;
	
	@NotBlank(message  = "Este atributo é de preenchimento obrigatório")
	@Size(min = 5 , max = 100 , message = "Este campo tem que possuir no minimo 5 caracteres e no maximo 100 caracteres" )
	private String titulo;
	
	@NotBlank(message  = "Este atributo é de preenchimento obrigatório")
	@Size(min = 10 , max = 1000 , message = "Este campo tem que possuir no minimo 10 caracteres e no maximo 1000 caracteres" )
	private String texto;

	//contador de Views para as postagens
	private int visualiacao;

	public int getVisualiacao() {
		return visualiacao;
	}

	public void setVisualiacao(int visualiacao) {
		this.visualiacao = visualiacao;
	}

	@UpdateTimestamp
	private LocalDateTime data;
	
	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private Tema tema;
	
	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private Usuario usuario;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Tema getTema() {
		return tema;
	}

	public void setTema(Tema tema) {
		this.tema = tema;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}
	
	

}
