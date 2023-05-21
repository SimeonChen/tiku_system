package com.tamguo.service;

import java.util.List;
import com.baomidou.mybatisplus.plugins.Page;
import com.tamguo.model.PaperEntity;
import com.tamguo.util.Result;

public interface IPaperService {

	List<PaperEntity> findHistoryPaper();

	List<PaperEntity> findSimulationPaper();

	List<PaperEntity> findHotPaper(String areaId);

	Page<PaperEntity> findList(String subjectId , String courseId, String paperType,
			String year, String area , Integer pageNum);
	
	PaperEntity find(String paperId);

	List<PaperEntity> findPaperByAreaId(String areaId , String type);

	Long getPaperTotal();

	List<PaperEntity> findByCreaterId(String createrId);

	void updatePaperName(String paperId, String name);

	Result deletePaper(String paperId);

	void addPaperQuestionInfo(String paperId, String title, String name,
			String type);

	void updatePaperQuestionInfo(String paperId, String title, String name,
			String type, String uid);

	Result deletePaperQuestionInfoBtn(String paperId, String cuid);

	Page<PaperEntity> memberPaperList(String name, String memberId , Page<PaperEntity> page);

	void addPaper(PaperEntity paper);
	
	List<PaperEntity> featuredPaper(String type , String subjectId);

	/**
	 * 试卷添加下载推荐
	 * @param subjectId
	 * @param courseId
	 * @return
	 */
	List<PaperEntity> findHotPaper(String subjectId, String courseId);

	/**
	 * 修改试卷
	 * @param paper
	 */
	Result updatePaper(PaperEntity paper);

}
