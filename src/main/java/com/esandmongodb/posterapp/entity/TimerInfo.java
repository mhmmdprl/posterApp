package com.esandmongodb.posterapp.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimerInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Transient
	public static final String seqName="timer_info_sequence";
	@Id
	private Long id;
	private int totalFireCount;
	private long repeatIntervalMs;
	private long intialOffSetMs;
	private String groupName;
	private String cronExpression;
	private String className;
	private String callBackData;
	private boolean runForever;
	private boolean cronJob;
}
