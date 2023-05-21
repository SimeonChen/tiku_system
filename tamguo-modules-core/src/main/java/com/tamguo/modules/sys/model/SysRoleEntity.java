package com.tamguo.modules.sys.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import lombok.Data;

@Data
@TableName(value="sys_role")
public class SysRoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId
	private String roleCode;
	private String corpCode;
	private String corpName;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String createBy;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date createDate;
	private String dataScope;
	private String isSys;
	private String remarks;
	private String roleName;
	private BigDecimal roleSort;
	private String roleType;
	private String status;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateBy;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateDate;
	private String userType;
	
	@TableField(exist=false)
	private String roleMenuListJson;
	@TableField(exist=false)
	private String roleDataScopeListJson;

}