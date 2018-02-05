package com.m2m.entity;

import java.io.Serializable;

import com.m2m.domain.UserProfile;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ExtUserProfile extends UserProfile implements Serializable {

	private static final long serialVersionUID = 2827929303060389560L;

	private String industry;
	
}
