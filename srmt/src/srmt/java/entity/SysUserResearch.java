package srmt.java.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;


/**
 * The persistent class for the sys_user_research database table.
 * 
 */
@Entity
@Table(name="sys_user_research")
@NamedQuery(name="SysUserResearch.findAll", query="SELECT s FROM SysUserResearch s")
public class SysUserResearch implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "generator", strategy = "uuid")
	@GeneratedValue(generator = "generator")
	private String rid;

	private String creater;

	@Column(name="creater_date")
	private Timestamp createrDate;

	@Column(name="research_id")
	private String researchId;

	@Column(name="research_type")
	private String researchType;

	@Column(name="research_user_id")
	private String researchUserId;

	public SysUserResearch() {
	}

	public String getRid() {
		return this.rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getCreater() {
		return this.creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Timestamp getCreaterDate() {
		return this.createrDate;
	}

	public void setCreaterDate(Timestamp createrDate) {
		this.createrDate = createrDate;
	}

	public String getResearchId() {
		return this.researchId;
	}

	public void setResearchId(String researchId) {
		this.researchId = researchId;
	}

	public String getResearchType() {
		return this.researchType;
	}

	public void setResearchType(String researchType) {
		this.researchType = researchType;
	}

	public String getResearchUserId() {
		return this.researchUserId;
	}

	public void setResearchUserId(String researchUserId) {
		this.researchUserId = researchUserId;
	}

}