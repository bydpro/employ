package srmt.java.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;


/**
 * The persistent class for the reward_info database table.
 * 
 */
@Entity
@Table(name="reward_info")
@NamedQuery(name="RewardInfo.findAll", query="SELECT r FROM RewardInfo r")
public class RewardInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "generator", strategy = "uuid")
	@GeneratedValue(generator = "generator")
	@Column(name="reward_id")
	private String rewardId;

	@Column(name="reward_level")
	private String rewardLevel;

	@Column(name="reward_name")
	private String rewardName;

	@Column(name="reward_organ")
	private String rewardOrgan;

	@Column(name="reward_place")
	private String rewardPlace;
	
	@Column(name="reward_content")
	private String rewardContent;
	
	@Column(name="reward_file")
	private String rewardFile;
	
	@Column(name="reward_pass")
	private String rewardPass;
	
	@Temporal(TemporalType.DATE)
	@Column(name="reward_time")
	private Date rewardTime;

	@Column(name="reward_type")
	private String rewardType;

	@Column(name="reward_user")
	private String rewardUser;

	public RewardInfo() {
	}

	
	public String getRewardPass() {
		return rewardPass;
	}


	public void setRewardPass(String rewardPass) {
		this.rewardPass = rewardPass;
	}


	public String getRewardContent() {
		return rewardContent;
	}

	public void setRewardContent(String rewardContent) {
		this.rewardContent = rewardContent;
	}

	public String getRewardFile() {
		return rewardFile;
	}

	public void setRewardFile(String rewardFile) {
		this.rewardFile = rewardFile;
	}

	public String getRewardId() {
		return this.rewardId;
	}

	public void setRewardId(String rewardId) {
		this.rewardId = rewardId;
	}

	public String getRewardLevel() {
		return this.rewardLevel;
	}

	public void setRewardLevel(String rewardLevel) {
		this.rewardLevel = rewardLevel;
	}

	public String getRewardName() {
		return this.rewardName;
	}

	public void setRewardName(String rewardName) {
		this.rewardName = rewardName;
	}

	public String getRewardOrgan() {
		return this.rewardOrgan;
	}

	public void setRewardOrgan(String rewardOrgan) {
		this.rewardOrgan = rewardOrgan;
	}

	public String getRewardPlace() {
		return this.rewardPlace;
	}

	public void setRewardPlace(String rewardPlace) {
		this.rewardPlace = rewardPlace;
	}

	public Date getRewardTime() {
		return this.rewardTime;
	}

	public void setRewardTime(Date rewardTime) {
		this.rewardTime = rewardTime;
	}

	public String getRewardType() {
		return this.rewardType;
	}

	public void setRewardType(String rewardType) {
		this.rewardType = rewardType;
	}

	public String getRewardUser() {
		return this.rewardUser;
	}

	public void setRewardUser(String rewardUser) {
		this.rewardUser = rewardUser;
	}

}