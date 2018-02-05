package com.m2m.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class Home4AttentionRequest extends BaseRequest implements Serializable {
	private static final long serialVersionUID = -8965366907203940748L;
	@NotNull
    private Long updateTime;
}