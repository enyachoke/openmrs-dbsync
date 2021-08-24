package org.openmrs.eip.dbsync.service.light;

import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;
import java.time.Month;

import org.openmrs.eip.dbsync.SyncContext;
import org.openmrs.eip.dbsync.entity.Person;
import org.openmrs.eip.dbsync.entity.light.LightEntity;
import org.openmrs.eip.dbsync.entity.light.PatientLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.openmrs.eip.dbsync.repository.PersonRepository;
import org.openmrs.eip.dbsync.service.PatientServiceUtils;
import org.openmrs.eip.dbsync.utils.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractLightService<E extends LightEntity> implements LightService<E> {
	
	private static final String DEFAULT_UUID_PREFIX = "PLACEHOLDER_";
	
	public static final String DEFAULT_STRING = "[Default]";
	
	protected static final LocalDateTime DEFAULT_DATE = LocalDateTime.of(1970, Month.JANUARY, 1, 0, 0);
	
	public static final long DEFAULT_USER_ID = 1L;
	
	protected OpenmrsRepository<E> repository;
	
	public AbstractLightService(final OpenmrsRepository<E> repository) {
		this.repository = repository;
	}
	
	/**
	 * Creates a placeholder entity with only mandatory attributes which will be unique for all entities
	 * with the same type. After a round of synchronization no placeholder entity should be left in the
	 * db
	 * 
	 * @return the entity
	 */
	protected abstract E createPlaceholderEntity(String uuid);
	
	/**
	 * Get the placeholder uuid
	 * 
	 * @return uuid
	 */
	private String getPlaceholderUuid() {
		Class<E> persistentClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass())
		        .getActualTypeArguments()[0];
		
		return DEFAULT_UUID_PREFIX + StringUtils.fromCamelCaseToSnakeCase(persistentClass.getSimpleName());
	}
	
	@Override
	public E getOrInitEntity(final String uuid) {
		return getOrInit(uuid);
	}
	
	@Override
	public E getOrInitPlaceholderEntity() {
		return getOrInit(getPlaceholderUuid());
	}
	
	private E getOrInit(final String uuid) {
		if (uuid == null) {
			return null;
		}
		
		E entity = repository.findByUuid(uuid);
		
		if (entity == null) {
			entity = createPlaceholderEntity(uuid);
			
			entity.setUuid(uuid);
			voidPlaceholder(entity);
			
			if (entity instanceof PatientLight) {
				//There is no row yet in the patient table and we don't yet know the FK, get the person row by uuid so
				//we can get the id and set it on this subclass, but we also need to insert the patient row.
				Long id;
				Person person = SyncContext.getBean(PersonRepository.class).findByUuid(uuid);
				if (person != null) {
					log.info("No matching row in the patient table, inserting one");
					id = person.getId();
					PatientLight p = (PatientLight) entity;
					Long creatorId = p.getPatientCreator() != null ? p.getPatientCreator() : DEFAULT_USER_ID;
					
					PatientServiceUtils.createPatient(id, uuid, p.isVoided(), creatorId, p.getPatientDateCreated());
					
					entity.setId(id);
				}
			}
			
			entity = repository.save(entity);
		}
		
		return entity;
	}
	
	private void voidPlaceholder(final E entity) {
		entity.setMuted(true);
		entity.setMuteReason("[placeholder]");
		entity.setDateMuted(DEFAULT_DATE);
		entity.setMutedBy(DEFAULT_USER_ID);
	}
}
