package com.tamguo.modules.sys.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import lombok.Data;


/**
 * The persistent class for the sys_office database table.
 * 
 */
@Data
@TableName(value="sys_office")
public class SysOfficeEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String ROOT_OFFICE_CODE = "0";
	public static final String TREE_CODE_OFFICE_SEPARATE = ",";
	public static final String TREE_NAME_OFFICE_SEPARATE = "/";
	
	@TableId
	private String officeCode;
	private String address;
	private String corpCode;
	private String corpName;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String createBy;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date createDate;
	private String email;
	private String fullName;
	private String leader;
	private String officeName;
	private String officeType;
	private String parentCode;
	private String parentCodes;
	private String phone;
	private String remarks;
	private String status;
	private Boolean treeLeaf;
	private BigDecimal treeLevel;
	private String treeNames;
	private BigDecimal treeSort;
	private String treeSorts;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateBy;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateDate;
	private String viewCode;
	private String zipCode;

	// grid tree
	public String getId() {
		return this.getOfficeCode();
	}

}