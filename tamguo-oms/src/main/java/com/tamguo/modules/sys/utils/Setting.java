package com.tamguo.modules.sys.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Setting - 系统
 * 
 * @author candy.tam
 *
 */
@Component
public final class Setting {

	/** 域名 */
	@Value(value="${domain.name}")
	public String domain;
	/** 静态资源地址*/
	@Value(value="${static.domain}")
	public String staticDomain;
	@Value(value="${version}")
	public String version;
	/** 真题 */
	public final String PAPER_TYPE_ZHENTI = "1";
	/** 模拟*/
	public final String PAPER_TYPE_MONI = "2";
	/** 押题*/
	public final String PAPER_TYPE_YATI = "3";
	/** 名校 */
	public final String PAPER_TYPE_MINGXIAO = "4";
	
}
