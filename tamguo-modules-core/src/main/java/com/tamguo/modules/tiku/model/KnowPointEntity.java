package com.tamguo.modules.tiku.model;

import java.util.Date;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.tamguo.modules.tiku.model.enums.BookStatusEnum;
import lombok.Data;

@Data
@TableName(value="t_know_point")
public class KnowPointEntity {

	@TableId
	private String id;
	private String subjectId;
	private String courseId;
	private String name;
	private String publishingHouse;
	private Integer questionNum;
	private Integer pointNum;
	private Integer sort;
	private String remarks;
	
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String createBy;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateBy;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date createDate;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateDate;
	
	@JSONField(serialzeFeatures= SerializerFeature.WriteEnumUsingToString)
	private BookStatusEnum status;

	@TableField(exist=false)
	private String subjectName;
	@TableField(exist=false)
	private String courseName;
	
}
