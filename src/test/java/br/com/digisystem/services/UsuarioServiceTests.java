package br.com.digisystem.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.digisystem.entities.UsuarioEntity;
import br.com.digisystem.exceptions.ObjNotFoundException;
import br.com.digisystem.repositories.CustomRepository;
import br.com.digisystem.repositories.UsuarioRepository;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class UsuarioServiceTests {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@MockBean
	private UsuarioRepository usuarioRepository;
	
	@MockBean
	private CustomRepository customRepository;
	
	@Test
	void getAllTest() {
		
		List<UsuarioEntity> usuarioSimulado = new ArrayList<>();
		UsuarioEntity usuario = new UsuarioEntity();
		usuario.setEmail("Email@email.com");
		usuario.setNome("Nome Teste");
		
		usuarioSimulado.add(usuario);
		
		when ( usuarioRepository.findAll() ).thenReturn( usuarioSimulado );
		
		List<UsuarioEntity> lista = usuarioService.getAll();
		
		assertThat( usuarioSimulado.get(0).getNome() )
			.isEqualTo(lista.get(0).getNome());
		
		assertThat( usuarioSimulado.get(0).getEmail() )
			.isEqualTo(lista.get(0).getEmail());
	}
	
	@Test
	void getOneWhenFoundUserTest() {
		
		// criando um objeto Optional do tipo UsuarioEntity
		String id = "id";
		
		UsuarioEntity usuarioEntity = new UsuarioEntity();
		usuarioEntity.setEmail("Email@email.com");
		usuarioEntity.setNome("Nome Teste");
		usuarioEntity.setId(id);
		
		Optional<UsuarioEntity> optional = Optional.of(usuarioEntity);
		
		/* simulando quando existe um usuário no banco de dados , então 
		 * retorno um Optional do tipo UsuárioEntity */
		when ( usuarioRepository.findById(id) ).thenReturn(optional);
		
		// executo o teste do método getOne da camada de serviço
		UsuarioEntity usuario = usuarioService.getOne(id);
		
		/* Verifico se o retorno do getOne possui os valores do objeto criado no 
		 * começo do teste*/
		assertThat( usuario.getNome() )
			.isEqualTo(usuarioEntity.getNome());
	
		assertThat( usuario.getEmail() )
			.isEqualTo( usuarioEntity.getEmail());
		
	}
	
	@Test
	void getOneWhenNotFoundUserTest() {
		
		String id = "id";
		/* simulando quando não há um usuário no banco de dados , então 
		 * retorno uma exceção do tipo ObjNotFoundException*/
		when ( usuarioRepository.findById(id) )
			.thenThrow(  new ObjNotFoundException("Erro") );
		
		/*
		 * chama o getOne para verificar se o retono é do tipo ObjNotFoundException*/
	 	assertThrows(ObjNotFoundException.class, ()-> usuarioService.getOne(id) );
	}
	
	@Test
	void createTest() {
		
		// criar um objeto que vai ser retornado pela camada repository
		
		String id = "id";
		
		UsuarioEntity usuarioEntity = new UsuarioEntity();
		usuarioEntity.setEmail("Email@email.com");
		usuarioEntity.setNome("Nome Teste");
		
		UsuarioEntity usuarioEntityRetorno = new UsuarioEntity();
		usuarioEntityRetorno.setEmail("Email@email.com");
		usuarioEntityRetorno.setNome("Nome Teste");
		usuarioEntityRetorno.setId(id);
		
		when( usuarioRepository.save( usuarioEntity ) ).thenReturn( usuarioEntityRetorno );
		
		
		/* Testar a camada de serviço (create)*/
		UsuarioEntity usuarioSalvo = usuarioService.create(usuarioEntity);
		
		/* Verifico se o retorno do getOne possui os valores do objeto criado no 
		 * começo do teste*/
		assertThat( usuarioSalvo.getNome() )
			.isEqualTo(usuarioEntity.getNome());
	
		assertThat( usuarioSalvo.getEmail() )
			.isEqualTo( usuarioEntity.getEmail());
		
		assertThat( usuarioSalvo.getId() ).isNotNull();
		
	}
	
	@Test
	void updateWhenFoundUser() {
		
		String id = "id";
		
		UsuarioEntity usuarioValid = this.createValidUsuario(true);
		
		when( usuarioRepository.findById(id) ).thenReturn( Optional.of(usuarioValid) );
		when (usuarioRepository.save(usuarioValid)).thenReturn(usuarioValid);
		
		// executar o teste
		
		UsuarioEntity usuarioAlterado = usuarioService.update(id, usuarioValid);
		
		// verificar se o teste deu certo
		assertThat( usuarioAlterado.getNome() )
			.isEqualTo(usuarioValid.getNome());

		assertThat( usuarioAlterado.getEmail() )
			.isEqualTo( usuarioValid.getEmail());
	}
	
	@Test
	void updateWhenNotFoundUser() {
		
		String id = "id";
		
		when( usuarioRepository.findById(id) ).thenReturn( Optional.empty() );
		
		// executar o teste
		
		/*
		 * chama o getOne para verificar se o retono é do tipo ObjNotFoundException*/
	 	assertThrows(ObjNotFoundException.class, ()-> usuarioService.update(id, null) );
		
	}
	
	@Test
	void deleteTest() {
		
		//execução
		
		String id = "id";
		
		assertDoesNotThrow( () -> usuarioService.delete(id) );
		
		// verifico se o método usuarioRepository.deleteById() foi executado apenas uma vez
		verify(usuarioRepository, times(1) ).deleteById(id);
	}
	
	@Test
	void getByNomeTest() {
		
		// criar a lista simulada
		UsuarioEntity usuarioValid = this.createValidUsuario(true);
		
//		List<UsuarioEntity> lista = new ArrayList<>();
//		lista.add(usuarioValid);
//		lista.add(usuarioValid);
		List<UsuarioEntity> lista = Arrays.asList(usuarioValid, usuarioValid);
		
		when( usuarioRepository.searchByNomeNativo("nome")).thenReturn(lista);
		

		// verificar teste
		
		//lista não é vazia
		assertThat( lista ).isNotEmpty();
		
		//não retornou uma exceção quando chamou o método de teste
		assertDoesNotThrow( () -> usuarioRepository.searchByNomeNativo("nome") );
		
	}
	
	@Test
	void updateUsuarioTest() {
		
		String id = "id";
		
		assertDoesNotThrow( () ->  usuarioService.updateUsuario(id,"nome") );
		verify(customRepository, times(1) ).updateUsuario(id, "nome");
		
	}
	
	private UsuarioEntity createValidUsuario( boolean isId ) {
	
		String id = "id";
		
		UsuarioEntity usuarioEntity = new UsuarioEntity();
		
		usuarioEntity.setEmail("Email@email.com");
		usuarioEntity.setNome("Nome Teste");
		
		if (isId == true) {
			usuarioEntity.setId(id);
		}
		
		return usuarioEntity;
	}

}
