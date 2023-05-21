package com.tamguo.modules.tiku.model;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tamguo.config.dao.SuperEntity;
import com.tamguo.modules.tiku.model.enums.ChapterStatusEnum;
import lombok.Data;

import java.util.List;


/**
 * The persistent class for the t_chapter database table.
 * 
 */
@TableName(value="t_chapter")
@Data
public class ChapterEntity extends SuperEntity<ChapterEntity> implements Serializable {
	private static final long serialVersionUID = 1L;

	private String courseId;
	private String bookId;
	private String name;
	private String parentCode;
	private String parentCodes;
	private Integer questionNum;
	private Integer pointNum;
	private Integer orders;
	private Boolean treeLeaf;
	private Integer treeLevel; 
	
	private String seoTitle;
	private String seoKeywords;
	private String seoDescription;
	
	@TableField(exist=false)
	private List<ChapterEntity> childChapterList;
	
	@JSONField(serialzeFeatures= SerializerFeature.WriteEnumUsingToString)
	private ChapterStatusEnum status;

}