package prototype.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import prototype.service.ScannerService;

@RestController
public class ScannerController {

	private static final String SCANNER = "/checkins/{parkId}";
	
	private final ScannerService scannerService;
	
	@Autowired
	public ScannerController(ScannerService scannerService) {
		this.scannerService = scannerService;
	}
	
	// REVIEW client's QR code (plate number) is scanned from the application device and sent to back end.
	// TODO validation here
	@GetMapping(SCANNER)
	public void scanVehicleCode(@RequestParam(value = "plateNumber", required = true) String plateNumber, 
			@PathVariable("parkId") String parkId) {
		scannerService.scan(plateNumber, parkId);
	}
	
}
