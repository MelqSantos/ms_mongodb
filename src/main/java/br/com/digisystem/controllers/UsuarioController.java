package br.com.digisystem.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.digisystem.dtos.UsuarioDTO;
import br.com.digisystem.entities.UsuarioEntity;
import br.com.digisystem.services.UsuarioService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;

	@GetMapping
	@ApiOperation(value = "Listar todos os usuários")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message="Sucesso"),
			@ApiResponse(code = 400, message="Bad Request"),
	})
	public ResponseEntity<List<UsuarioDTO>> getAll() {
		
		List<UsuarioEntity> lista = this.usuarioService.getAll();
		
		// Converte cada elemento da lista para DTO
		List<UsuarioDTO> listaDTO = lista.stream().map(x -> x.toDTO())
				.collect(Collectors.toList());
				
		return ResponseEntity.ok().body( listaDTO );
	}

	@ApiOperation(value = "Buscar usuário pelo Id")
	@GetMapping("/{id}")
	public ResponseEntity<UsuarioDTO> getOne(@PathVariable String id) {
		
		return ResponseEntity.ok().body(this.usuarioService.getOne(id).toDTO()); 
	}
	
	@GetMapping("/get-by-nome/{nome}")
	public ResponseEntity<List<UsuarioDTO>> getByNome(@PathVariable String nome){
		
		List<UsuarioEntity> lista = this.usuarioService.getByName(nome);
		
		List<UsuarioDTO> listaDTO = new ArrayList<>();
		
		for(int i = 0; i < lista.size(); i++) {
			listaDTO.add( lista.get(i).toDTO());
		}
		return ResponseEntity.ok().body( listaDTO );
	}

	@ApiOperation(value = "Adicionar novo usuário")
	@PostMapping
	public ResponseEntity<UsuarioDTO> create(@Valid @RequestBody UsuarioDTO usuario) {
		
		// Converte um usuarioDTO para usuarioEntity
		UsuarioEntity usuarioEntity = usuario.toEntity();
		
		// Cria uma usuario no banco uasando o UsuarioDTO convertido
		UsuarioEntity usuarioEntitySalvo = this.usuarioService.create(usuarioEntity);
		
		// Retorna o usuario DTO convertido
		return ResponseEntity.ok().body(usuarioEntitySalvo.toDTO());
	}

	@ApiOperation(value = "Atualizar usuário")
	@PatchMapping("/{id}")
	public ResponseEntity<UsuarioDTO> update(@PathVariable String id,
			@RequestBody UsuarioDTO usuario) {
		
		UsuarioEntity usuarioEntitySalvo = this.usuarioService.update(id, usuario.toEntity());
		
		return ResponseEntity.ok().body( usuarioEntitySalvo.toDTO() );
	}
	
	@PatchMapping("update/{id}")
	public ResponseEntity<UsuarioDTO> updateUsuario(@PathVariable String id,
			@RequestBody UsuarioDTO dto) {
		
		UsuarioEntity usuario = this.usuarioService.updateUsuario(id, dto.getNome());
		
		return ResponseEntity.ok().body(usuario.toDTO());
	}
	
	@ApiOperation(value = "Deletar usuário")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		this.usuarioService.delete(id);
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/pagination")
	public ResponseEntity<Page<UsuarioDTO>> getAllPagination(
			@RequestParam ( name= "page", defaultValue = "0" ) int page,
			@RequestParam ( name= "limit", defaultValue = "10" ) int limit
	) {
		log.info("page = {}, limit = {}", page, limit);
		
		Page<UsuarioEntity> paginado = usuarioService.getAllPagination(page, limit);
		Page<UsuarioDTO> pageDTO = paginado.map(
				new Function<UsuarioEntity, UsuarioDTO>(){
					public UsuarioDTO apply(UsuarioEntity entity) {
						return entity.toDTO();
					}
				}
			);
		
		return ResponseEntity.ok().body( pageDTO );
	}
	
}
