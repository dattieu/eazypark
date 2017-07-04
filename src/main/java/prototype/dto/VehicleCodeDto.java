package prototype.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class VehicleCodeDto {

	@NotNull
    @NotEmpty
    private String plateNumber;
	
	@NotNull
    @NotEmpty
    private String parkId;

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public String getParkId() {
		return parkId;
	}

	public void setParkId(String parkId) {
		this.parkId = parkId;
	}
	
}
