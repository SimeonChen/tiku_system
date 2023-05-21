package com.tamguo.modules.sys.model.enums;

import java.io.Serializable;
import com.baomidou.mybatisplus.enums.IEnum;

/**
 * 用户状态
 */
public enum SysUserStatusEnum implements IEnum {
	NORMAL("0", "正常"),
	DELETE("1" , "删除"),
	DISABLED("2" , "禁用"),
	LOCKED("3", "锁定"),;

    private String value;
    private String desc;

    SysUserStatusEnum(final String value, final String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Serializable getValue() {
        return this.value;
    }

    public String getDesc(){
        return this.desc;
    }
    
    @Override
    public String toString() {
    	return this.value;
    }
}
