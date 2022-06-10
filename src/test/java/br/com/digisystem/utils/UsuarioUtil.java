package br.com.digisystem.utils;

import br.com.digisystem.entities.UsuarioEntity;

public class UsuarioUtil {

protected UsuarioEntity createValidUser() {
		
		UsuarioEntity usuarioEntity = new UsuarioEntity();
		usuarioEntity.setEmail("teste@teste.com");
		usuarioEntity.setNome("Nome teste");
		
		return usuarioEntity;
	}
}
