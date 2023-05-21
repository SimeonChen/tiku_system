package com.tamguo.modules.tiku.model;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.tamguo.modules.tiku.model.enums.CourseStatusEnum;
import lombok.Data;

@Data
@TableName(value="t_course")
public class CourseEntity {
	
	@TableId
	private String id;
	private String subjectId;
	private String name;
	private Integer sort;
	private Integer questionNum;
	private Integer pointNum;
	private String remarks;
	private String icon;
	
	private String seoTitle;
	private String seoKeywords;
	private String seoDescription;
	
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String createBy;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateBy;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date createDate;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateDate;
	
	@JSONField(serialzeFeatures= SerializerFeature.WriteEnumUsingToString)
	private CourseStatusEnum status;

}
