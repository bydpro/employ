package srmt.java.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;


/**
 * The persistent class for the sys_dict database table.
 * 
 */
@Entity
@Table(name="sys_dict")
@NamedQuery(name="SysDict.findAll", query="SELECT s FROM SysDict s")
public class SysDict implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "generator", strategy = "uuid")
	@GeneratedValue(generator = "generator")
	@Column(name="dict_id")
	private String dictId;

	@Column(name="dict_name")
	private String dictName;

	@Column(name="dict_type")
	private String dictType;

	@Column(name="dict_value")
	private String dictValue;

	@Column(name="is_valid")
	private String isValid;

	private String seq;

	public SysDict() {
	}

	public String getDictId() {
		return this.dictId;
	}

	public void setDictId(String dictId) {
		this.dictId = dictId;
	}

	public String getDictName() {
		return this.dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public String getDictType() {
		return this.dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	public String getDictValue() {
		return this.dictValue;
	}

	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}

	public String getIsValid() {
		return this.isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public String getSeq() {
		return this.seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

}