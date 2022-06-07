package br.com.digisystem.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

import br.com.digisystem.entities.UsuarioEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {

	private String id;
	
	@NotEmpty(message = "O Campo nome é obrigatório")
	@NotBlank(message = "O Campo nome não pode ser vazio")
	@Length(min = 3, message="O campo nome deve possuir no mínimo 3 caracteres")
	private String nome;
	
	private String email;
	
	// Converte DTO em Entity
	public UsuarioEntity toEntity() {
		ModelMapper mapper = new ModelMapper();
		
		return mapper.map(this, UsuarioEntity.class);
	}
}
