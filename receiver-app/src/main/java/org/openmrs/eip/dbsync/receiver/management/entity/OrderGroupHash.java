package org.openmrs.eip.dbsync.receiver.management.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "order_group_hash")
public class OrderGroupHash extends BaseHashEntity {}
