package com.tamguo.modules.tiku.model;

import java.io.Serializable;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.annotations.TableName;
import com.tamguo.config.dao.SuperEntity;

@Data
@TableName(value="t_ad")
public class AdEntity extends SuperEntity<AdEntity> implements Serializable {
	private static final long serialVersionUID = 1L;

	private String businessKey;
	private String name;
	private String adInfo;

	public JSONArray getAds(){
		if(StringUtils.isEmpty(adInfo)){
			return null;
		}
		return JSONArray.parseArray(adInfo);
	}


}