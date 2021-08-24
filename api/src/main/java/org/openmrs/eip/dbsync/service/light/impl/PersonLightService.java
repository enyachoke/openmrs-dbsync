package org.openmrs.eip.dbsync.service.light.impl;

import org.openmrs.eip.dbsync.entity.light.PersonLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.openmrs.eip.dbsync.service.light.AbstractLightService;
import org.springframework.stereotype.Service;

@Service
public class PersonLightService extends AbstractLightService<PersonLight> {

    public PersonLightService(final OpenmrsRepository<PersonLight> repository) {
        super(repository);
    }

    @Override
    protected PersonLight createPlaceholderEntity(final String uuid) {
        PersonLight person = new PersonLight();
        person.setCreator(DEFAULT_USER_ID);
        person.setDateCreated(DEFAULT_DATE);
        return person;
    }
}
