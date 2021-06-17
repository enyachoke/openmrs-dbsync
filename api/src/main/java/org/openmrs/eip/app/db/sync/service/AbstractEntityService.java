package org.openmrs.eip.app.db.sync.service;

import lombok.extern.slf4j.Slf4j;
import org.openmrs.eip.app.db.sync.entity.BaseEntity;
import org.openmrs.eip.app.db.sync.exception.ConflictsFoundException;
import org.openmrs.eip.app.db.sync.mapper.EntityToModelMapper;
import org.openmrs.eip.app.db.sync.mapper.ModelToEntityMapper;
import org.openmrs.eip.app.db.sync.model.BaseModel;
import org.openmrs.eip.app.db.sync.repository.SyncEntityRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public abstract class AbstractEntityService<E extends BaseEntity, M extends BaseModel> implements EntityService<M> {

    protected SyncEntityRepository<E> repository;
    protected EntityToModelMapper<E, M> entityToModelMapper;
    protected ModelToEntityMapper<M, E> modelToEntityMapper;

    public AbstractEntityService(final SyncEntityRepository<E> repository,
                                 final EntityToModelMapper<E, M> entityToModelMapper,
                                 final ModelToEntityMapper<M, E> modelToEntityMapper) {
        this.repository = repository;
        this.entityToModelMapper = entityToModelMapper;
        this.modelToEntityMapper = modelToEntityMapper;
    }

    /**
     * get the service entity name
     *
     * @return enum
     */
    public abstract TableToSyncEnum getTableToSync();

    @Override
    public M save(final M model) {
        E etyInDb = repository.findByUuid(model.getUuid());

        E ety = modelToEntityMapper.apply(model);

        M modelToReturn = model;

        if (etyInDb == null) {
            modelToReturn = saveEntity(ety);
            log.info(getMsg(ety, model.getUuid(), " inserted"));
        } else if (!etyInDb.wasModifiedAfter(ety)) {
            ety.setId(etyInDb.getId());
            modelToReturn = saveEntity(ety);
            log.info(getMsg(ety, model.getUuid(), " updated"));
        } else {
            throw new ConflictsFoundException();
        }

        return modelToReturn;
    }

    private M saveEntity(final E ety) {
        return entityToModelMapper.apply(repository.save(ety));
    }

    @Override
    public List<M> getAllModels() {
        return mapEntities(repository.findAll());
    }

    @Override
    public M getModel(final String uuid) {
        E entity  = repository.findByUuid(uuid);
        return entity != null ? entityToModelMapper.apply(entity) : null;
    }

    @Override
    public M getModel(final Long id) {
        Optional<E> entity = repository.findById(id);
        return entity.map(entityToModelMapper)
                .orElse(null);
    }

    @Override
    public void delete(String uuid) {
        E entity = repository.findByUuid(uuid);
        if (entity != null) {
            repository.delete(entity);
            log.info(getMsg(entity, uuid, " deleted"));
        } else {
            log.warn("No " + getTableToSync().getEntityClass().getName() + " found matching uuid: " + uuid);
        }
    }

    protected List<M> mapEntities(List<E> entities) {
        return entities.stream()
                .map(entityToModelMapper)
                .collect(Collectors.toList());
    }

    private String getMsg(final E ety, final String uuid, final String s) {
        return "Entity of type " + ety.getClass().getName() + " with uuid " + uuid + s;
    }
}
