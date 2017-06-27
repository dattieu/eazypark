package prototype.model;

import java.time.LocalTime;

import javax.persistence.Embeddable;

// REVIEW to review, not so much needed?
@Embeddable
public class WorkingTime {
	
	private LocalTime fromTime;
	private LocalTime toTime;
	
	public WorkingTime() {}

	public WorkingTime(LocalTime fromTime, LocalTime toTime) {
		super();
		this.fromTime = fromTime;
		this.toTime = toTime;
	}

	public LocalTime getFromTime() {
		return fromTime;
	}

	public void setFromTime(LocalTime fromTime) {
		this.fromTime = fromTime;
	}

	public LocalTime getToTime() {
		return toTime;
	}

	public void setToTime(LocalTime toTime) {
		this.toTime = toTime;
	}
	
}
