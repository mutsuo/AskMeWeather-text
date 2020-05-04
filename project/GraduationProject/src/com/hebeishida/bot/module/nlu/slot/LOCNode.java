package com.hebeishida.bot.module.nlu.slot;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 */

/**
 *@desc:一句话被描述
 *@author 邓旸
 *@date:2020年2月7日下午3:38:04
 */
public class LOCNode {
	private Integer index = -1;
	private Integer parentIndex = -1;
	private String name = "";
	private boolean isLeaf = true;
	private List<Integer> childList = new ArrayList<Integer>();
	private String code = "";
	
	public LOCNode() {
		this.index = -1;
		this.parentIndex = -1;
		this.name = "";
		this.isLeaf = true;
		this.code = "";
	}
	public LOCNode(LOCNode node) {
		this.index = node.index;
		this.parentIndex = node.parentIndex;
		this.name = node.name;
		this.isLeaf = node.isLeaf;
		this.childList = node.childList;
		this.code = node.code;
	}
	
	public Integer getParentIndex() {
		return parentIndex;
	}
	public void setParentIndex(Integer parentIndex) {
		this.parentIndex = parentIndex;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isLeaf() {
		return isLeaf;
	}
	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
	public List<Integer> getChildList() {
		return childList;			
	}
	public void setChildList(List<Integer> childList) {
		this.childList = childList;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}
	
	public boolean isFilled() {
		boolean flag = false;
		if(this.index!=0 && this.index!=-1 && this.parentIndex!=-1 && !this.name.equals("")) {
			flag = true;
		}
			
		return flag;
	}
	
}
