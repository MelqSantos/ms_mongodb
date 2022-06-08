package br.com.digisystem.entities;

import org.modelmapper.ModelMapper;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.digisystem.dtos.ProfessorDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "professores")
public class ProfessorEntity {

	@Id
	private String id;

	private String nome;
	private String cpf;
	private String email;
	private String telefone;

	public ProfessorDTO toDTO() {
		ModelMapper mapper = new ModelMapper();

		return mapper.map(this, ProfessorDTO.class);
	}
}
