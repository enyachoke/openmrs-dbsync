package org.openmrs.eip.dbsync.receiver.management.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "allergy_hash")
public class AllergyHash extends BaseHashEntity {}
