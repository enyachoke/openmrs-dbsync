package org.openmrs.eip.dbsync.receiver.management.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "condition_hash")
public class ConditionHash extends BaseHashEntity {}
