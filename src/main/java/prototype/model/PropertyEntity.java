package prototype.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.validator.constraints.Email;

@MappedSuperclass
public class PropertyEntity extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Email
	@Column(name = "owner", nullable = false, unique = true)
	private String owner;

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
	
}
