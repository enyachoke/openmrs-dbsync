package org.openmrs.eip.dbsync.receiver.management.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "person_address_hash")
public class PersonAddressHash extends BaseHashEntity {}
