package org.openmrs.eip.dbsync.receiver.management.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "drug_order_hash")
public class DrugOrderHash extends BaseHashEntity {}
