package srmt.java.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;


/**
 * The persistent class for the oil_storage database table.
 * 
 */
@Entity
@Table(name="oil_storage")
@NamedQuery(name="OilStorage.findAll", query="SELECT o FROM OilStorage o")
public class OilStorage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "generator", strategy = "uuid")
	@GeneratedValue(generator = "generator")
	@Column(name="oil_storage_id")
	private String oilStorageId;

	@Temporal(TemporalType.DATE)
	@Column(name="cerater_date")
	private Date ceraterDate;

	private String creater;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="oil_receive_time")
	private Date oilReceiveTime;

	@Column(name="oil_tank_id")
	private String oilTankId;

	@Column(name="oil_type")
	private String oilType;

	@Column(name="olil_num")
	private Integer olilNum;

	public OilStorage() {
	}

	public String getOilStorageId() {
		return this.oilStorageId;
	}

	public void setOilStorageId(String oilStorageId) {
		this.oilStorageId = oilStorageId;
	}

	public Date getCeraterDate() {
		return this.ceraterDate;
	}

	public void setCeraterDate(Date ceraterDate) {
		this.ceraterDate = ceraterDate;
	}

	public String getCreater() {
		return this.creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Date getOilReceiveTime() {
		return this.oilReceiveTime;
	}

	public void setOilReceiveTime(Date oilReceiveTime) {
		this.oilReceiveTime = oilReceiveTime;
	}

	public String getOilTankId() {
		return this.oilTankId;
	}

	public void setOilTankId(String oilTankId) {
		this.oilTankId = oilTankId;
	}

	public String getOilType() {
		return this.oilType;
	}

	public void setOilType(String oilType) {
		this.oilType = oilType;
	}

	public Integer getOlilNum() {
		return this.olilNum;
	}

	public void setOlilNum(Integer olilNum) {
		this.olilNum = olilNum;
	}

}