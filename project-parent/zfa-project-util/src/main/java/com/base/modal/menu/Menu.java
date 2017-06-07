package com.base.modal.menu;
//-表示要创建的菜单
public class Menu {
	public ViewButton[] getButton() {
		return button;
	}

	public void setButton(ViewButton[] button) {
		this.button = button;
	}

	private ViewButton[] button;
/*    private ViewButton[] view;
    public ViewButton[] getView(){
    	return view;
    }
    public void setView(ViewButton[] view){
    	this.view=view;
    }*/
}
