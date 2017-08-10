package com.castle.repo.jpa;

import java.io.Serializable;

import javax.persistence.MappedSuperclass;

import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.AbstractPersistable;

@MappedSuperclass
public class BaseEntity<I extends Serializable> extends AbstractPersistable<I>implements Persistable<I> {

	private static final long serialVersionUID = 2909347062854434851L;

}
