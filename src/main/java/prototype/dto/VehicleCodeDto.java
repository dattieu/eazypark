package prototype.dto;

import javax.validation.constraints.Min;
import org.hibernate.validator.constraints.NotBlank;

public class VehicleCodeDto {

	@NotBlank
	private String plateNumber;
	
	@Min(1)
	private int parkId;

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public int getParkId() {
		return parkId;
	}

	public void setParkId(int parkId) {
		this.parkId = parkId;
	}
	
}
