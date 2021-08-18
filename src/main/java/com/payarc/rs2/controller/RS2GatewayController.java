package com.payarc.rs2.controller;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payarc.rs2.model.APIRequest;
import com.payarc.rs2.model.APIResponse;
import com.payarc.rs2.model.FieldErrorMessage;
import com.payarc.rs2.service.RS2MessageService;


@RefreshScope
@RestController
@RequestMapping("${com.rs2.rootURI}")
public class RS2GatewayController {
	
	@Autowired
	RS2MessageService service;
	
	@GetMapping("${com.rs2.enquire}")
	public ResponseEntity<String> getTxnResult(@PathVariable("rrn") String rrn){
		return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
	}

	@PostMapping("${com.rs2.createPurchase}")
	public ResponseEntity<APIResponse> createTransaction(@Valid @RequestBody APIRequest request)
	{
		System.out.println("APIRequest: "+request);
		APIResponse response = null;
		
		//Purchase transaction - MTI 0100
		if(request.getTxn_type().equalsIgnoreCase("sale")) {
			Instant start = Instant.now();
			byte[] isoMsg = service.createPurchaseISORequest(request);
			String rs2response = service.connectRS2(isoMsg);
			response = service.parseISOResponse(rs2response,"sale");
			Instant stop = Instant.now();
			long elapsed = Duration.between(start, stop).toMillis();
			System.out.println("Elapsed time in millis: "+ elapsed);
		}
		//Authorization/Pre-Authorization transaction - MTI 0100
		else if(request.getTxn_type().equalsIgnoreCase("auth")) {
			byte[] isoMsg = service.createPreAuthISORequest(request);
			String rs2response = service.connectRS2(isoMsg);
			response = service.parseISOResponse(rs2response,"auth");
		}
		//Capture/Completion transaction - MTI 0120
		else if(request.getTxn_type().equalsIgnoreCase("capture")) {
			byte[] isoMsg = service.createCaptureISORequest(request);
			String rs2response = service.connectRS2(isoMsg);
			response = service.parseISOResponse(rs2response,"capture");
		}
		
		return new ResponseEntity<APIResponse>(response,HttpStatus.OK);
	}
}
