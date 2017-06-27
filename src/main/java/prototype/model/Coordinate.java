package prototype.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Coordinate {

	@Column(name = "latitude")
	private double latitude;
	
	@Column(name = "longitude")
	private double longitude;

	public Coordinate(){};
	
	public Coordinate(double latitude, double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
}
