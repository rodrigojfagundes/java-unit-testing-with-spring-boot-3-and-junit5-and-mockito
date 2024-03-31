package br.com.erudio.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;


//class de test da PERSONSERVICES

@ExtendWith(MockitoExtension.class)
public class PersonServicesTest {
	
	@Mock
	private PersonRepository repository;
	
	@InjectMocks
	private PersonServices services;
	
	private Person person0;
		
	//criando um metoo de nome SETUP com as configuracoes padroes para TODOS OS TESTES
	//pois por ter a ANNOTATION @BEFOREEACH esse SETUP sera EXEC toda vez
	//antes de CADA TEST
	@BeforeEach
	public void setup() {
		//PERSON0 recebe a instanciacao de um OBJ do tipo PERSON, e atribuindo a ele os valores leandro, costa, leandro@eurudio.com.br, etc...
		person0 = new Person("Leandro", 
				"Costa", 
				"leandro@erudio.com.br",
				"Uberlandia - Minas Gerais - Brasil",
				"Male");
	}
	
	
	//implementando o TEST de SAVE PERSON

	//esse metodo vai testar se quando nos SALVAMOS um OBJ do tipo PERSON
	//é RETORNADO o OBJ PERSON q foi SALVO...
	@DisplayName("JUnit test for Given Person Object When Save person then return Person Object")
	@Test
	void testGivenPersonObject_WhenSavePerson_thenReturnPersonObject() {
		
		//Given / Arrange

		//QUANDO chamar o o metodo FINDBYEMAIL do REPOSITORY recebendo QUALQUER STRING
		//ele vai retornar um OBJ do tipo OPTIONAL VAZIO
		given(repository.findByEmail(anyString())).willReturn(Optional.empty());
		
		//QUANDO chamar o metodo SAVE do REPOSITORY, passando como argumento o PERSON0
		//o RETURN tem q ser um PERSON0... tem q ser o memso OBJ q foi passado tem q RETORNAR
		given(repository.save(person0)).willReturn(person0);
		
		//When / Act

		//criando um OBJ do tipo PERSON de nome SAVEDPERSON q vai receber o RETORNO
		//do metodo CREATE do SERVICES, apos passar o OBJ PERSON0
		Person savedPerson = services.create(person0);
				
		//Then / Assert

		//agora aqui vamos testar SE SAVEDPERSON nao ta NULL (ou seja se RETORNOU ALGO)
		assertNotNull(savedPerson);

		//verificando SE o o FIRSTNAME q ta no SAVEDPERSON é LEANDRO
		assertEquals("Leandro", savedPerson.getFirstName());
		
		
	}
	
	//test[System Under Test]_[Condition or State Change]_[Expected Result]
	@DisplayName("JUnit test for Given Existing Email When Save Person then Throws Exception")
	@Test
	void testGivenExistingEmail_WhenSavePerson_thenThrowsException() {
		
		//Given / Arrange
		given(repository.findByEmail(anyString())).willReturn(Optional.of(person0));
				
		//When / Act
		assertThrows(ResourceNotFoundException.class, () -> {
			services.create(person0);
		});
		
		
		//Then / Assert
		verify(repository, never()).save(any(Person.class));
		
	}
	
}
