package com.tamguo.web.member;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.model.QuestionEntity;
import com.tamguo.service.IPaperService;
import com.tamguo.service.IQuestionService;
import com.tamguo.util.ExceptionSupport;
import com.tamguo.util.Result;

@Controller(value="memberQuestionController")
public class QuestionController {
	
	@Autowired
	private IQuestionService iQuestionService;
	@Autowired
	private IPaperService iPaperService;
	
	@RequestMapping(value = "/member/addQuestion", method = RequestMethod.GET)
	public ModelAndView index(String paperId , ModelAndView model){
		model.setViewName("member/addQuestion");
		model.addObject("paper", iPaperService.find(paperId));
		return model;
	}
	
	@RequestMapping(value = "/member/submitQuestion", method = RequestMethod.POST)
	@ResponseBody
	public Result submitQuestion(QuestionEntity question){
		try {
			return iQuestionService.addQuestion(question);
		} catch (Exception e) {
			return ExceptionSupport.resolverResult("添加试题", this.getClass(), e);
		}
	}
	
	@RequestMapping(value = "/member/questionList", method = RequestMethod.GET)
	public ModelAndView questionList(String paperId , ModelAndView model){
		model.addObject("paper", iPaperService.find(paperId));
		model.setViewName("member/questionList");
		return model;
	}
	
	@RequestMapping(value = "/member/queryQuestionList" , method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryQuestionList(@RequestBody JSONObject data){
		String questionType ; String uid ; String content ; String paperId ;
			Integer page ; Integer limit;
		questionType = data.getString("questionType");
		uid = data.getString("uid");
		content = data.getString("content");
		paperId = data.getString("paperId");
		page = data.getInteger("page");
		limit = data.getInteger("limit");
		Page<QuestionEntity> p = new Page<>();
		p.setCurrent(page);
		p.setSize(limit);
		Page<QuestionEntity> list = iQuestionService.queryQuestionList(questionType , uid , content , paperId , p);
		return Result.jqGridResult(list.getRecords(), list.getTotal(), limit, page, list.getPages());
	}
	
	@RequestMapping(value = "/member/editQuestion", method = RequestMethod.GET)
	public ModelAndView editQuestion(String paperId , String questionId , ModelAndView model){
		model.setViewName("member/editQuestion");
		model.addObject("paper", iPaperService.find(paperId));
		model.addObject("questionId" , questionId);
		return model;
	}
	
	@RequestMapping(value = "/member/getQuestion", method = RequestMethod.GET)
	@ResponseBody
	public Result getQuestion(String questionId) {
		return Result.successResult(iQuestionService.select(questionId));
	}
	
	@RequestMapping(value = "/member/updateQuestion", method = RequestMethod.POST)
	@ResponseBody
	public Result updateQuestion(QuestionEntity question) {
		return iQuestionService.updateQuestion(question);
	}
	
	
	@RequestMapping(value = "/member/deleteQuestion", method = RequestMethod.GET)
	@ResponseBody
	public Result deleteQuestion(@RequestBody String uid) {
		return iQuestionService.delete(uid);
	}
}
