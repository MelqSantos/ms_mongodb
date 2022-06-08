package br.com.digisystem.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.digisystem.dtos.ProfessorDTO;
import br.com.digisystem.entities.ProfessorEntity;
import br.com.digisystem.services.ProfessorService;
import lombok.Data;

@Data
@RestController
@RequestMapping("/professores")
public class ProfessorController {

	@Autowired
	private ProfessorService professorService;

	@GetMapping
	public ResponseEntity<List<ProfessorDTO>> getAll() {
		
		List<ProfessorEntity> lista = this.professorService.getAll();
		
		// Converte cada elemento da lista para DTO
		List<ProfessorDTO> listaDTO = lista.stream().map(x -> x.toDTO())
				.collect(Collectors.toList());
				
		return ResponseEntity.ok().body( listaDTO );
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProfessorDTO> getOne(@PathVariable String id) {
		
		return ResponseEntity.ok().body(this.professorService.getOne(id).toDTO()); 
	}
	
	@GetMapping("/get-by-nome/{nome}")
	public ResponseEntity<List<ProfessorDTO>> getByNome(@PathVariable String nome){
		
		List<ProfessorEntity> lista = this.professorService.getByName(nome);
		
		List<ProfessorDTO> listaDTO = new ArrayList<>();
		
		for(int i = 0; i < lista.size(); i++) {
			listaDTO.add( lista.get(i).toDTO());
		}
		return ResponseEntity.ok().body( listaDTO );
	}

	@PostMapping
	public ResponseEntity<ProfessorDTO> create(@Valid @RequestBody ProfessorDTO professor) {
		
		// Converte um ProfessorDTO para professorEntity
		ProfessorEntity professorEntity = professor.toEntity();
		
		// Cria uma usuario no banco uasando o ProfessorDTO convertido
		ProfessorEntity professorEntitySalvo = this.professorService.create(professorEntity);
		
		// Retorna o usuario DTO convertido
		return ResponseEntity.ok().body(professorEntitySalvo.toDTO());
	}

	@PatchMapping("/{id}")
	public ResponseEntity<ProfessorDTO> update(@PathVariable String id,
			@RequestBody ProfessorDTO usuario) {
		
		ProfessorEntity professorEntitySalvo = this.professorService.update(id, usuario.toEntity());
		
		return ResponseEntity.ok().body( professorEntitySalvo.toDTO() );
	}
	

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		this.professorService.delete(id);
		
		return ResponseEntity.ok().build();
	}
}
