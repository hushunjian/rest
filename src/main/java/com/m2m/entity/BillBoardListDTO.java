package com.m2m.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class BillBoardListDTO implements Serializable{
	private static final long serialVersionUID = -8781449680844822539L;
	
	private long targetId;
	private long sinceId;
	private int type;
}
