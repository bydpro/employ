package srmt.java.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the reward_score database table.
 * 
 */
@Entity
@Table(name="reward_score")
@NamedQuery(name="RewardScore.findAll", query="SELECT r FROM RewardScore r")
public class RewardScore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name="dishi_first")
	private double dishiFirst;

	@Column(name="dishi_second")
	private double dishiSecond;

	@Column(name="dishi_third")
	private double dishiThird;

	@Column(name="dishi2_first")
	private double dishi2First;

	@Column(name="dishi2_second")
	private double dishi2Second;

	@Column(name="dishi2_third")
	private double dishi2Third;

	@Column(name="dishi3_first")
	private double dishi3First;

	@Column(name="dishi3_second")
	private double dishi3Second;

	@Column(name="dishi3_third")
	private double dishi3Third;

	@Column(name="school_first")
	private double schoolFirst;

	@Column(name="school_second")
	private double schoolSecond;

	@Column(name="school_third")
	private double schoolThird;

	@Column(name="shen_fifth")
	private double shenFifth;

	@Column(name="shen_first")
	private double shenFirst;

	@Column(name="shen_fourth")
	private double shenFourth;

	@Column(name="shen_second")
	private double shenSecond;

	@Column(name="shen_third")
	private double shenThird;

	@Column(name="shen2_fifth")
	private double shen2Fifth;

	@Column(name="shen2_first")
	private double shen2First;

	@Column(name="shen2_fourth")
	private double shen2Fourth;

	@Column(name="shen2_second")
	private double shen2Second;

	@Column(name="shen2_third")
	private double shen2Third;

	@Column(name="shen3_fifth")
	private double shen3Fifth;

	@Column(name="shen3_first")
	private double shen3First;

	@Column(name="shen3_fourth")
	private double shen3Fourth;

	@Column(name="shen3_second")
	private double shen3Second;

	@Column(name="shen3_third")
	private double shen3Third;

	@Column(name="yong_teach")
	private double yongTeach;

	@Column(name="youxiu_jiaoxue")
	private double youxiuJiaoxue;

	public RewardScore() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getDishiFirst() {
		return this.dishiFirst;
	}

	public void setDishiFirst(double dishiFirst) {
		this.dishiFirst = dishiFirst;
	}

	public double getDishiSecond() {
		return this.dishiSecond;
	}

	public void setDishiSecond(double dishiSecond) {
		this.dishiSecond = dishiSecond;
	}

	public double getDishiThird() {
		return this.dishiThird;
	}

	public void setDishiThird(double dishiThird) {
		this.dishiThird = dishiThird;
	}

	public double getDishi2First() {
		return this.dishi2First;
	}

	public void setDishi2First(double dishi2First) {
		this.dishi2First = dishi2First;
	}

	public double getDishi2Second() {
		return this.dishi2Second;
	}

	public void setDishi2Second(double dishi2Second) {
		this.dishi2Second = dishi2Second;
	}

	public double getDishi2Third() {
		return this.dishi2Third;
	}

	public void setDishi2Third(double dishi2Third) {
		this.dishi2Third = dishi2Third;
	}

	public double getDishi3First() {
		return this.dishi3First;
	}

	public void setDishi3First(double dishi3First) {
		this.dishi3First = dishi3First;
	}

	public double getDishi3Second() {
		return this.dishi3Second;
	}

	public void setDishi3Second(double dishi3Second) {
		this.dishi3Second = dishi3Second;
	}

	public double getDishi3Third() {
		return this.dishi3Third;
	}

	public void setDishi3Third(double dishi3Third) {
		this.dishi3Third = dishi3Third;
	}

	public double getSchoolFirst() {
		return this.schoolFirst;
	}

	public void setSchoolFirst(double schoolFirst) {
		this.schoolFirst = schoolFirst;
	}

	public double getSchoolSecond() {
		return this.schoolSecond;
	}

	public void setSchoolSecond(double schoolSecond) {
		this.schoolSecond = schoolSecond;
	}

	public double getSchoolThird() {
		return this.schoolThird;
	}

	public void setSchoolThird(double schoolThird) {
		this.schoolThird = schoolThird;
	}

	public double getShenFifth() {
		return this.shenFifth;
	}

	public void setShenFifth(double shenFifth) {
		this.shenFifth = shenFifth;
	}

	public double getShenFirst() {
		return this.shenFirst;
	}

	public void setShenFirst(double shenFirst) {
		this.shenFirst = shenFirst;
	}

	public double getShenFourth() {
		return this.shenFourth;
	}

	public void setShenFourth(double shenFourth) {
		this.shenFourth = shenFourth;
	}

	public double getShenSecond() {
		return this.shenSecond;
	}

	public void setShenSecond(double shenSecond) {
		this.shenSecond = shenSecond;
	}

	public double getShenThird() {
		return this.shenThird;
	}

	public void setShenThird(double shenThird) {
		this.shenThird = shenThird;
	}

	public double getShen2Fifth() {
		return this.shen2Fifth;
	}

	public void setShen2Fifth(double shen2Fifth) {
		this.shen2Fifth = shen2Fifth;
	}

	public double getShen2First() {
		return this.shen2First;
	}

	public void setShen2First(double shen2First) {
		this.shen2First = shen2First;
	}

	public double getShen2Fourth() {
		return this.shen2Fourth;
	}

	public void setShen2Fourth(double shen2Fourth) {
		this.shen2Fourth = shen2Fourth;
	}

	public double getShen2Second() {
		return this.shen2Second;
	}

	public void setShen2Second(double shen2Second) {
		this.shen2Second = shen2Second;
	}

	public double getShen2Third() {
		return this.shen2Third;
	}

	public void setShen2Third(double shen2Third) {
		this.shen2Third = shen2Third;
	}

	public double getShen3Fifth() {
		return this.shen3Fifth;
	}

	public void setShen3Fifth(double shen3Fifth) {
		this.shen3Fifth = shen3Fifth;
	}

	public double getShen3First() {
		return this.shen3First;
	}

	public void setShen3First(double shen3First) {
		this.shen3First = shen3First;
	}

	public double getShen3Fourth() {
		return this.shen3Fourth;
	}

	public void setShen3Fourth(double shen3Fourth) {
		this.shen3Fourth = shen3Fourth;
	}

	public double getShen3Second() {
		return this.shen3Second;
	}

	public void setShen3Second(double shen3Second) {
		this.shen3Second = shen3Second;
	}

	public double getShen3Third() {
		return this.shen3Third;
	}

	public void setShen3Third(double shen3Third) {
		this.shen3Third = shen3Third;
	}

	public double getYongTeach() {
		return this.yongTeach;
	}

	public void setYongTeach(double yongTeach) {
		this.yongTeach = yongTeach;
	}

	public double getYouxiuJiaoxue() {
		return this.youxiuJiaoxue;
	}

	public void setYouxiuJiaoxue(double youxiuJiaoxue) {
		this.youxiuJiaoxue = youxiuJiaoxue;
	}

}