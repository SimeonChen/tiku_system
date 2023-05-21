package com.tamguo.modules.sys.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.tamguo.modules.sys.model.enums.SysAreaStatusEnum;
import lombok.Data;


/**
 * The persistent class for the sys_area database table.
 * 
 */
@Data
@TableName(value="sys_area")
public class SysAreaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String ROOT_AREA_CODE = "0";
	public static final String TREE_CODE_AREA_SEPARATE = ",";
	public static final String TREE_NAME_AREA_SEPARATE = "/";

	@TableId
	private String areaCode;
	private String areaName;
	private String areaType;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String createBy;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date createDate;
	private String parentCode;
	private String parentCodes;
	private String remarks;
	
	@JSONField(serialzeFeatures= SerializerFeature.WriteEnumUsingToString)
	private SysAreaStatusEnum status;
	
	private Boolean treeLeaf;
	private BigDecimal treeLevel;
	private String treeNames;
	private BigDecimal treeSort;
	private String treeSorts;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateBy;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateDate;
	
	@TableField(exist=false)
	private List<SysAreaEntity> children;

	public String getId() {
		return areaCode;
	}
}