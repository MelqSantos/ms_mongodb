package br.com.digisystem.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.digisystem.entities.ProfessorEntity;
import br.com.digisystem.exceptions.ObjNotFoundException;
import br.com.digisystem.repositories.ProfessorRepository;

@Service
public class ProfessorService {
	
	@Autowired
	private ProfessorRepository professorRepository;

	public List<ProfessorEntity> getAll() {
		return this.professorRepository.findAll();
	}

	public ProfessorEntity getOne(String id) {
		return this.professorRepository.findById(id)
				.orElseThrow( () -> new ObjNotFoundException("Elemento com ID " + id +" não foi localizado"));
	}
	
	public List<ProfessorEntity> getByName(String nome) {
		return this.professorRepository.searchByNomeNativo(nome);
	}

	public ProfessorEntity create(ProfessorEntity professor) {
		ProfessorEntity prof = new ProfessorEntity();
		prof.setNome(professor.getNome());
		prof.setCpf(professor.getCpf());
		prof.setEmail(professor.getEmail());
		prof.setTelefone(professor.getTelefone());

		return this.professorRepository.save(prof);
	}

	public ProfessorEntity update(String id, ProfessorEntity professor) {

		Optional<ProfessorEntity> professorOptional = this.professorRepository.findById(id);

		if (professorOptional.isPresent()) {
			ProfessorEntity professorUpdate = professorOptional.get();
			
			professorUpdate.setNome(professor.getNome());
			professorUpdate.setCpf(professor.getCpf());
			professorUpdate.setEmail(professor.getEmail());
			professorUpdate.setTelefone(professor.getTelefone());
			
			return this.professorRepository.save(professorUpdate);
		} else {
			return null;
		}
	}
	
	public void delete(String id) {
		this.professorRepository.deleteById(id);
	}

}
