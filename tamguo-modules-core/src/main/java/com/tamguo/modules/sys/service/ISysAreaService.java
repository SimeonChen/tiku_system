package com.tamguo.modules.sys.service;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.service.IService;
import com.tamguo.common.utils.Result;
import com.tamguo.modules.sys.model.SysAreaEntity;
import com.tamguo.modules.sys.model.condition.SysAreaCondition;

public interface ISysAreaService extends IService<SysAreaEntity>{

	List<SysAreaEntity> listData(SysAreaCondition condition);

	JSONArray treeData(String excludeId);

	/** 保存地区*/
	void save(SysAreaEntity area);

	/** 修改地区*/
	void update(SysAreaEntity area);
	
	/** 树形*/
	Result findAreaTree();

}
