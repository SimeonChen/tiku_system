package com.tamguo.modules.tiku.model;

import java.io.Serializable;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tamguo.config.dao.SuperEntity;


/**
 * The persistent class for the t_chapter database table.
 * 
 */
@Data
@TableName(value="t_paper")
public class PaperEntity extends SuperEntity<PaperEntity> implements Serializable {
	private static final long serialVersionUID = 1L;
	private String subjectId;
	private String courseId;
	private String schoolId;
	private String areaId;
	private String createrId;
	private String name;
	private String questionInfo;
	private String type;
	private String year;
	private Integer downHits;
	private Integer openHits;
	private String seoTitle;
	private String seoKeywords;
	private String seoDescription;
	@TableField(value="free")
	private String free;
	private String point;
	private String money;
	@TableField(exist=false)
	private String subjectName;
	@TableField(exist=false)
	private String courseName;
	@TableField(exist=false)
	private String areaName;
	@TableField(exist=false)
	private String schoolName;
	
	public JSONArray getQueInfo(){
		if(StringUtils.isEmpty(questionInfo)){
			return null;
		}
		return JSONArray.parseArray(questionInfo);
	}

}