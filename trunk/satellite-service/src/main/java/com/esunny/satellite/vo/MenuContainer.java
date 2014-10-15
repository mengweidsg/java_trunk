package com.esunny.satellite.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单容器
 * @author tss 2011-11-16
 *
 */
public class MenuContainer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4526494240360558638L;
	
	/**
	 * 当前菜单编号
	 */
	private String curMenuCode;
	
	/**
	 * 一级菜单编号
	 */
	private String rootMenuCode;
	
	private List<MenuVo> rootMenuList = new ArrayList<MenuVo>();
	
	private List<MenuVo> childMenuList = new ArrayList<MenuVo>();

	public String getCurMenuCode() {
		return curMenuCode;
	}

	public void setCurMenuCode(String curMenuCode) {
		this.curMenuCode = curMenuCode;
	}

	public String getRootMenuCode() {
		return rootMenuCode;
	}

	public void setRootMenuCode(String rootMenuCode) {
		this.rootMenuCode = rootMenuCode;
	}

	public List<MenuVo> getRootMenuList() {
		return rootMenuList;
	}

	public void setRootMenuList(List<MenuVo> rootMenuList) {
		this.rootMenuList = rootMenuList;
	}

	public List<MenuVo> getChildMenuList() {
		return childMenuList;
	}

	public void setChildMenuList(List<MenuVo> childMenuList) {
		this.childMenuList = childMenuList;
	}
	
	

}
