package com.tamguo.modules.sys.model;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName(value="sys_user_data_scope")
public class SysUserDataScopeEntity {

	private String userCode;
	private String ctrlType;
	private String ctrlData;
	private String ctrlPermi;

}
