package com.tamguo.modules.sys.service;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.service.IService;
import com.tamguo.modules.sys.model.SysCompanyEntity;
import com.tamguo.modules.sys.model.condition.SysCompanyCondition;

public interface ISysCompanyService extends IService<SysCompanyEntity>{
	
	/** 公司树形结构*/
	JSONArray treeData(String excludeId);

	/** 查询公司列表*/
	List<SysCompanyEntity> listData(SysCompanyCondition condition);
	
	/** 根据ID查询公司*/
	SysCompanyEntity selectByCode(String code);

	/** 新建公司*/
	void save(SysCompanyEntity company);

	/** 修改公司*/
	void update(SysCompanyEntity company);
}
