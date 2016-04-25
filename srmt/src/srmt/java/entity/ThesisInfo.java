package srmt.java.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;


/**
 * The persistent class for the thesis_info database table.
 * 
 */
@Entity
@Table(name="thesis_info")
@NamedQuery(name="ThesisInfo.findAll", query="SELECT t FROM ThesisInfo t")
public class ThesisInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "generator", strategy = "uuid")
	@GeneratedValue(generator = "generator")
	@Column(name="thesis_id")
	private String thesisId;

	@Column(name="thesis_abstract")
	private String thesisAbstract;

	@Column(name="thesis_author")
	private String thesisAuthor;

	@Temporal(TemporalType.DATE)
	@Column(name="thesis_date")
	private Date thesisDate;

	@Column(name="thesis_file")
	private String thesisFile;

	@Column(name="thesis_name")
	private String thesisName;

	@Column(name="thesis_periodical")
	private String thesisPeriodical;

	@Column(name="thesis_record")
	private String thesisRecord;

	@Column(name="thesis_type")
	private String thesisType;

	public ThesisInfo() {
	}

	public String getThesisId() {
		return this.thesisId;
	}

	public void setThesisId(String thesisId) {
		this.thesisId = thesisId;
	}

	public String getThesisAbstract() {
		return this.thesisAbstract;
	}

	public void setThesisAbstract(String thesisAbstract) {
		this.thesisAbstract = thesisAbstract;
	}

	public String getThesisAuthor() {
		return this.thesisAuthor;
	}

	public void setThesisAuthor(String thesisAuthor) {
		this.thesisAuthor = thesisAuthor;
	}

	public Date getThesisDate() {
		return this.thesisDate;
	}

	public void setThesisDate(Date thesisDate) {
		this.thesisDate = thesisDate;
	}

	public String getThesisFile() {
		return this.thesisFile;
	}

	public void setThesisFile(String thesisFile) {
		this.thesisFile = thesisFile;
	}

	public String getThesisName() {
		return this.thesisName;
	}

	public void setThesisName(String thesisName) {
		this.thesisName = thesisName;
	}

	public String getThesisPeriodical() {
		return this.thesisPeriodical;
	}

	public void setThesisPeriodical(String thesisPeriodical) {
		this.thesisPeriodical = thesisPeriodical;
	}

	public String getThesisRecord() {
		return this.thesisRecord;
	}

	public void setThesisRecord(String thesisRecord) {
		this.thesisRecord = thesisRecord;
	}

	public String getThesisType() {
		return this.thesisType;
	}

	public void setThesisType(String thesisType) {
		this.thesisType = thesisType;
	}

}