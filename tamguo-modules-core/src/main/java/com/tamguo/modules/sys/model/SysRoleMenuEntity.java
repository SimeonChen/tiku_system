package com.tamguo.modules.sys.model;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

@Data
@TableName(value="sys_role_menu")
public class SysRoleMenuEntity {

	private String roleCode;
	private String menuCode;

}
