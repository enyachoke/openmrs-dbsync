package org.openmrs.eip.app.db.sync.service.impl;

import org.junit.Test;
import org.openmrs.eip.app.db.sync.model.PersonModel;
import org.openmrs.eip.app.db.sync.entity.Person;

import static org.junit.Assert.assertEquals;

public class MapperServiceTest {

    private MapperServiceImpl mapperService = new MapperServiceImpl();

    @Test
    public void getCorrespondingModelClass_should_return_model_class() {
        // Given
        Person person = new Person();

        // When
        Class result = mapperService.getCorrespondingModelClass(person);

        // Then
        assertEquals(PersonModel.class, result);
    }

    @Test
    public void getCorrespondingEntityClass_should_return_entity_class() {
        // Given
        PersonModel person = new PersonModel();

        // When
        Class result = mapperService.getCorrespondingEntityClass(person);

        // Then
        assertEquals(Person.class, result);
    }
}
