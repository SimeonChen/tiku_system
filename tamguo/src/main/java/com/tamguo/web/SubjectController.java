package com.tamguo.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.Condition;
import com.tamguo.model.BookEntity;
import com.tamguo.model.ChapterEntity;
import com.tamguo.model.CourseEntity;
import com.tamguo.model.SubjectEntity;
import com.tamguo.service.IAreaService;
import com.tamguo.service.IBookService;
import com.tamguo.service.IChapterService;
import com.tamguo.service.ISubjectService;
import com.tamguo.util.Result;

/**
 * Controller - 考试（高考，建造师，医药师）
 * 
 * @author candy.tam
 *
 */
@Controller
public class SubjectController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IChapterService iChapterService;
	@Autowired
	private IAreaService iAreaService;
	@Autowired
	private ISubjectService iSubjectService;
	@Autowired
	private IBookService iBookService;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = {"subject/{subjectId}.html"}, method = RequestMethod.GET)
    public ModelAndView indexAction(@PathVariable String subjectId , ModelAndView model) {
		try {
			SubjectEntity subject = iSubjectService.find(subjectId);
			// 获取第一个科目
			CourseEntity course = subject.getCourseList().get(0);
			// 获取第一本书
			List<BookEntity> bookList = iBookService.selectList(Condition.create().eq("course_id", course.getUid()));
			BookEntity book = bookList.get(0);
			
			List<ChapterEntity> chapterList = iChapterService.findCourseChapter(book.getUid());
	    	model.setViewName("subject");
	    	model.addObject("subject", subject);
	    	model.addObject("course" , course);
	    	model.addObject("courseList", subject.getCourseList());
	    	model.addObject("chapterList" , chapterList);
	    	model.addObject("areaList", iAreaService.findRootArea());
	        return model;
		} catch (Exception e) {
			logger.error(e.getMessage() , e);
			model.setViewName("500");
			return model;
		}
		
    }
	
	@RequestMapping(value = {"subject/getCourseTree.html"}, method = RequestMethod.GET)
	@ResponseBody
	public Result getCourseTree(){
		JSONArray list = iSubjectService.getCourseTree();
		return Result.successResult(list);
	}
	
	// [{"value":"11","label":"北京市","children":[{"value":"1101","label":"市辖区"}]}]
	@RequestMapping(value = {"subject/getCourseCascaderTree"}, method = RequestMethod.GET)
	@ResponseBody
	public Result getCourseCascaderTree() {
		JSONArray list = iSubjectService.getCourseCascaderTree();
		return Result.successResult(list);
	}
}
