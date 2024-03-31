package br.com.erudio.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.erudio.model.Person;

//classe de TESTE do PERSONREPOSITORY

@DataJpaTest
class PersonRepositoryTest {
	
	//test[System Under Test]_[Condition or State Change]_[Expected Result]
	@Autowired
	private PersonRepository repository;
	
	//criando um METODO DE TEST, q vai TESTAR SE quando nos CRIAMOS um OBJ
	//do TIPO PERSON ele(PERSONREPOSITORY.JAVA) tem q retornar o OBJ PERSON
	//CRIADO...

	@DisplayName("Given Person Object when Save Then Return Saved Person")
	@Test
	void testGivenPersonObject_whenSave_ThenReturnSavedPerson() {
		
		//Given / Arrange

		//instanciando um OBJ do tipo PERSON, e atribuindo a ele os valores
		//leandro, costa, leandro@eurudio.com.br, etc...
		Person person0 = new Person("Leandro", 
				"Costa", 
				"leandro@erudio.com.br",
				"Uberlandia - Minas Gerais - Brasil",
				"Male");
		
		//When / Act
		
		//quando nos salvarmos UM PERSON no BANCO, o PERSONREPOSITORY.JAVA
		//precisa RETORNAR a PESSOA q foi salva (nome, email, etc...) apos ele
		//SALVAR no BANCO...
		//e o q ele retornar nos VAMOS SALVAR em uma VAR de nome SAVEDPERSON
		Person savedPerson = repository.save(person0);
		
		
		//Then / Assert

		//Verificando SE o q ta na VAR SAVEDPERSON nao e NULL
		assertNotNull(savedPerson);
		
		assertTrue(savedPerson.getId() > 0);

	}

	@DisplayName("Given Person List when findAll Then Return Saved Person List")
	@Test
	void testGivenPersonList_whenFindAll_ThenReturnSavedPersonList() {
		
		//Given / Arrange
		Person person0 = new Person("Leandro", 
				"Costa", 
				"leandro@erudio.com.br",
				"Uberlandia - Minas Gerais - Brasil",
				"Male");

		Person person1 = new Person("Leonardo", 
				"Costa", 
				"leonardo@erudio.com.br",
				"Uberlandia - Minas Gerais - Brasil",
				"Male");
		
		repository.save(person0);
		repository.save(person1);
		
		//When / Act
		List<Person> personList = repository.findAll();
		
		//Then / Assert
		assertNotNull(personList);
		assertEquals(2, personList.size());

	}
	
	
}
