package com.tamguo.modules.tiku.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@TableName(value="t_question_options")
@Data
public class QuestionOptionsEntity {

    @TableId
    private String id;
    private String questionId;
    private String option;
    private String optionNo;
    private int order;
}
