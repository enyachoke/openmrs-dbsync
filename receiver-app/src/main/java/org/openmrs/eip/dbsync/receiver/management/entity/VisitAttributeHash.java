package org.openmrs.eip.dbsync.receiver.management.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "visit_attribute_hash")
public class VisitAttributeHash extends BaseHashEntity {}
