package br.com.digisystem.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.digisystem.dtos.UsuarioDTO;
import br.com.digisystem.entities.UsuarioEntity;
import br.com.digisystem.repositories.UsuarioRepository;
import br.com.digisystem.utils.UsuarioUtil;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioControllerTests extends UsuarioUtil{
	
	@Autowired
	private MockMvc mockmvc;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	// converte String para objetos
	private ObjectMapper mapper = new ObjectMapper();
	
	@Test
	void getAllTest() throws Exception {
		ResultActions response =  mockmvc.perform(
				get("/usuarios")
				.contentType("application/json")
				);
		
		MvcResult result = response.andReturn();
		String resultStr = result.getResponse().getContentAsString();
		System.out.println(resultStr);
		
		UsuarioDTO[] lista = mapper.readValue(resultStr, UsuarioDTO[].class);
		
		// Verifica se a lista não é vazia
		assertThat(lista).isNotEmpty();
		assertThat( result.getResponse().getStatus() ).isEqualTo( HttpStatus.OK.value() );
	}
	
	@Test
	void getOne() throws Exception {
		
		UsuarioEntity entity = saveUsuario( createValidUser() );
		String id = entity.getId();
		
		ResultActions response =  mockmvc.perform(
				get("/usuarios/" + id)
				.contentType("application/json")
				);
		
		MvcResult result = response.andReturn();
		String resultStr = result.getResponse().getContentAsString();
		
		UsuarioDTO usuario = mapper.readValue(resultStr, UsuarioDTO.class);
		
		assertThat(usuario.getId()).isEqualTo(id);
		assertThat( result.getResponse().getStatus() ).isEqualTo( HttpStatus.OK.value() );
	}
	
	@Test
	void creatTest() throws Exception{
		// Criar um usuarioDTO para enviar no corpo da requisição
		
		UsuarioDTO usuario = new UsuarioDTO();
		
		usuario.setNome("Melqui JUNIT");
		usuario.setEmail("junit@gmail.com");
		
		ResultActions response =  mockmvc.perform(
				post("/usuarios")
				.contentType("application/json")
				.content(mapper.writeValueAsString(usuario))
				);
		
		MvcResult result = response.andReturn();
		String resultStr = result.getResponse().getContentAsString();
		
		UsuarioDTO usuarioSalvo = mapper.readValue(resultStr, UsuarioDTO.class);
		
		assertThat(usuarioSalvo.getId()).isNotNull();
		assertThat( result.getResponse().getStatus() ).isEqualTo( HttpStatus.OK.value() );
	}
	
	@Test
	void updateTest() throws Exception{
		
		UsuarioEntity entity = saveUsuario( createValidUser() );
		String id = entity.getId();
		
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setNome("Melqui JUNIT");
		usuario.setEmail("junit@gmail.com");
		
		ResultActions response =  mockmvc.perform(
				patch("/usuarios/" + id)
				.contentType("application/json")
				.content(mapper.writeValueAsString(usuario))
				);
		
		MvcResult result = response.andReturn();
		String resultStr = result.getResponse().getContentAsString();
		
		UsuarioDTO usuarioAlterado = mapper.readValue(resultStr, UsuarioDTO.class);
		
		assertThat(usuarioAlterado.getId()).isEqualTo(id);
		assertThat(usuarioAlterado.getNome()).isEqualTo(usuario.getNome());
		assertThat(usuarioAlterado.getEmail()).isEqualTo(usuario.getEmail());
		assertThat( result.getResponse().getStatus() ).isEqualTo( HttpStatus.OK.value() );
	}
	
	@Test
	void deleteTest() throws Exception{
		
		UsuarioEntity entity = saveUsuario( createValidUser() );
		String id = entity.getId();
		
		ResultActions response =  mockmvc.perform(
				delete("/usuarios/" + id)
				.contentType("application/json")
				);
		
		MvcResult result = response.andReturn();
		assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
	}
	
	@Test
	void getByNomeTest() throws Exception{
		
		UsuarioEntity entity = saveUsuario( createValidUser() );
		
		ResultActions response =  mockmvc.perform(
				get("/usuarios/get-by-nome/" + entity.getNome())
				.contentType("application/json")
				);
		
		MvcResult result = response.andReturn();
		String resultStr = result.getResponse().getContentAsString();
		
		UsuarioDTO[] lista = mapper.readValue(resultStr, UsuarioDTO[].class);
		
		assertThat(lista).isNotEmpty();
		assertThat( result.getResponse().getStatus() ).isEqualTo( HttpStatus.OK.value() );
	}
	
	@Test
	void updateUsuario() throws Exception {
		
		UsuarioEntity entity = saveUsuario( createValidUser() );
		String id = entity.getId();
		
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setNome("Melqui Alterado Junit");
		
		ResultActions response = mockmvc.perform(
				patch("/usuarios/update/" + id)
				.contentType("application/json")
				.content( mapper.writeValueAsString(usuario) )
		);
		
		MvcResult result = response.andReturn();
		
		assertThat( result.getResponse().getStatus() ).isEqualTo( HttpStatus.OK.value() );
	}
	
	private UsuarioEntity saveUsuario(UsuarioEntity entity) {
		return usuarioRepository.save(entity);
	}
}
