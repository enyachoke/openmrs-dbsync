package org.openmrs.eip.dbsync.receiver.management.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "person_name_hash")
public class PersonNameHash extends BaseHashEntity {}
