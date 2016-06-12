package srmt.java.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;


/**
 * The persistent class for the project_info database table.
 * 
 */
@Entity
@Table(name="project_info")
@NamedQuery(name="ProjectInfo.findAll", query="SELECT p FROM ProjectInfo p")
public class ProjectInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "generator", strategy = "uuid")
	@GeneratedValue(generator = "generator")
	@Column(name="project_id")
	private String projectId;

	@Temporal(TemporalType.DATE)
	@Column(name="end_time")
	private Date endTime;

	@Column(name="proect_file")
	private String proectFile;

	@Column(name="project_content")
	private String projectContent;

	@Column(name="project_first")
	private String projectFirst;

	@Column(name="project_fund")
	private float projectFund;

	@Column(name="project_name")
	private String projectName;

	@Column(name="project_second")
	private String projectSecond;

	@Column(name="project_source")
	private String projectSource;

	@Column(name="project_third")
	private String projectThird;

	@Column(name="project_type")
	private String projectType;
	
	@Column(name="project_pass")
	private String projectPass;

	@Temporal(TemporalType.DATE)
	@Column(name="start_time")
	private Date startTime;

	public ProjectInfo() {
	}

	public String getProjectPass() {
		return projectPass;
	}

	public void setProjectPass(String projectPass) {
		this.projectPass = projectPass;
	}

	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getProectFile() {
		return this.proectFile;
	}

	public void setProectFile(String proectFile) {
		this.proectFile = proectFile;
	}

	public String getProjectContent() {
		return this.projectContent;
	}

	public void setProjectContent(String projectContent) {
		this.projectContent = projectContent;
	}

	public String getProjectFirst() {
		return this.projectFirst;
	}

	public void setProjectFirst(String projectFirst) {
		this.projectFirst = projectFirst;
	}

	public float getProjectFund() {
		return this.projectFund;
	}

	public void setProjectFund(float projectFund) {
		this.projectFund = projectFund;
	}

	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectSecond() {
		return this.projectSecond;
	}

	public void setProjectSecond(String projectSecond) {
		this.projectSecond = projectSecond;
	}

	public String getProjectSource() {
		return this.projectSource;
	}

	public void setProjectSource(String projectSource) {
		this.projectSource = projectSource;
	}

	public String getProjectThird() {
		return this.projectThird;
	}

	public void setProjectThird(String projectThird) {
		this.projectThird = projectThird;
	}

	public String getProjectType() {
		return this.projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

}