package com.tamguo.web.tiku;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.mapper.Condition;
import com.tamguo.modules.tiku.model.KnowPointEntity;
import com.tamguo.modules.tiku.model.ChapterEntity;
import com.tamguo.modules.tiku.model.CourseEntity;
import com.tamguo.modules.tiku.model.SubjectEntity;
import com.tamguo.modules.tiku.service.IKnowPointService;
import com.tamguo.modules.tiku.service.IChapterService;
import com.tamguo.modules.tiku.service.ICourseService;
import com.tamguo.modules.tiku.service.ISubjectService;

@Controller
public class KnowPointController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	IKnowPointService iKnowPointService;
	@Autowired
	IChapterService iChapterService;
	@Autowired
	ISubjectService iSubjectService;
	@Autowired
	ICourseService iCourseService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = {"knowpoint/{uid}.html"}, method = RequestMethod.GET)
	public ModelAndView index(@PathVariable String uid , ModelAndView model , HttpServletRequest request) {
		// request url 
		logger.info("request url :{} " , request.getRequestURI());
		KnowPointEntity knowpoint = iKnowPointService.selectById(uid);
		SubjectEntity subject = iSubjectService.selectById(knowpoint.getSubjectId());
		List<CourseEntity> courseList = iCourseService.selectList(Condition.create().eq("subject_id", subject.getId()).orderAsc(Arrays.asList("sort")));
		List<KnowPointEntity> knowPointList = iKnowPointService.selectList(Condition.create().eq("course_id", knowpoint.getCourseId()));
		CourseEntity course = iCourseService.selectById(knowpoint.getCourseId());
		List<ChapterEntity> chapterList = iChapterService.findChapterTree(knowpoint.getId());
		model.addObject("knowpoint", knowpoint);
		model.addObject("subject", subject);
		model.addObject("course", course);
		model.addObject("chapterList" , chapterList);
		model.addObject("courseList", courseList);
		model.addObject("knowPointList", knowPointList);
		model.setViewName("knowpoint");
		return model;
	}
	
}
