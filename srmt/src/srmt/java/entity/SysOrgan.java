package srmt.java.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;


/**
 * The persistent class for the sys_organ database table.
 * 
 */
@Entity
@Table(name="sys_organ")
@NamedQuery(name="SysOrgan.findAll", query="SELECT s FROM SysOrgan s")
public class SysOrgan implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "generator", strategy = "uuid")
	@GeneratedValue(generator = "generator")
	@Column(name="organ_id")
	private String organId;

	@Column(name="is_valid")
	private String isValid;

	@Column(name="organ_address")
	private String organAddress;

	@Column(name="organ_code")
	private String organCode;

	@Column(name="organ_name")
	private String organName;

	@Column(name="organ_type")
	private String organType;

	private String parent;

	public SysOrgan() {
	}

	public String getOrganId() {
		return this.organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

	public String getIsValid() {
		return this.isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public String getOrganAddress() {
		return this.organAddress;
	}

	public void setOrganAddress(String organAddress) {
		this.organAddress = organAddress;
	}

	public String getOrganCode() {
		return this.organCode;
	}

	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}

	public String getOrganName() {
		return this.organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public String getOrganType() {
		return this.organType;
	}

	public void setOrganType(String organType) {
		this.organType = organType;
	}

	public String getParent() {
		return this.parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

}