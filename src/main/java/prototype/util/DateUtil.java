package prototype.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtil {

	public static final String getCurrentTimestamp(String format){
		return new SimpleDateFormat(format).format(new Date());
	}
	
}
