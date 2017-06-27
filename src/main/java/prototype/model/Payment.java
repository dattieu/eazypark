package prototype.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "payment")
public class Payment extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotEmpty
	@Column(name = "type", nullable = false)
	private String type;
	
	@Min(0)
	@Column(name = "amount")
	private BigDecimal amount;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	@Column(name = "payment_date")
	private Date paymentDate;

	public Payment(){}
	
	public Payment(String type, BigDecimal amount) {
		super();
		this.type = type;
		this.amount = amount;
		this.paymentDate = new Date();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public boolean isValid(){
		return amount.compareTo(new BigDecimal(0)) == 1;
	}
	
}
