package com.m2m.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class ExtIdCount implements Serializable {

	private static final long serialVersionUID = -7623267159480107113L;

	private Long id;

    private Long count;

}
