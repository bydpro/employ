package srmt.java.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * The persistent class for the gas_record database table.
 * 
 */
@Entity
@Table(name="gas_record")
@NamedQuery(name="GasRecord.findAll", query="SELECT g FROM GasRecord g")
public class GasRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "generator", strategy = "uuid")
	@GeneratedValue(generator = "generator")
	@Column(name="gas_id")
	private String gasId;

	private String creater;

	@Column(name="creater_time")
	private Timestamp createrTime;

	@Column(name="gas_price")
	private double gasPrice;

	@Column(name="gas_time")
	private Timestamp gasTime;

	@Column(name="gas_type")
	private String gasType;

	@Column(name="gas_user_num")
	private Long gasUserNum;

	@Column(name="gas_volume")
	private double gasVolume;

	public GasRecord() {
	}

	public String getGasId() {
		return this.gasId;
	}

	public void setGasId(String gasId) {
		this.gasId = gasId;
	}

	public String getCreater() {
		return this.creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Timestamp getCreaterTime() {
		return this.createrTime;
	}

	public void setCreaterTime(Timestamp createrTime) {
		this.createrTime = createrTime;
	}

	public double getGasPrice() {
		return this.gasPrice;
	}

	public void setGasPrice(double gasPrice) {
		this.gasPrice = gasPrice;
	}

	public Timestamp getGasTime() {
		return this.gasTime;
	}

	public void setGasTime(Timestamp gasTime) {
		this.gasTime = gasTime;
	}

	public String getGasType() {
		return this.gasType;
	}

	public void setGasType(String gasType) {
		this.gasType = gasType;
	}

	public Long getGasUserNum() {
		return this.gasUserNum;
	}

	public void setGasUserNum(Long gasUserNum) {
		this.gasUserNum = gasUserNum;
	}

	public double getGasVolume() {
		return this.gasVolume;
	}

	public void setGasVolume(double gasVolume) {
		this.gasVolume = gasVolume;
	}

}