package com.base.modal.menu;
//-表示二级菜单(CLICK类型)
public class CommonButton extends Button{
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	private String type;//菜单类型
	private String key;//key值
}
