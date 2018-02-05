package com.m2m.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserCoinDto implements Serializable {
	private static final long serialVersionUID = 8797249922885267162L;

	private int currentLevel;
    private int upgrade;

}
