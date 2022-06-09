package br.com.digisystem.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import br.com.digisystem.entities.UsuarioEntity;
import br.com.digisystem.repositories.CustomRepository;

@Repository
public class CustomRepositoryImpl implements CustomRepository {

	// Cria consultas
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public UsuarioEntity updateUsuario(String id, String nome) {
		
		Query query = new Query();
		
		// { nome: <nome> } 
		query.addCriteria(Criteria.where("id").is(id));
		
		Update update = new Update();
		update.set("nome", nome);
		
		//mongoTemplate.update(UsuarioEntity.class).matching(query).apply(update);
		mongoTemplate.findAndModify(query,update,UsuarioEntity.class);
		
		UsuarioEntity usuario = mongoTemplate.findOne(query, UsuarioEntity.class);
		
		return usuario;
	}

}
