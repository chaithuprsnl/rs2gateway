package com.payarc.rs2.service;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.payarc.rs2.model.APIRequest;
import com.payarc.rs2.model.APIResponse;
import com.payarc.rs2.model.RS2ISOMessage;

@Service
public class RS2MessageService {
	
	@Autowired
	RS2ISOMessage msgObj;
	
	public String connectRS2(byte[] isoMsg){
		
		int port = 8376;
		String hostIP = "", response = "";
		
		try {
			hostIP = InetAddress.getLocalHost().getHostAddress();
		}catch(UnknownHostException uhe) {
			uhe.printStackTrace();
		}
		
		String res = "";
		
		try(
				Socket socket = new Socket(hostIP, port);
				//PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				DataOutputStream out = new DataOutputStream(socket.getOutputStream());
				//BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				DataInputStream in = new DataInputStream(socket.getInputStream());
			){
			
			//Sending data to server
			//String clientRequest = "Sample Client Message2"; 
			out.write(isoMsg);
			System.out.println("Request sent to server: "+ Arrays.toString(isoMsg));
			
			//Receiving reply from server
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[4096];
			baos.write(buffer, 0, in.read(buffer));
			
			byte[] result = baos.toByteArray();
			//String res = Arrays.toString(result);
			res = new String(result, StandardCharsets.ISO_8859_1);
			
			System.out.println("Received from server: "+res);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	
	public byte[] createPurchaseISORequest(APIRequest request) {
		
		msgObj.setElement("MSG-TYP", "0100");
		
		//Set sample ISO Data Elements
		msgObj.setElement("F-2", request.getCard_number()); // Card No
		msgObj.setElement("F-3", "000000");//Processing Code - 00(Txn Type) - Purchase, 00(From Account Type) - Not Specified, 00(To Account Type) - Not Specified
		msgObj.setElement("F-4", IsoUtils.formatAmount(request.getAmount()));//Amount
		msgObj.setElement("F-7", IsoUtils.formatUTCDateTime()); // Transmission date and time in UTC MMddHHmmss format
		msgObj.setElement("F-11", IsoUtils.formatStan());//System Trace Audit Number
		msgObj.setElement("F-12", IsoUtils.formatLocalTime()); // Local Transaction Time hhmmss
		msgObj.setElement("F-13", IsoUtils.formatLocalDate()); // Local Transaction Date MMdd
		msgObj.setElement("F-14", request.getExp_year().substring(2)+request.getExp_month()); // Expiration Date YYMM
		msgObj.setElement("F-18", "5999"); // Merchant category code
		msgObj.setElement("F-22", "010");// Point of service entry mode 01 - Ecomm, 0 - Unspecified/Unknown
		
		if("internet".equalsIgnoreCase(request.getCard_source()) || "mail".equalsIgnoreCase(request.getCard_source()) || "phone".equalsIgnoreCase(request.getCard_source()))
			msgObj.setElement("F-25", "59");//Point of service condition code	59 - Ecomm/Card Not Present
		else
			msgObj.setElement("F-25", "00");//Point of service condition code	59 - POS/Card Present
		
		msgObj.setElement("F-32", "00000003"); // Acquiring institution identification code - Bankworks institution number
		msgObj.setElement("F-37", "078103259592");//RRN
		msgObj.setElement("F-41", "33719300"); // Card acceptor terminal identification TID
		msgObj.setElement("F-42", "BUNIT0033719300"); //Card acceptor identification code MID
		String merchantName = IsoUtils.padRight(request.getMerchant_name(),25);
		String merchantCity = IsoUtils.padRight(request.getMerchant_city(), 13);
		String merchantCountry = request.getMerchant_country();
		msgObj.setElement("F-43", merchantName + merchantCity + merchantCountry); //Card acceptor name/location
		msgObj.setElement("F-48", "Y");
		msgObj.setElement("S-48.40", "210");//PDS 48.40 Security Level Indicator for Ecomm
		msgObj.setElement("S-48.43", request.getCvv());//CVV
		msgObj.setElement("F-49", IsoUtils.formatISOCurrencyCode(request.getCurrency_code())); // Transaction currency code
		msgObj.setElement("F-60", "10009010");
		
		return msgObj.pack();
  		//System.out.println("isoMsg: "+ new String(isoMsg));
	}

	public APIResponse parseISOResponse(String rs2Response, String txnType) {
		APIResponse response = new APIResponse();
		
		Map<String, String> isoBuffer = msgObj.unpack(rs2Response);
		double amount = (double)Integer.parseInt(isoBuffer.get("F-4")) / 100; //Fraction digits are taken as 2 
		response.setAmount(String.valueOf(amount));
		response.setType(txnType);
		response.setTxnid("1128376");
		response.setAmount_captured(String.valueOf(amount));
		response.setAuth_code("266861");
		response.setCaptured("1");
		response.setStatus("submitted_for_settlement");

		return response;
	}
	
	public byte[] createPreAuthISORequest(APIRequest request) {
		
		msgObj.setElement("MSG-TYP", "0100");
		
		//Set sample ISO Data Elements
		msgObj.setElement("F-2", request.getCard_number()); // Card No
		msgObj.setElement("F-3", "000000");//Processing Code - 00(Txn Type) - Purchase, 00(From Account Type) - Not Specified, 00(To Account Type) - Not Specified
		msgObj.setElement("F-4", IsoUtils.formatAmount(request.getAmount()));//Amount
		msgObj.setElement("F-7", IsoUtils.formatUTCDateTime()); // Transmission date and time in UTC MMddHHmmss format
		msgObj.setElement("F-11", IsoUtils.formatStan());//System Trace Audit Number
		msgObj.setElement("F-12", IsoUtils.formatLocalTime()); // Local Transaction Time hhmmss
		msgObj.setElement("F-13", IsoUtils.formatLocalDate()); // Local Transaction Date MMdd
		msgObj.setElement("F-14", request.getExp_year().substring(2)+request.getExp_month()); // Expiration Date YYMM
		msgObj.setElement("F-18", "5999"); // Merchant category code
		msgObj.setElement("F-22", "010");// Point of service entry mode 01 - Ecomm, 0 - Unspecified/Unknown
		
		if("internet".equalsIgnoreCase(request.getCard_source()) || "mail".equalsIgnoreCase(request.getCard_source()) || "phone".equalsIgnoreCase(request.getCard_source()))
			msgObj.setElement("F-25", "59");//Point of service condition code	59 - Ecomm/Card Not Present
		else
			msgObj.setElement("F-25", "00");//Point of service condition code	59 - POS/Card Present
		
		msgObj.setElement("F-32", "00000003"); // Acquiring institution identification code - Bankworks institution number
		msgObj.setElement("F-37", "078103259592");//RRN
		msgObj.setElement("F-41", "33719300"); // Card acceptor terminal identification TID
		msgObj.setElement("F-42", "BUNIT0033719300"); //Card acceptor identification code MID
		msgObj.setElement("F-43",  "BUNIT MERCHANT           BUNIT CITY   GB"); //Card acceptor name/location
		msgObj.setElement("F-48", "Y");
		msgObj.setElement("S-48.05", "P");
		msgObj.setElement("S-48.40", "210");//PDS 48.40 Security Level Indicator for Ecomm
		msgObj.setElement("S-48.43", request.getCvv());//CVV
		msgObj.setElement("F-49", IsoUtils.formatISOCurrencyCode(request.getCurrency_code())); // Transaction currency code
		msgObj.setElement("F-60", "10009010");
		
		return msgObj.pack();
  		//System.out.println("isoMsg: "+ new String(isoMsg));

	}

	public byte[] createCaptureISORequest(APIRequest request) {
		
		msgObj.setElement("MSG-TYP", "0120");
		
		//Set sample ISO Data Elements
		msgObj.setElement("F-2", request.getCard_number()); // Card No
		msgObj.setElement("F-3", "000000");//Processing Code - 00(Txn Type) - Purchase, 00(From Account Type) - Not Specified, 00(To Account Type) - Not Specified
		msgObj.setElement("F-4", IsoUtils.formatAmount(request.getAmount()));//Amount
		msgObj.setElement("F-7", IsoUtils.formatUTCDateTime()); // Transmission date and time in UTC MMddHHmmss format
		msgObj.setElement("F-11", IsoUtils.formatStan());//System Trace Audit Number
		msgObj.setElement("F-12", IsoUtils.formatLocalTime()); // Local Transaction Time hhmmss
		msgObj.setElement("F-13", IsoUtils.formatLocalDate()); // Local Transaction Date MMdd
		msgObj.setElement("F-14", request.getExp_year().substring(2)+request.getExp_month()); // Expiration Date YYMM
		msgObj.setElement("F-18", "5999"); // Merchant category code
		msgObj.setElement("F-22", "010");// Point of service entry mode 01 - Ecomm, 0 - Unspecified/Unknown
		
		if("internet".equalsIgnoreCase(request.getCard_source()) || "mail".equalsIgnoreCase(request.getCard_source()) || "phone".equalsIgnoreCase(request.getCard_source()))
			msgObj.setElement("F-25", "59");//Point of service condition code	59 - Ecomm/Card Not Present
		else
			msgObj.setElement("F-25", "00");//Point of service condition code	59 - POS/Card Present
		
		msgObj.setElement("F-32", "00000003"); // Acquiring institution identification code - Bankworks institution number
		msgObj.setElement("F-37", "078103259592");//RRN
		msgObj.setElement("F-41", "33719300"); // Card acceptor terminal identification TID
		msgObj.setElement("F-42", "BUNIT0033719300"); //Card acceptor identification code MID
		msgObj.setElement("F-43",  "BUNIT MERCHANT           BUNIT CITY   GB"); //Card acceptor name/location
		msgObj.setElement("F-48", "Y");
		msgObj.setElement("S-48.05", "T");
		msgObj.setElement("S-48.40", "210");//PDS 48.40 Security Level Indicator for Ecomm
		msgObj.setElement("S-48.43", request.getCvv());//CVV
		msgObj.setElement("F-49", IsoUtils.formatISOCurrencyCode(request.getCurrency_code())); // Transaction currency code
		msgObj.setElement("F-60", "10009010");
		
		return msgObj.pack();
  		//System.out.println("isoMsg: "+ new String(isoMsg));

		
	}
	
	public void createRefundISOMessage() {
		
	}

}
