package br.com.digisystem.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.digisystem.entities.UsuarioEntity;
import br.com.digisystem.exceptions.ObjNotFoundException;
import br.com.digisystem.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public List<UsuarioEntity> getAll() {

		List<UsuarioEntity> usuarios = this.usuarioRepository.findAll();

		return usuarios;
	}

	public UsuarioEntity getOne(String id) {
		return this.usuarioRepository.findById(id)
				.orElseThrow( () -> new ObjNotFoundException("Elemento com ID " + id +" não foi localizado"));
	}
	
//	public List<UsuarioEntity> getByName(String nome) {
//		return this.usuarioRepository.searchByNomeNativo(nome);
//	}

	public UsuarioEntity create(UsuarioEntity usuario) {

		UsuarioEntity usu = new UsuarioEntity();

		usu.setNome(usuario.getNome());
		usu.setEmail(usuario.getEmail());

		usu = this.usuarioRepository.save(usu);

		return usu;
	}

	public UsuarioEntity update(String id, UsuarioEntity usuario) {

		Optional<UsuarioEntity> usuarioOptional = this.usuarioRepository.findById(id);

		if (usuarioOptional.isPresent()) {
			UsuarioEntity usuarioupdate = usuarioOptional.get();

			usuarioupdate.setEmail(usuario.getEmail());
			usuarioupdate.setNome(usuario.getNome());

			return this.usuarioRepository.save(usuarioupdate);
		} else {
			return null;
		}
	}
	
//	@Transactional // Executa tudo ou desfaz a transação caso dê errado (Segurança)
//	public void updateUsuario(int id, String nome) {
//		this.usuarioRepository.updateUsuario(id, nome);
//	}

	public void delete(String id) {
		this.usuarioRepository.deleteById(id);
	}

}
