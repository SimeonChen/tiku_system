package com.tamguo.service.impl;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tamguo.dao.PaperMapper;
import com.tamguo.dao.QuestionMapper;
import com.tamguo.dao.redis.CacheService;
import com.tamguo.model.PaperEntity;
import com.tamguo.service.IPaperService;
import com.tamguo.util.Result;
import com.tamguo.util.ShiroUtils;
import com.tamguo.util.TamguoConstant;

@Service
public class PaperService extends ServiceImpl<PaperMapper, PaperEntity> implements IPaperService{
	
	@Autowired
	private PaperMapper paperMapper;
	@Autowired
	private CacheService cacheService;
	@Autowired
	private QuestionMapper questionMapper;

	@SuppressWarnings("unchecked")
	@Override
	public List<PaperEntity> findHistoryPaper() {
		List<PaperEntity> paperList = (List<PaperEntity>) cacheService.getObject(TamguoConstant.HISTORY_PAPER);
		if(paperList == null || paperList.isEmpty()){
			Page<PaperEntity> page = new Page<>(1 , 6);
			paperList = paperMapper.findByTypeAndAreaId(TamguoConstant.ZHENGTI_PAPER_ID , TamguoConstant.BEIJING_AREA_ID , page);
			cacheService.setObject(TamguoConstant.ZHENGTI_PAPER_ID, paperList , 2 * 60 * 60);
		}
		return paperList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PaperEntity> findSimulationPaper() {
		List<PaperEntity> paperList = (List<PaperEntity>) cacheService.getObject(TamguoConstant.SIMULATION_PAPER);
		if(paperList == null || paperList.isEmpty()){
			Page<PaperEntity> page = new Page<>(1 , 6);
			paperList = paperMapper.findByTypeAndAreaId(TamguoConstant.MONI_PAPER_ID , TamguoConstant.BEIJING_AREA_ID , page);
			cacheService.setObject(TamguoConstant.SIMULATION_PAPER, paperList , 2 * 60 * 60);
		}
		return paperList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PaperEntity> findHotPaper(String areaId) {
		List<PaperEntity> paperList = (List<PaperEntity>) cacheService.getObject(TamguoConstant.HOT_PAPER);
		paperList = null;
		if(paperList == null || paperList.isEmpty()){
			Page<PaperEntity> page = new Page<>(1 , 10);
			paperList = paperMapper.findByAreaId(areaId ,page);
			cacheService.setObject(TamguoConstant.HOT_PAPER, paperList , 2 * 60 * 60);
		}
		return paperList;
	}

	@Override
	public Page<PaperEntity> findList(String subjectId , String courseId,
			String paperType, String year, String area , Integer pageNum) {
		Page<PaperEntity> page = new Page<>(pageNum , TamguoConstant.DEFAULT_PAGE_SIZE);
		if("0".equals(courseId)) {
			courseId = "";
		}
		if("0".equals(paperType)) {
			paperType = "";
		}
		if("0".equals(year)) {
			year = "";
		}
		if("0".equals(area)) {
			area = "";
		}
		return page.setRecords(paperMapper.findList(subjectId , courseId , paperType , year , area , page));
	}

	@Override
	public PaperEntity find(String paperId) {
		return paperMapper.selectById(paperId);
	}

	@Override
	public List<PaperEntity> findPaperByAreaId(String areaId , String type) {
		if("n".equals(type)){
			return this.findHotPaper(areaId);
		}
		Page<PaperEntity> page = new Page<>(1 , 8);
		return paperMapper.findPaperByAreaId(areaId , type , page);
	}

	@Override
	public Long getPaperTotal() {
		return paperMapper.getPaperTotal();
	}

	@Override
	public List<PaperEntity> findByCreaterId(String createrId) {
		return paperMapper.findByCreaterId(createrId);
	}

	@Transactional(readOnly=false)
	@Override
	public void updatePaperName(String paperId, String name) {
		PaperEntity paper = paperMapper.selectById(paperId);
		paper.setName(name);
		paperMapper.updateById(paper);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Result deletePaper(String paperId) {
		PaperEntity paper = paperMapper.selectById(paperId);
		if(!ShiroUtils.getUserId().equals(paper.getCreaterId())) {
			return Result.result(501, null , "不能删除其他人的试卷！");
		}
		paperMapper.deleteById(paperId);
		// 删除试题
		questionMapper.delete(Condition.create().eq("paper_id", paperId));
		return Result.result(Result.SUCCESS_CODE, null , "删除成功！");
	}

	@Transactional(readOnly=false)
	@Override
	public void addPaperQuestionInfo(String paperId, String title,
			String name, String type) {
		PaperEntity paper = paperMapper.selectById(paperId);
		String questionInfo = paper.getQuestionInfo();
		
		JSONArray qList = JSONArray.parseArray(questionInfo);
		JSONObject entity = new JSONObject();
		entity.put("name", name);
		entity.put("title", title);
		entity.put("type", type);
		entity.put("uid", UUID.randomUUID().toString());
		qList.add(entity);
		
		paper.setQuestionInfo(qList.toString());
		paperMapper.updateById(paper);
	}

	@Transactional(readOnly=false)
	@Override
	public void updatePaperQuestionInfo(String paperId, String title,
			String name, String type , String uid) {
		PaperEntity paper = paperMapper.selectById(paperId);
		JSONArray qList = JSONArray.parseArray(paper.getQuestionInfo());
		for(int i =0 ; i<qList.size() ; i++){
			JSONObject q = qList.getJSONObject(i);
			if(q.getString("uid").equals(uid)){
				q.put("name", name);
				q.put("title", title);
				q.put("type", type);
			}
		}
		
		paper.setQuestionInfo(qList.toString());
		paperMapper.updateById(paper);
	}

	@Override
	public Result deletePaperQuestionInfoBtn(String paperId, String uid) {
		PaperEntity paper = paperMapper.selectById(paperId);
		if(!paper.getCreaterId().equals(ShiroUtils.getMember().getUid())) {
			return Result.failResult("试卷属于当前用户，不能修改！");
		}
		JSONArray qList = JSONArray.parseArray(paper.getQuestionInfo());
		for(int i =0 ; i<qList.size() ; i++){
			JSONObject q = qList.getJSONObject(i);
			if(q.getString("uid").equals(uid)){
				qList.remove(i);
			}
		}
		paper.setQuestionInfo(qList.toString());
		paperMapper.updateById(paper);
		return Result.result(Result.SUCCESS_CODE, null, "删除子卷成功");
	}


	@Override
	public Page<PaperEntity> memberPaperList(String name , String memberId , Page<PaperEntity> page) {
		if(!StringUtils.isEmpty(name)){
			name = "%" + name + "%";
		}
		return page.setRecords(paperMapper.queryPageByNameAndCreatorId(name , memberId , page));
	}

	@Transactional(readOnly=false)
	@Override
	public void addPaper(PaperEntity paper) {
		paper.setDownHits(0);
		paper.setOpenHits(0);
		paper.setQuestionInfo("[]");
		
		// 写入seo信息
		paper.setSeoTitle(paper.getName());
		paper.setSeoKeywords(paper.getName());
		paper.setSeoDescription(paper.getName());
		paperMapper.insert(paper);
	}

	@Transactional(readOnly=true)
	@Override
	public List<PaperEntity> featuredPaper(String type, String subjectId) {
		Page<PaperEntity> page = new Page<>(1,8);
		return paperMapper.featuredPaper(type , subjectId , page);
	}

	@Transactional(readOnly=true)
	@Override
	public List<PaperEntity> findHotPaper(String subjectId, String courseId) {
		Page<PaperEntity> page = new Page<>(1,5);
		return paperMapper.findHotPaper(subjectId , courseId , page);
	}

	@Transactional(readOnly=false)
	@Override
	public Result updatePaper(PaperEntity paper) {
		PaperEntity entity = paperMapper.selectById(paper.getUid());
		if(!entity.getCreaterId().equals(ShiroUtils.getMember().getUid())) {
			return Result.failResult("试卷属于当前用户，不能修改！");
		}
		paper.setCreaterId(ShiroUtils.getUserId());
		paperMapper.updateById(paper);
		return Result.result(Result.SUCCESS_CODE, paper, "修改成功");
	}

}
