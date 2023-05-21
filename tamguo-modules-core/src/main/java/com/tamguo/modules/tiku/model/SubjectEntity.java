package com.tamguo.modules.tiku.model;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.tamguo.modules.tiku.model.enums.SubjectStatusEnum;
import lombok.Data;

@Data
@TableName(value="t_subject")
public class SubjectEntity {
	
	@TableId
	private String id;
	private String seoTitle;
	private String seoKeywords;
	private String seoDescription;
	private String name;
	private String sort;
	
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String createBy;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateBy;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date createDate;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateDate;
	
	private String remarks;
	
	@JSONField(serialzeFeatures= SerializerFeature.WriteEnumUsingToString)
	private SubjectStatusEnum status;
}
