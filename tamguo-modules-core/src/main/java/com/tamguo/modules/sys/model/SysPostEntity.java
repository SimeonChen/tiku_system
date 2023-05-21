package com.tamguo.modules.sys.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.tamguo.modules.sys.model.enums.SysPostStatusEnum;
import lombok.Data;

@Data
@TableName(value="sys_post")
public class SysPostEntity extends Model<SysPostEntity> implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId
	private String postCode;
	private String corpCode;
	private String corpName;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String createBy;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date createDate;
	private String postName;
	private BigDecimal postSort;
	private String postType;
	private String remarks;
	
	@JSONField(serialzeFeatures= SerializerFeature.WriteEnumUsingToString)
	private SysPostStatusEnum status;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateBy;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateDate;

	@Override
	protected Serializable pkVal() {
		return getPostCode();
	}

}