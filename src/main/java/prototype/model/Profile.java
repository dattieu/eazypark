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
@Table(name = "profile")
public class Profile extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@Column(name = "joining_date")
	private Date joiningDate;
	
	@Column(name = "photo")
	private String photo;
	
	public Profile(){}
	
	public Profile(String photo) {
		super();
		this.joiningDate = new Date();
		this.photo = photo;
	}

	public Date getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

}
