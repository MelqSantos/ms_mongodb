package br.com.digisystem.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.digisystem.entities.UsuarioEntity;

@Repository
public interface UsuarioRepository extends MongoRepository<UsuarioEntity, String> {
	
	@Query(" { nome: {$regex: /?0/  }  } ")
	public List<UsuarioEntity> searchByNomeNativo(String nome); 
	
}
