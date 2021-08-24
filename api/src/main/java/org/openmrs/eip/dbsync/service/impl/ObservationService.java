package org.openmrs.eip.dbsync.service.impl;

import org.openmrs.eip.dbsync.model.ObservationModel;
import org.openmrs.eip.dbsync.entity.Observation;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.openmrs.eip.dbsync.service.AbstractEntityService;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.springframework.stereotype.Service;

@Service
public class ObservationService extends AbstractEntityService<Observation, ObservationModel> {

    public ObservationService(final SyncEntityRepository<Observation> repository,
                              final EntityToModelMapper<Observation, ObservationModel> entityToModelMapper,
                              final ModelToEntityMapper<ObservationModel, Observation> modelToEntityMapper) {
        super(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Override
    public TableToSyncEnum getTableToSync() {
        return TableToSyncEnum.OBS;
    }
}
