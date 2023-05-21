package com.tamguo;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.config.redis.CacheService;
import com.tamguo.dao.*;
import com.tamguo.model.*;
import com.tamguo.model.enums.QuestionType;
import com.tamguo.model.vo.QuestionVo;
import com.xuxueli.crawler.XxlCrawler;
import com.xuxueli.crawler.conf.XxlCrawlerConf;
import com.xuxueli.crawler.loader.strategy.HtmlUnitPageLoader;
import com.xuxueli.crawler.parser.PageParser;
import com.xuxueli.crawler.rundata.RunData;
import com.xuxueli.crawler.util.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PaperQuestionCrawler {

	@Autowired
	QuestionMapper questionMapper;
	@Autowired
	CrawlerQuestionMapper crawlerQuestionMapper;
	@Autowired
	CrawlerPaperMapper crawlerPaperMapper;
	@Autowired
	ChapterMapper chapterMapper;
	@Autowired
	CourseMapper courseMapper;
	@Autowired
	SubjectMapper subjectMapper;
	@Autowired
	PaperMapper paperMapper;
	@Autowired
	CacheService cacheService;
	private static final String FILES_NO_FORMAT = "000000000";
	private static final String FILES_PREFIX = "shengwu";
	private static final String COURSE_ID = "shengwu";
	
	private RunData runData;

	@SuppressWarnings("unchecked")
	@Test
	public void crawlerQuestion() {
		
		XxlCrawler crawler = new XxlCrawler.Builder()
	            .setAllowSpread(false)
	            .setThreadCount(1)
	            .setFailRetryCount(5)
	            .setPageLoader(new HtmlUnitPageLoader())
	            .setPageParser(new PageParser<QuestionVo>() {
	            	
	                @Override
	                public void parse(Document html, Element pageVoElement, QuestionVo questionVo) {
	                	if(StringUtils.isEmpty(questionVo.getContent())) {
	                		runData.addUrl(html.baseUri());
	                		return;
	                	}
	                	CrawlerPaperEntity condition = new CrawlerPaperEntity();
	                	condition.setQuestionUrl(html.baseUri());
	                	System.out.println(html.baseUri());
	                	CrawlerPaperEntity crawlerPaper = crawlerPaperMapper.selectOne(condition);
	                	PaperEntity paper = paperMapper.selectById(crawlerPaper.getPaperId());
	                	CourseEntity course = courseMapper.selectById(paper.getCourseId());
	                	SubjectEntity subject = subjectMapper.selectById(paper.getSubjectId());
	                	
	                	QuestionType questionType = QuestionType.getQuestionType(questionVo.getQuestionType());
	                	

	                	QuestionEntity question = new QuestionEntity();
	                	if(questionType == QuestionType.DANXUANTI) {
	                		if(!StringUtils.isEmpty(questionVo.getQueoptions())) {
	                			question.setContent(questionVo.getContent() + questionVo.getQueoptions());	
	                		}else {
	                			question.setContent(questionVo.getContent());
	                		}
	                	}else {
	                		question.setContent(questionVo.getContent());
	                	}
	                	question.setAnalysis(questionVo.getAnalysis());
	                	if(StringUtils.isEmpty(question.getAnalysis())) {
	                		question.setAnalysis("<p> <span> 略 </span> <br> </p>");
	                	}
	                	question.setAnswer(questionVo.getAnswer());
	                	question.setAuditStatus("1");
	                	question.setChapterId("");
	                	question.setCourseId(course.getId());
	                	question.setPaperId(paper.getId());
	                	question.setQuestionType(questionType.getValue().toString());
	                	if(questionVo.getReviewPoint() != null && questionVo.getReviewPoint().size() > 0) {
		                	question.setReviewPoint(StringUtils.join(questionVo.getReviewPoint().toArray(), ","));
	                	}
	                	// 处理分数
	                	if(questionVo.getScore() != null) {
	                		if(questionVo.getScore().contains("分")) {
		                		question.setScore(questionVo.getScore());
		                	}
		                	if(questionVo.getScore().contains("年")) {
		                		question.setYear(questionVo.getScore());
		                	}
	                	}
	                	if(questionVo.getYear() != null) {
	                		if(questionVo.getYear().contains("年")) {
	                			question.setYear(questionVo.getYear());
	                		}
	                	}
	                	question.setSubjectId(subject.getId());
	                	
	                	if (questionVo.getAnswerImages()!=null && questionVo.getAnswerImages().size() > 0) {
                            Set<String> imagesSet = new HashSet<String>(questionVo.getAnswerImages());
                            for (String img: imagesSet) {

                                // 下载图片文件
                            	String fileName = getFileName(img);
                                String filePath = getFilePath();
                                String fileDatePath = getFileDatePath();
                                
                                File dir = new File(filePath +fileDatePath+ "/");
                				if (!dir.exists())
                					dir.mkdirs();
                                boolean ret = FileUtil.downFile(img, XxlCrawlerConf.TIMEOUT_MILLIS_DEFAULT, filePath +fileDatePath+ "/", fileName);
                                System.out.println("down images " + (ret?"success":"fail") + "：" + img);
                                
                                // 替换URL
                                question.setAnswer(question.getAnswer().replace(img, "/files/paper/" + COURSE_ID + '/' + fileDatePath + "/" + fileName));
                            }
                            question.setAnswer(question.getAnswer());
                        }
	                	
	                	if (questionVo.getAnalysisImages()!=null && questionVo.getAnalysisImages().size() > 0) {
                            Set<String> imagesSet = new HashSet<String>(questionVo.getAnalysisImages());
                            for (String img: imagesSet) {

                                // 下载图片文件
                                String fileName = getFileName(img);
                                String filePath = getFilePath();
                                String fileDatePath = getFileDatePath();
                                
                                File dir = new File(filePath +fileDatePath+ "/");
                				if (!dir.exists())
                					dir.mkdirs();
                                boolean ret = FileUtil.downFile(img, XxlCrawlerConf.TIMEOUT_MILLIS_DEFAULT, filePath +fileDatePath+ "/", fileName);
                                System.out.println("down images " + (ret?"success":"fail") + "：" + img);
                                
                                // 替换URL
                                question.setAnalysis(question.getAnalysis().replace(img, "/files/paper/" + COURSE_ID + '/' + fileDatePath + "/" + fileName));
                            }
                            question.setAnalysis(question.getAnalysis());
                        }
	                	
	                	if (questionVo.getContentImages()!=null && questionVo.getContentImages().size() > 0) {
                            Set<String> imagesSet = new HashSet<String>(questionVo.getContentImages());
                            for (String img: imagesSet) {

                                // 下载图片文件
                                String fileName = getFileName(img);
                                String filePath = getFilePath();
                                String fileDatePath = getFileDatePath();
                                File dir = new File(filePath +fileDatePath+ "/");
                				if (!dir.exists()) {
                					dir.mkdirs();
                				}
                                boolean ret = FileUtil.downFile(img, XxlCrawlerConf.TIMEOUT_MILLIS_DEFAULT, filePath +fileDatePath+ "/", fileName);
                                System.out.println("down images " + (ret?"success":"fail") + "：" + img);
                                
                                // 替换URL
                                question.setContent(question.getContent().replace(img, "/files/paper/" + COURSE_ID + '/' + fileDatePath + "/" + fileName));
                            }
                            question.setContent(question.getContent());
                        }
	                	
	                	
	                	// 处理图片
	                	question.setSourceType("baidu");
	                	question.setSourceUrl(html.baseUri());
	                	questionMapper.insert(question);
	                }
	                
	                public String getFileName(String img) {
	            		return getFileNo() + img.substring(img.lastIndexOf("."));
	            	}
	                
	                private String getFilePath() {
	            		return "/home/webdata/files/paper/" + COURSE_ID + "/";
	                }
	                
	                private String getFileDatePath() {
	                	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
	            		String format = sdf.format(new Date());
	            		return format;
	                }

	            	private String getFileNo() {
	            		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
	            		String format = sdf.format(new Date());
	            		DecimalFormat df = new DecimalFormat(FILES_NO_FORMAT);
	            		String key = FILES_PREFIX + format;
	            		Long incr = cacheService.incr(key);
	            		String avatorNo = FILES_PREFIX + df.format(incr);
	            		return avatorNo;
	            	}
	        }).build();
			
			runData = crawler.getRunData();
			int page = 1;
			int pageSize = 1000;
			while(true) {
				Page<CrawlerPaperEntity> questionPage = new Page<CrawlerPaperEntity>(page , pageSize);
				List<CrawlerPaperEntity> questionList = crawlerPaperMapper.selectPage(questionPage, Condition.create().orderAsc(Arrays.asList("paper_id" , "queindex")));
				for(int i=0 ;i<questionList.size() ; i++) {
					runData.addUrl(questionList.get(i).getQuestionUrl());
				}
				page++;
				if(questionList.size() < 1000) {
					break;
				}
			}
			// 获取科目
			crawler.start(true);
	}
	
		
    
}
