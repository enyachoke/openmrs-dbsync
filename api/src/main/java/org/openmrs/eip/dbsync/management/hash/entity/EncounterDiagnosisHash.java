package org.openmrs.eip.dbsync.management.hash.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "encounter_diagnosis_hash")
public class EncounterDiagnosisHash extends BaseHashEntity {}
