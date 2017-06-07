package com.base.modal;
/**
 * 与普通Java类不同的是，Face类实现了Comparable接口，并实现了该接口的compareTo()方法，这正是Java中对象排序的关键所在。
 * 131-139行代码是通过比较每个Face的脸部中心点的横坐标来决定对象的排序方式，这样能够实现检测出的多个Face按从左至右的先后顺序进行排序。
 * @author 15935
 *
 */
public class Face {
	//人脸数目
    private Integer result_num;
    //美丑打分，范围0-1，越大表示越美。face_fields包含beauty时返回
    private double beauty;
    //人脸置信度，范围0-1
    private double face_probability;
    //表情，0，不笑；1，微笑；2，大笑。face_fields包含expression时返回
    private Integer expression;    
    //脸型：square/triangle/oval/heart/round
    private String type;
    //是否带眼镜，0-无眼镜，1-普通眼镜，2-墨镜。face_fields包含glasses时返回
    private Integer glasses;
    //真实人脸置信度，[0, 1]
    private double human;
    private double age;
    //yellow、white、black、arabs。face_fields包含race时返回
    private String race;   
    //性别male、female。face_fields包含gender时返回
    private String gender;
    

	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getRace() {
		return race;
	}
	public void setRace(String race) {
		this.race = race;
	}
	public double getAge() {
		return age;
	}
	public void setAge(double age) {
		this.age = age;
	}
	public Integer getResult_num() {
		return result_num;
	}
	public void setResult_num(Integer result_num) {
		this.result_num = result_num;
	}
	public double getBeauty() {
		return beauty;
	}
	public void setBeauty(double beauty) {
		this.beauty = beauty;
	}
	public double getFace_probability() {
		return face_probability;
	}
	public void setFace_probability(double face_probability) {
		this.face_probability = face_probability;
	}
	public Integer getExpression() {
		return expression;
	}
	public void setExpression(Integer expression) {
		this.expression = expression;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getGlasses() {
		return glasses;
	}
	public void setGlasses(Integer glasses) {
		this.glasses = glasses;
	}
	public double getHuman() {
		return human;
	}
	public void setHuman(double human) {
		this.human = human;
	}

}
