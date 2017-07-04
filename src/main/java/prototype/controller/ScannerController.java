package prototype.controller;

import org.hashids.Hashids;
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
	
	// REVIEW use Spring Aspect here to add a id obfuscation layer?
	private final Hashids idObfuscator;
	
	@Autowired
	public ScannerController(ScannerService scannerService, Hashids idObfuscator) {
		this.scannerService = scannerService;
		this.idObfuscator = idObfuscator;
	}
	
	// REVIEW client's QR code (plate number) is scanned from the application device and sent to back end.
	// TODO need testing and some validation work here
	@GetMapping(SCANNER)
	public void scanVehicleCode(@RequestParam(value = "plateNumber", required = true) String plateNumber, 
								@PathVariable("parkId") String parkId) {
		// REVIEW should you DTO here? because it' not actually a query, so avoid using request parameters
		int deObfuscatedProfileId = (int) idObfuscator.decode(parkId)[0];
		scannerService.scan(plateNumber, deObfuscatedProfileId);
	}
	
}
