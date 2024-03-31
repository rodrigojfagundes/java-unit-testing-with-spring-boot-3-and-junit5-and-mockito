package br.com.erudio.controllers;

import static org.mockito.BDDMockito.*;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.erudio.model.Person;
import br.com.erudio.services.PersonServices;



//CLASS para TESTAR os metodos da PERSONCONTROLLER/PERSONRESOURCE
@WebMvcTest
public class PersonControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;
	
	@MockBean
	private PersonServices service;
	
	private Person person;
		
	//criando um metoo de nome SETUP com as configuracoes padroes para TODOS OS TESTES
	//pois por ter a ANNOTATION @BEFOREEACH esse SETUP sera EXEC toda vez
	//antes de CADA TEST
	@BeforeEach
	public void setup() {
		//PERSON recebe a instanciacao de um OBJ do tipo PERSON, e atribuindo a ele os valores leandro, costa, leandro@eurudio.com.br, etc...
		person = new Person("Leandro", 
				"Costa", 
				"leandro@erudio.com.br",
				"Uberlandia - Minas Gerais - Brasil",
				"Male");
	}
	
	//metodo para TESTAR metodo CREATE do PERSONCONTROLLER.JAVA... Para pd CRIAR um novo
	//PERSON
	//
	//test[System Under Test]_[Condition or State Change]_[Expected Result]
	@Test
	@DisplayName("Given Person Object When Create Person Then Return Saved Person")
	void testGivenPersonObject_WhenCreatePerson_ThenReturnSavedPerson() throws JsonProcessingException, Exception {
		
		//Given / Arrange

		//QUANDO CHAMAR O METODO CREATE DO SERVICE, RECEBENDO QUALQUER INSTANCIA de 
		//PERSON.CLASS.... Vai RETORNAR(willAnswer) com uma funcao LAMBDA o ARGUMENTO
		//Ou seja VAI RETORNAR o OBJ q foi CRIADO... no CASO O PERSON q FOI CRIADO
		given(service.create(any(Person.class)))
			.willAnswer((invocation) -> invocation.getArgument(0));
		
		//When / Act
		
		//chamando o METODO PERFORM do MOCKMVC e ele vai fazer uma REQUISICAO do TIPO
		//POST para a URL /PERSON... Q no caso cai no PERSONCONTROLLER.JAVA
		//e o POST e o do METODO CREATE... E dai USANDO JSON nos vamos passar o 
		//CONTENT/CONTEUDO um OBJ PERSON com o CONTEUDO da requisicao... 
		//Ou seja vamos CAD o OBJ PERSON... e o OBJ MAPPER q pegou os DADOS do PERSON
		//e criou um JSON com as INFO...
		//E O RESULTADO/RETORNO vai ficar salvo na VAR/OBJ do TIPO RESULTACTIONS
		//de NOME RESPONSE...
		ResultActions response = mockMvc.perform(post("/person")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(person)));
		
		//Then / Assert
		
		//vamos verificar o RESULTADO, pois apos CAD um PERSON ele deve RETORNAR
		//o PERSON q foi CAD... 
		response.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.firstName", is(person.getFirstName())))
		.andExpect(jsonPath("$.lastName", is(person.getLastName())))
		.andExpect(jsonPath("$.email", is(person.getEmail())));
		
	}
	
}