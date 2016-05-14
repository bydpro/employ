package srmt.java.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the patent_score database table.
 * 
 */
@Entity
@Table(name="patent_score")
@NamedQuery(name="PatentScore.findAll", query="SELECT p FROM PatentScore p")
public class PatentScore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name="invent_fist")
	private double inventFist;

	@Column(name="invent_is_move")
	private double inventIsMove;

	@Column(name="practical_first")
	private double practicalFirst;

	@Column(name="practical_is_move")
	private double practicalIsMove;

	@Column(name="soft_first")
	private double softFirst;

	@Column(name="soft_is_move")
	private double softIsMove;

	@Column(name="view_first")
	private double viewFirst;

	@Column(name="view_is_move")
	private double viewIsMove;

	public PatentScore() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getInventFist() {
		return this.inventFist;
	}

	public void setInventFist(double inventFist) {
		this.inventFist = inventFist;
	}

	public double getInventIsMove() {
		return this.inventIsMove;
	}

	public void setInventIsMove(double inventIsMove) {
		this.inventIsMove = inventIsMove;
	}

	public double getPracticalFirst() {
		return this.practicalFirst;
	}

	public void setPracticalFirst(double practicalFirst) {
		this.practicalFirst = practicalFirst;
	}

	public double getPracticalIsMove() {
		return this.practicalIsMove;
	}

	public void setPracticalIsMove(double practicalIsMove) {
		this.practicalIsMove = practicalIsMove;
	}

	public double getSoftFirst() {
		return this.softFirst;
	}

	public void setSoftFirst(double softFirst) {
		this.softFirst = softFirst;
	}

	public double getSoftIsMove() {
		return this.softIsMove;
	}

	public void setSoftIsMove(double softIsMove) {
		this.softIsMove = softIsMove;
	}

	public double getViewFirst() {
		return this.viewFirst;
	}

	public void setViewFirst(double viewFirst) {
		this.viewFirst = viewFirst;
	}

	public double getViewIsMove() {
		return this.viewIsMove;
	}

	public void setViewIsMove(double viewIsMove) {
		this.viewIsMove = viewIsMove;
	}

}