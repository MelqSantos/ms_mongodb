package br.com.digisystem.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.digisystem.entities.UsuarioEntity;
import br.com.digisystem.exceptions.ObjNotFoundException;
import br.com.digisystem.repositories.CustomRepository;
import br.com.digisystem.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private CustomRepository customRepository;

	public List<UsuarioEntity> getAll() {
		return this.usuarioRepository.findAll();
	}

	public UsuarioEntity getOne(String id) {
		return this.usuarioRepository.findById(id)
				.orElseThrow( () -> new ObjNotFoundException("Elemento com ID " + id +" não foi localizado"));
	}
	
	public List<UsuarioEntity> getByName(String nome) {
		return this.usuarioRepository.searchByNomeNativo(nome);
	}

	public UsuarioEntity create(UsuarioEntity usuario) {

		UsuarioEntity usu = new UsuarioEntity();

		usu.setNome(usuario.getNome());
		usu.setEmail(usuario.getEmail());

		usu = this.usuarioRepository.save(usu);

		return usu;
	}

	public UsuarioEntity update(String id, UsuarioEntity usuario) {

		UsuarioEntity usuarioupdate = this.usuarioRepository.findById(id)
				.orElseThrow(() -> new ObjNotFoundException("ID" + id + "Não localizado"));

			usuarioupdate.setEmail(usuario.getEmail());
			usuarioupdate.setNome(usuario.getNome());

			return this.usuarioRepository.save(usuarioupdate);
	}
	
	public UsuarioEntity updateUsuario(String id, String nome) {
		return this.customRepository.updateUsuario(id, nome);
	}

	public void delete(String id) {
		this.usuarioRepository.deleteById(id);
	}

}
