package prototype.util;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashSet;

import org.hashids.Hashids;

import prototype.model.Coordinate;
import prototype.model.Park;
import prototype.model.Payment;
import prototype.model.Profile;
import prototype.model.Role;
import prototype.model.User;
import prototype.model.Vehicle;

import com.fasterxml.jackson.core.JsonProcessingException;

public class Testing {
	
	public static void main(String[] args) {
		
////		// Test Password Encoder
//		String pwd = "dattieu123";
//		String pwd2 = "admin123";
//		String hash = "$2a$10$lv0w60s/9/3wxq2d1GhOtedsmoi/1CXoQAhNYD2Nym9/2zLurgxYi";
//		String hashPwd = PasswordEncodingGenerator.encode(pwd);
//		System.out.println(hashPwd);
//		System.out.println(PasswordEncodingGenerator.match(pwd, hash));
////		
////		// Test Haversine
////		System.out.println(Haversine.getDistance(36.12, -86.67, 33.94, -118.40));
//		
//		// Test Model objects
//		User user = new User();
//		Profile profile = new Profile("user.png");
		Payment payment = new Payment("prepaid", new BigDecimal(100));
//		Vehicle vehicle = new Vehicle();
//		vehicle.setPayment(payment);
//		Park park = new Park();
//		park.setCapacity(0);
//		BigDecimal fee = new BigDecimal(5);
//		Coordinate coords = new Coordinate(1.0, 2.0);
//		
//		try {
//			System.out.println(JsonMapper.convertObjectToJson(payment));
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		Path src = Paths.get("E:/workspace", "dat.txt");
//		Path dest = Paths.get("E:/workspace/", "test2.txt");
//		try {
//			Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
//			System.out.println(src.toString());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		Hashids hashids = new Hashids("salt", 4);
		String hash = hashids.encode(1);
		long[] numbers = hashids.decode(hash);
		System.out.println(hash);
		
		System.out.println(numbers[0]);
	}
	
}
