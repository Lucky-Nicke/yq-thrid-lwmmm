package com.lanxige.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysOperLogQueryVo implements Serializable {

	private String title;
	private String operName;

	private String createTimeBegin;
	private String createTimeEnd;

}

