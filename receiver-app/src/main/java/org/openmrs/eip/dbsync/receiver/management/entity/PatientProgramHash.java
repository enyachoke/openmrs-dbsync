package org.openmrs.eip.dbsync.receiver.management.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "patient_program_hash")
public class PatientProgramHash extends BaseHashEntity {}
