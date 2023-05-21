package com.tamguo.modules.sys.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.tamguo.modules.sys.model.enums.SysMenuStatusEnum;
import lombok.Data;

@Data
@TableName(value="sys_menu")
public class SysMenuEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String ROOT_MENU_CODE = "0";
	public static final String TREE_CODE_MENU_SEPARATE = ",";
	public static final String TREE_NAME_MENU_SEPARATE = "/";

	@TableId
	private String menuCode;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String createBy;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date createDate;
	private String isShow;
	private String menuColor;
	private String menuHref;
	private String menuIcon;
	private String menuName;
	private String menuTarget;
	private String menuType;
	private String parentCode;
	private String parentCodes;
	private String permission;
	private String remarks;
	
	@JSONField(serialzeFeatures= SerializerFeature.WriteEnumUsingToString)
	private SysMenuStatusEnum status;
	private String sysCode;
	private Boolean treeLeaf;
	private BigDecimal treeLevel;
	private String treeNames;
	private BigDecimal treeSort;
	private String treeSorts;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateBy;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateDate;
	private BigDecimal weight;

    public String getId() {
    	return menuCode;
    }
}