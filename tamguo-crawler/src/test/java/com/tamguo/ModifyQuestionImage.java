package com.tamguo;

import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.model.QuestionEntity;
import com.tamguo.service.IQuestionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

/**
 * Test - 修改用户图片
 * 
 * @author tamguo
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ModifyQuestionImage {
	
	@Autowired
	private IQuestionService iQuestionService;

	@SuppressWarnings("unchecked")
	@Test
	public void modify() {
		Integer current = 1 ; 
		Integer size = 100;
		
		while(true) {
			Page<QuestionEntity> page = new Page<QuestionEntity>(current , size);
			Page<QuestionEntity> entitys = iQuestionService.selectPage(page , Condition.create().orderAsc(Arrays.asList("id")));
			if(entitys.getCurrent() > 759) {
				break;
			}
			// 处理数据
			for(int i=0 ; i<entitys.getSize() ; i++) {
				QuestionEntity question = entitys.getRecords().get(i);
				question.setAnalysis(question.getAnalysis().replaceAll("files/", "/files/"));
				question.setContent(question.getContent().replaceAll("files/", "/files/"));
				if(question.getAnswer() == null) {
					question.setAnswer("");
				}
				question.setAnswer(question.getAnswer().replaceAll("files/", "/files/"));
			}
			iQuestionService.updateAllColumnBatchById(entitys.getRecords());
			current = current + 1;
			System.out.println("当前Current" + current);
		}
	}
}
