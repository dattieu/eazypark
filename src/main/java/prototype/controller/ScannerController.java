package prototype.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import prototype.constant.Constant;
import prototype.dto.VehicleCodeDto;
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
	// TODO need testing
	@PostMapping(SCANNER)
	public void scanVehicleCode(@RequestBody @Valid VehicleCodeDto vehicleCodeDto, BindingResult result) {
		if(result.hasErrors()) {
			throw new IllegalArgumentException(Constant.INVALID_VEHICLE_CODE);
		}
		scannerService.scan(vehicleCodeDto);
	}
	
}
