package com.tamguo.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

@TableName(value="t_question_options")
public class QuestionOptionsEntity {

    @TableId
    private Long id;
    private String questionId;
    private String option;
    private String optionNo;
    private int order;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getOptionNo() {
        return optionNo;
    }

    public void setOptionNo(String optionNo) {
        this.optionNo = optionNo;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
