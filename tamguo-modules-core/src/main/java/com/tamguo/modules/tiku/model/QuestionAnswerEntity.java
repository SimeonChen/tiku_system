package com.tamguo.modules.tiku.model;

import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tamguo.config.dao.SuperEntity;
import lombok.Data;

@Data
@TableName(value="t_question_answer")
public class QuestionAnswerEntity extends SuperEntity<PaperEntity> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String questionId;
	private String memberId;
	private String memberAvatar;
	private String memberName;
	private String answer;
	private Integer agreeNum;
	private Integer disagreeNum;
	private Date createDate;
	
}
