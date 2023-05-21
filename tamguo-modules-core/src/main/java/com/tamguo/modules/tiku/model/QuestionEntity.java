package com.tamguo.modules.tiku.model;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tamguo.config.dao.SuperEntity;
import lombok.Data;

/**
 * The persistent class for the t_question database table.
 * 
 */
@Data
@TableName(value="t_question")
public class QuestionEntity extends SuperEntity<QuestionEntity> implements Serializable {
	private static final long serialVersionUID = 1L;

	private String analysis;
	private String paperId;
	private String answer;
	private String chapterId;
	private String questionType;
	private String content;
	private String subjectId;
	private String courseId;
	private String reviewPoint;
	private String year;
	private String score;
	private String auditStatus;
	
	@TableField(exist=false)
	private String courseName;
	@TableField(exist=false)
	private String chapterName;
	@TableField(exist=false)
	private String subjectName;
	@TableField(exist = false)
	private List<QuestionOptionsEntity> questionOptions;

}