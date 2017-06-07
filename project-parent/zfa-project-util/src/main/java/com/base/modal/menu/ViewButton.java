package com.base.modal.menu;
//-表示二级菜单（VIEW类型）
public class ViewButton extends Button{
	private String type;//菜单类型
	private String url;//view路径值
	private Button[] sub_button;//子级菜单，因为一个一级菜单可以有多个二级菜单，所以这儿使用二级 菜单类型的集合

	public Button[] getSub_button() {
		return sub_button;
	}
	public void setSub_button(Button[] sub_button) {
		this.sub_button = sub_button;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
