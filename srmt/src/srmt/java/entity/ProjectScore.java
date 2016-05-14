package srmt.java.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;


/**
 * The persistent class for the project_score database table.
 * 
 */
@Entity
@Table(name="project_score")
@NamedQuery(name="ProjectScore.findAll", query="SELECT p FROM ProjectScore p")
public class ProjectScore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "generator", strategy = "uuid")
	@GeneratedValue(generator = "generator")
	private String id;

	private double guoFund;

	private double guoFundLast;

	private double guoK;

	private double otherFund;

	private double otherFundLast;

	private double otherK;

	private double shenFund;

	private double shenFundLast;

	private double shenK;

	public ProjectScore() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getGuoFund() {
		return this.guoFund;
	}

	public void setGuoFund(double guoFund) {
		this.guoFund = guoFund;
	}

	public double getGuoFundLast() {
		return this.guoFundLast;
	}

	public void setGuoFundLast(double guoFundLast) {
		this.guoFundLast = guoFundLast;
	}

	public double getGuoK() {
		return this.guoK;
	}

	public void setGuoK(double guoK) {
		this.guoK = guoK;
	}

	public double getOtherFund() {
		return this.otherFund;
	}

	public void setOtherFund(double otherFund) {
		this.otherFund = otherFund;
	}

	public double getOtherFundLast() {
		return this.otherFundLast;
	}

	public void setOtherFundLast(double otherFundLast) {
		this.otherFundLast = otherFundLast;
	}

	public double getOtherK() {
		return this.otherK;
	}

	public void setOtherK(double otherK) {
		this.otherK = otherK;
	}

	public double getShenFund() {
		return this.shenFund;
	}

	public void setShenFund(double shenFund) {
		this.shenFund = shenFund;
	}

	public double getShenFundLast() {
		return this.shenFundLast;
	}

	public void setShenFundLast(double shenFundLast) {
		this.shenFundLast = shenFundLast;
	}

	public double getShenK() {
		return this.shenK;
	}

	public void setShenK(double shenK) {
		this.shenK = shenK;
	}

}