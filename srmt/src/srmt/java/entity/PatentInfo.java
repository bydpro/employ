package srmt.java.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;


/**
 * The persistent class for the patent_info database table.
 * 
 */
@Entity
@Table(name="patent_info")
@NamedQuery(name="PatentInfo.findAll", query="SELECT p FROM PatentInfo p")
public class PatentInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "generator", strategy = "uuid")
	@GeneratedValue(generator = "generator")
	@Column(name="patent_id")
	private String patentId;

	@Column(name="patent_content")
	private String patentContent;

	@Column(name="patent_creater")
	private String patentCreater;

	@Temporal(TemporalType.DATE)
	@Column(name="patent_date")
	private Date patentDate;

	@Column(name="patent_file")
	private String patentFile;

	@Column(name="patent_first")
	private String patentFirst;

	@Column(name="patent_is_transfer")
	private String patentIsTransfer;

	@Column(name="patent_name")
	private String patentName;

	@Column(name="patent_people")
	private String patentPeople;

	@Column(name="patent_type")
	private String patentType;

	public PatentInfo() {
	}

	public String getPatentId() {
		return this.patentId;
	}

	public void setPatentId(String patentId) {
		this.patentId = patentId;
	}

	public String getPatentContent() {
		return this.patentContent;
	}

	public void setPatentContent(String patentContent) {
		this.patentContent = patentContent;
	}

	public String getPatentCreater() {
		return this.patentCreater;
	}

	public void setPatentCreater(String patentCreater) {
		this.patentCreater = patentCreater;
	}

	public Date getPatentDate() {
		return this.patentDate;
	}

	public void setPatentDate(Date patentDate) {
		this.patentDate = patentDate;
	}

	public String getPatentFile() {
		return this.patentFile;
	}

	public void setPatentFile(String patentFile) {
		this.patentFile = patentFile;
	}

	public String getPatentFirst() {
		return this.patentFirst;
	}

	public void setPatentFirst(String patentFirst) {
		this.patentFirst = patentFirst;
	}

	public String getPatentIsTransfer() {
		return this.patentIsTransfer;
	}

	public void setPatentIsTransfer(String patentIsTransfer) {
		this.patentIsTransfer = patentIsTransfer;
	}

	public String getPatentName() {
		return this.patentName;
	}

	public void setPatentName(String patentName) {
		this.patentName = patentName;
	}

	public String getPatentPeople() {
		return this.patentPeople;
	}

	public void setPatentPeople(String patentPeople) {
		this.patentPeople = patentPeople;
	}

	public String getPatentType() {
		return this.patentType;
	}

	public void setPatentType(String patentType) {
		this.patentType = patentType;
	}

}