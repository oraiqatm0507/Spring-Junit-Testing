package com.quintrix.banking;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public abstract class DataModel {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public long id;	
}
