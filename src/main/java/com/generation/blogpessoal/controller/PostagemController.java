package com.generation.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;
import com.generation.blogpessoal.repository.TemaRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/postagens")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class PostagemController {

	@Autowired
	private PostagemRepository postagemRepository;

	@Autowired
	private TemaRepository temaRepository;

	@GetMapping("/all")
	public ResponseEntity<List<Postagem>> getAll() {
		return ResponseEntity.ok(postagemRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Postagem> getById(@PathVariable Long id) {
		Optional<Postagem> postagemOptional = postagemRepository.findById(id);

		if (postagemOptional.isPresent()) {
			Postagem postagem = postagemOptional.get();
			postagem.setVisualiacao(postagem.getVisualiacao() + 1);
			postagemRepository.save(postagem);
			return ResponseEntity.ok(postagem);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo) {
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
	}

	@PostMapping
	public ResponseEntity<Postagem> post(@Valid @RequestBody Postagem postagem) {
		if (temaRepository.existsById(postagem.getTema().getId()))
			return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));

		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tema não existe", null);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Postagem> put(@PathVariable Long id, @Valid @RequestBody Postagem postagem) {
		Optional<Postagem> postagemOptional = postagemRepository.findById(id);

		if (postagemOptional.isPresent()) {
			Postagem postagemAtual = postagemOptional.get();

			// Mantém a contagem de visualizações da postagem atual
			postagem.setVisualiacao(postagemAtual.getVisualiacao());

			if (temaRepository.existsById(postagem.getTema().getId())) {
				postagem.setId(id); // Define o ID da postagem atualizada
				return ResponseEntity.ok(postagemRepository.save(postagem));
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tema não existe");
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {
		Optional<Postagem> postagem = postagemRepository.findById(id);

		if (postagem.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		postagemRepository.deleteById(id);
	}

}
