package com.lanxige.model.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lanxige.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(description = "SysOpenLog")
@TableName("sys_oper_log")
public class SysOpenLog extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "模块标题")
	@TableField("title")
	private String title;

	@ApiModelProperty(value = "业务类型（0其它 1新增 2修改 3删除）")
	@TableField("business_type")
	private String businessType;

	@ApiModelProperty(value = "请求方式")
	@TableField("request_method")
	private String requestMethod;

	@ApiModelProperty(value = "操作人员")
	@TableField("oper_name")
	private String operName;

	@ApiModelProperty(value = "请求URL")
	@TableField("oper_url")
	private String operUrl;

	@ApiModelProperty(value = "主机地址")
	@TableField("oper_ip")
	private String operIp;

	@ApiModelProperty(value = "操作时间")
	@TableField("oper_time")
	private Date operTime;

}