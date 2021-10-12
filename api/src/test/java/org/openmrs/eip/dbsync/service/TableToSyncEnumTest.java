package org.openmrs.eip.dbsync.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openmrs.eip.dbsync.MockedModel;
import org.openmrs.eip.dbsync.entity.MockedEntity;
import org.openmrs.eip.dbsync.entity.Person;
import org.openmrs.eip.dbsync.exception.SyncException;
import org.openmrs.eip.dbsync.management.hash.entity.PersonHash;
import org.openmrs.eip.dbsync.model.PersonModel;

public class TableToSyncEnumTest {
	
	@Test
	public void getTableToSyncEnum_should_return_enum() {
		// Given
		String nameString = "person";
		
		// When
		TableToSyncEnum result = TableToSyncEnum.getTableToSyncEnum(nameString);
		
		// Then
		assertEquals(TableToSyncEnum.PERSON, result);
	}
	
	@Test
	public void getTableToSyncEnum_with_model_class_should_return_enum() {
		// Given
		Class<PersonModel> personModelClass = PersonModel.class;
		
		// When
		TableToSyncEnum result = TableToSyncEnum.getTableToSyncEnum(personModelClass);
		
		// Then
		assertEquals(TableToSyncEnum.PERSON, result);
	}
	
	@Test(expected = SyncException.class)
	public void getTableToSyncEnum_with_model_class_should_throw_exception() {
		// Given
		Class<MockedModel> personModelClass = MockedModel.class;
		
		// When
		TableToSyncEnum result = TableToSyncEnum.getTableToSyncEnum(personModelClass);
		
		// Then
	}
	
	@Test
	public void getModelClass_should_return_model_class() {
		// Given
		Person person = new Person();
		
		// When
		Class result = TableToSyncEnum.getModelClass(person);
		
		// Then
		assertEquals(PersonModel.class, result);
	}
	
	@Test(expected = SyncException.class)
	public void getModelClass_should_throw_exception() {
		// Given
		MockedEntity mockedEntity = new MockedEntity(1L, "uuid");
		
		// When
		TableToSyncEnum.getModelClass(mockedEntity);
		
		// Then
	}
	
	@Test
	public void getHashClass_shouldReturnTheHashClass() {
		// Given
		PersonModel person = new PersonModel();
		
		// When
		Class result = TableToSyncEnum.getHashClass(person);
		
		// Then
		assertEquals(PersonHash.class, result);
	}
	
	@Test(expected = SyncException.class)
	public void getHashClass_shouldFailIfNoHashClassIsFound() {
		// Given
		MockedEntity mockedEntity = new MockedEntity(1L, "uuid");
		
		// When
		TableToSyncEnum.getModelClass(mockedEntity);
	}
	
}
