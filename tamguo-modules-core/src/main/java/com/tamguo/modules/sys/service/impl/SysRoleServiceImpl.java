package com.tamguo.modules.sys.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.tamguo.modules.sys.dao.SysMenuMapper;
import com.tamguo.modules.sys.dao.SysRoleDataScopeMapper;
import com.tamguo.modules.sys.dao.SysRoleMapper;
import com.tamguo.modules.sys.dao.SysRoleMenuMapper;
import com.tamguo.modules.sys.model.SysMenuEntity;
import com.tamguo.modules.sys.model.SysRoleDataScopeEntity;
import com.tamguo.modules.sys.model.SysRoleEntity;
import com.tamguo.modules.sys.model.SysRoleMenuEntity;
import com.tamguo.modules.sys.model.condition.SysRoleCondition;
import com.tamguo.modules.sys.service.ISysRoleService;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleEntity> implements ISysRoleService {
	
	@Autowired
	private SysRoleMapper sysRoleMapper;
	@Autowired
	private SysMenuMapper sysMenuMapper;
	@Autowired
	private SysRoleMenuMapper sysRoleMenuMapper;
	@Autowired
	private SysRoleDataScopeMapper sysRoleDataScopeMapper;

	@Transactional(readOnly=true)
	@Override
	public Page<SysRoleEntity> listData(SysRoleCondition condition) {
		Page<SysRoleEntity> page = new Page<>(condition.getPageNo(), condition.getPageSize());
		return page.setRecords(sysRoleMapper.listData(condition , page));
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> menuTreeData(String roleCode) {
		List<SysMenuEntity> menus = sysMenuMapper.selectList(Condition.create().eq("is_show", "1"));
		List<SysMenuEntity> roleMenus = sysMenuMapper.selectMenuByRoleId(roleCode);
		
		JSONObject result = new JSONObject();
		
		JSONObject menuInfo = transformZTree(menus);
		result.put("menuMap", menuInfo);
		result.put("roleMenuList", roleMenus);
		
		return result;
	}

	private JSONObject transformZTree(List<SysMenuEntity> menus) {
		JSONObject result = new JSONObject();
		if(menus != null) {
			JSONArray nodes = new JSONArray();
			for(int i=0 ; i<menus.size() ; i++) {
				SysMenuEntity menu = menus.get(i);
				
				JSONObject node = new JSONObject();
				node.put("name", menu.getMenuName() + "<font color=#888> &nbsp; &nbsp;    </font>");
				if(!StringUtils.isEmpty(menu.getMenuHref())) {
					node.put("name", menu.getMenuName() + "<font color=#888> &nbsp; &nbsp;  "+menu.getMenuHref()+"  </font>");
				}else if(!StringUtils.isEmpty(menu.getPermission())) {
					node.put("name", menu.getMenuName() + "<font color=#888> &nbsp; &nbsp;  "+menu.getPermission()+"  </font>");
				}
				node.put("pId", menu.getParentCode());
				node.put("id", menu.getMenuCode());
				node.put("title", menu.getMenuName());
				nodes.add(node);
			}
			result.put("default", nodes);
		}
		return result;
	}

	@Transactional(readOnly=false)
	@SuppressWarnings("unchecked")
	@Override
	public void allowMenuPermission(SysRoleEntity role) {
		// 删除关联菜单
		sysRoleMenuMapper.delete(Condition.create().eq("role_code", role.getRoleCode()));
		
		if(!StringUtils.isEmpty(role.getRoleMenuListJson())) {
			JSONArray roleMenus = JSONArray.parseArray(role.getRoleMenuListJson());
			for(int i=0 ; i<roleMenus.size() ; i++) {
				JSONObject menu = roleMenus.getJSONObject(i);
				SysRoleMenuEntity roleMenu = new SysRoleMenuEntity();
				roleMenu.setRoleCode(role.getRoleCode());
				roleMenu.setMenuCode(menu.getString("menuCode"));
				sysRoleMenuMapper.insert(roleMenu);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void allowDataScope(SysRoleEntity role) {
		// 删除权限
		sysRoleDataScopeMapper.delete(Condition.create().eq("role_code", role.getRoleCode()));
		
		// 插入权限
		if(!StringUtils.isEmpty(role.getRoleDataScopeListJson())) {
			JSONArray roleDataScopes = JSONArray.parseArray(role.getRoleDataScopeListJson());
			for(int i=0 ; i<roleDataScopes.size() ; i++) {
				JSONObject dataScope = roleDataScopes.getJSONObject(i);
				SysRoleDataScopeEntity roleDataScope = new SysRoleDataScopeEntity();
				roleDataScope.setRoleCode(role.getRoleCode());
				roleDataScope.setCtrlPermi("1");
				roleDataScope.setCtrlType(dataScope.getString("ctrlType"));
				roleDataScope.setCtrlData(dataScope.getString("ctrlData"));
				sysRoleDataScopeMapper.insert(roleDataScope);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONArray treeDate(String userType) {
		if(StringUtils.isEmpty(userType)) {
			return this.transformTreeDate(sysRoleMapper.selectList(Condition.create().eq("is_sys", "0")));
		}
		return this.transformTreeDate(sysRoleMapper.selectList(Condition.create().eq("is_sys", "0").eq("user_type", userType)));
	}

	private JSONArray transformTreeDate(List<SysRoleEntity> roles) {
		if(roles != null) {
			JSONArray entitys = new JSONArray();
			for(int i=0 ; i<roles.size() ; i++) {
				JSONObject entity = new JSONObject();
				entity.put("id", roles.get(i).getRoleCode());
				entity.put("name", roles.get(i).getRoleName());
				entitys.add(entity);
			}
			return entitys;
		}
		return null;
	}
	
	@Transactional(readOnly=false)
	@Override
	public void update(SysRoleEntity role) {
		SysRoleEntity entity = sysRoleMapper.selectById(role.getRoleCode());
		entity.setRoleName(role.getRoleName());
		entity.setRoleSort(role.getRoleSort());
		entity.setUserType(role.getUserType());
		entity.setIsSys(role.getIsSys());
		entity.setRoleType(role.getRoleType());
		
		entity.setUpdateDate(new Date());
		entity.setRemarks(role.getRemarks());
		sysRoleMapper.updateById(entity);
	}

	@Transactional(readOnly=false)
	@Override
	public void save(SysRoleEntity role) {
		role.setCreateDate(new Date());
		role.setUpdateDate(new Date());
		
		sysRoleMapper.insert(role);
		
		if(!StringUtils.isEmpty(role.getRoleMenuListJson())) {
			JSONArray roleMenus = JSONArray.parseArray(role.getRoleMenuListJson());
			for(int i=0 ; i<roleMenus.size() ; i++) {
				JSONObject menu = roleMenus.getJSONObject(i);
				SysRoleMenuEntity roleMenu = new SysRoleMenuEntity();
				roleMenu.setRoleCode(role.getRoleCode());
				roleMenu.setMenuCode(menu.getString("menuCode"));
				sysRoleMenuMapper.insert(roleMenu);
			}
		}
	}
}
