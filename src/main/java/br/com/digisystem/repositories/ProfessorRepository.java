package br.com.digisystem.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.digisystem.entities.ProfessorEntity;

@Repository
public interface ProfessorRepository extends MongoRepository<ProfessorEntity, String>{
	
	@Query(" { nome: {$regex: /?0/  }  } ")
	public List<ProfessorEntity> searchByNomeNativo(String nome); 
}
