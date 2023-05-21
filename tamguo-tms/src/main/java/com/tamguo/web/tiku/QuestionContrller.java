package com.tamguo.web.tiku;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.http.HttpServletRequest;

import com.tamguo.modules.tiku.model.*;
import com.tamguo.modules.tiku.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.common.utils.Result;
import com.tamguo.modules.tiku.model.enums.QuestionTypeEnum;
import com.tamguo.modules.tiku.model.queue.LearnChapterQueue;
import com.tamguo.utils.BrowserUtils;

@Controller
public class QuestionContrller {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IQuestionService iQuestionService;
	@Autowired
	private IChapterService iChapterService;
	@Autowired
	private ISubjectService iSubjectService;
	@Autowired
	private ICourseService iCourseService;
	@Autowired
	private IQuestionAnswerService iQuestionAnswerService;
	@Autowired
	private IQuestionOptionsService iQuestionOptionsService;

	// 章节
	BlockingQueue<ChapterEntity> chapterQueue = new LinkedBlockingQueue<ChapterEntity>(10);
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = {"questionlist/{chapterId}-{current}-{size}.html"}, method = RequestMethod.GET)
	public ModelAndView questionList(@PathVariable String chapterId , @PathVariable Integer current , 
			@PathVariable Integer size , ModelAndView model , HttpServletRequest request) throws InterruptedException{
		// request url 
		logger.info("request url :{} " , request.getRequestURI());

		ChapterEntity chapter = iChapterService.selectById(chapterId);
		CourseEntity course = iCourseService.selectById(chapter.getCourseId());
		SubjectEntity subject = iSubjectService.selectById(course.getSubjectId());
		ChapterEntity parentChapter = iChapterService.selectById(chapter.getParentCode());
		ChapterEntity nextChapter = iChapterService.selectNextChapter(chapter.getParentCode() , chapter.getId());
		
		Page<QuestionEntity> page = new Page<>();
		page.setCurrent(current);
		page.setSize(size);
		Page<QuestionEntity> questionList = iQuestionService.selectPage(page , Condition.create().eq("chapter_id", chapterId).orderDesc(Arrays.asList("id")));
		for(int i=0 ;i<questionList.getRecords().size() ; i++) {
			QuestionEntity question = questionList.getRecords().get(i);
			List<QuestionOptionsEntity> questionOptions = iQuestionOptionsService.selectList(Condition.create().eq("question_id" , question.getId()));
			question.setQuestionOptions(questionOptions);
			question.setQuestionType(QuestionTypeEnum.getQuestionType(question.getQuestionType()).getDesc());
		}
		model.addObject("subject", subject);
		model.addObject("course", course);
		model.addObject("chapter", chapter);
		model.addObject("parentChapter" , parentChapter);
		model.addObject("nextChapter" , nextChapter);
		model.addObject("questionList", questionList);
		model.addObject("subjectId", course.getSubjectId());
		model.addObject("courseId", course.getId());
		
		LearnChapterQueue.add(chapter);
		
		if(BrowserUtils.isMobile(request.getHeader("user-agent"))) {
    		model.setViewName("mobile/questionList");
    	}else {
    		model.setViewName("questionList");
    	}
		return model;
	}
	
	/**
	 * 直接访问题目
	 * @param uid
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = {"/question/{uid}.html"}, method = RequestMethod.GET)
	public ModelAndView question(@PathVariable String uid , ModelAndView model , HttpServletRequest request){
		// request url 
		logger.info("request url :{}" , request.getRequestURI());
		model.setViewName("question");
		QuestionEntity question = iQuestionService.selectById(uid);
		List<QuestionOptionsEntity> questionOptions = iQuestionOptionsService.selectList(Condition.create().eq("question_id" , question.getId()));
		question.setQuestionOptions(questionOptions);
		question.setQuestionType(QuestionTypeEnum.getQuestionType(question.getQuestionType()).getDesc());
		model.addObject("question", question);
		model.addObject("course", iCourseService.selectById(question.getCourseId()));
		model.addObject("answerList", iQuestionAnswerService.selectList(Condition.create().eq("question_id", uid).orderDesc(Arrays.asList("create_date"))));
		if(BrowserUtils.isMobile(request.getHeader("user-agent"))) {
    		model.setViewName("mobile/question");
    	}else {
    		model.setViewName("question");
    	}
		return model;
	}
	
	@RequestMapping(value = {"question/getQuestion/{uid}.html"}, method = RequestMethod.GET)
	@ResponseBody
	public Result getQuestion(@PathVariable String uid , ModelAndView model){
		return Result.successResult(iQuestionService.selectById(uid));
	}
	
}
