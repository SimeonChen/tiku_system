package com.tamguo.modules.tiku.service;

import java.util.List;

import com.tamguo.modules.tiku.model.MenuEntity;

/**
 * Service - 类型
 * 
 * @author candy.tam
 *
 */
public interface IMenuService {

	/** 获取类型数结构 */
	public List<MenuEntity> findMenus();
	
	/** 获取所有头部菜单 */
	public List<MenuEntity> findAllMenus();

	/** 获取左侧菜单*/
	public List<MenuEntity> findLeftMenus();
	
	/** 资格考试专区*/
	public List<MenuEntity> findFooterMenus();
	
	/** 首页章节学习*/
	public List<MenuEntity> findChapterMenus();

	/** 获取菜单树*/
	public List<MenuEntity> getMenuTree();
	
	/** 获取菜单*/
	public MenuEntity findById(String uid);

}
