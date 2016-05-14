package srmt.java.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;


/**
 * The persistent class for the thesis_score database table.
 * 
 */
@Entity
@Table(name="thesis_score")
@NamedQuery(name="ThesisScore.findAll", query="SELECT t FROM ThesisScore t")
public class ThesisScore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "generator", strategy = "uuid")
	@GeneratedValue(generator = "generator")
	private String id;

	@Column(name="thesis_chinese")
	private double thesisChinese;

	@Column(name="thesis_eishou")
	private double thesisEishou;

	@Column(name="thesis_eishoulu")
	private double thesisEishoulu;

	@Column(name="thesis_eiyuan")
	private double thesisEiyuan;

	@Column(name="thesis_guoji")
	private double thesisGuoji;

	@Column(name="thesis_jiaowu")
	private double thesisJiaowu;

	@Column(name="thesis_other")
	private double thesisOther;

	@Column(name="thesis_shoulu")
	private double thesisShoulu;

	@Column(name="thesis_shousci")
	private double thesisShousci;

	public ThesisScore() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getThesisChinese() {
		return this.thesisChinese;
	}

	public void setThesisChinese(double thesisChinese) {
		this.thesisChinese = thesisChinese;
	}

	public double getThesisEishou() {
		return this.thesisEishou;
	}

	public void setThesisEishou(double thesisEishou) {
		this.thesisEishou = thesisEishou;
	}

	public double getThesisEishoulu() {
		return this.thesisEishoulu;
	}

	public void setThesisEishoulu(double thesisEishoulu) {
		this.thesisEishoulu = thesisEishoulu;
	}

	public double getThesisEiyuan() {
		return this.thesisEiyuan;
	}

	public void setThesisEiyuan(double thesisEiyuan) {
		this.thesisEiyuan = thesisEiyuan;
	}

	public double getThesisGuoji() {
		return this.thesisGuoji;
	}

	public void setThesisGuoji(double thesisGuoji) {
		this.thesisGuoji = thesisGuoji;
	}

	public double getThesisJiaowu() {
		return this.thesisJiaowu;
	}

	public void setThesisJiaowu(double thesisJiaowu) {
		this.thesisJiaowu = thesisJiaowu;
	}

	public double getThesisOther() {
		return this.thesisOther;
	}

	public void setThesisOther(double thesisOther) {
		this.thesisOther = thesisOther;
	}

	public double getThesisShoulu() {
		return this.thesisShoulu;
	}

	public void setThesisShoulu(double thesisShoulu) {
		this.thesisShoulu = thesisShoulu;
	}

	public double getThesisShousci() {
		return this.thesisShousci;
	}

	public void setThesisShousci(double thesisShousci) {
		this.thesisShousci = thesisShousci;
	}

}