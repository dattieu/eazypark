package prototype.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "review")
public class Review extends PropertyEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "park_id")
	private int parkId;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@Column(name = "date")
	private Date date;
	
	@Column(name = "rate")
	private int rate;
		
	@Column(name = "description")
	private String description;

	public Review(){}

	public Review(int rate, String description) {
		super();
		this.date = new Date();
		this.rate = rate;
		this.description = description;
	}

	public int getParkId() {
		return parkId;
	}

	public void setParkId(int parkId) {
		this.parkId = parkId;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}