package org.openmrs.eip.app.db.sync.mapper.operations;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.openmrs.eip.app.db.sync.model.BaseModel;
import org.openmrs.eip.app.db.sync.entity.BaseEntity;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode
public class Context<E extends BaseEntity, M extends BaseModel> {

    private final E entity;

    private final M model;

    private final MappingDirectionEnum direction;

    private BeanWrapper entityBeanWrapper;

    private BeanWrapper modelBeanWrapper;

    public BeanWrapper getEntityBeanWrapper() {
        if (entityBeanWrapper == null) {
            entityBeanWrapper = new BeanWrapperImpl(entity);
        }
        return entityBeanWrapper;
    }

    public BeanWrapper getModelBeanWrapper() {
        if (modelBeanWrapper == null) {
            modelBeanWrapper = new BeanWrapperImpl(model);
        }
        return modelBeanWrapper;
    }
}
