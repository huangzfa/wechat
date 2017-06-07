package com.base.modal;

/*
 * 系统用户
 * @author ypcheng
 *
 */
public class User 
{
	private static final long serialVersionUID=1L;
 
    private Integer ENABLE;
    private String role;
    private String city;
    private String country;
    private String headImgUrl;
    private String nickname;
    private String openId;
    private String sex;
    private String province;
    private String access_token;//非7200那个token

	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public Integer getENABLE() {
		return ENABLE;
	}
	public void setENABLE(Integer eNABLE) {
		ENABLE = eNABLE;
	}

	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	
}
