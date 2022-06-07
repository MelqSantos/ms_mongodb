package br.com.digisystem.entities;

import org.modelmapper.ModelMapper;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.digisystem.dtos.UsuarioDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "usuarios")
public class UsuarioEntity {
	
	@Id
	private String id;
	
	private String nome;
	
	private String email;
	
	public UsuarioDTO toDTO() {
		ModelMapper mapper = new ModelMapper();
		
		return mapper.map(this, UsuarioDTO.class);
	}
}
