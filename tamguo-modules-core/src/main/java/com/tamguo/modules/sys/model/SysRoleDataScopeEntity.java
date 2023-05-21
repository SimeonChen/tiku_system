package com.tamguo.modules.sys.model;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName(value="sys_role_data_scope")
public class SysRoleDataScopeEntity {
	
	private String roleCode;
	private String ctrlType;
	private String ctrlData;
	private String ctrlPermi;

}
