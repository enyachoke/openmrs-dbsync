package org.openmrs.eip.dbsync.receiver.management.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "relationship_hash")
public class RelationshipHash extends BaseHashEntity {}
