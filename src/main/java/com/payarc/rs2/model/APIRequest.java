package com.payarc.rs2.model;

import org.springframework.stereotype.Component;

/**
 * @author Krishna Chaithanya
 *
 */
@Component
public class APIRequest {

	private String txn_type;
	
	private String amount;
	
	private String card_number;
	
	private String card_source;
	
	private String exp_month;
	
	private String exp_year;
	
	private String cvv;
	
	private String card_holder_name;
	
	private String address;

	private String merchant_name;

	private String merchant_city;

	private String merchant_state;

	private String merchant_country;

	private String currency_code;

	public String getAddress() {
		return address;
	}

	public String getAmount() {
		return amount;
	}

	public String getCard_holder_name() {
		return card_holder_name;
	}

	public String getCard_number() {
		return card_number;
	}

	public String getCard_source() {
		return card_source;
	}

	public String getCurrency_code() {
		return currency_code;
	}

	public String getCvv() {
		return cvv;
	}

	public String getExp_month() {
		return exp_month;
	}

	public String getExp_year() {
		return exp_year;
	}

	public String getMerchant_city() {
		return merchant_city;
	}

	public String getMerchant_country() {
		return merchant_country;
	}

	public String getMerchant_name() {
		return merchant_name;
	}

	public String getMerchant_state() {
		return merchant_state;
	}

	public String getTxn_type() {
		return txn_type;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public void setCard_holder_name(String card_holder_name) {
		this.card_holder_name = card_holder_name;
	}

	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}

	public void setCard_source(String card_source) {
		this.card_source = card_source;
	}

	public void setCurrency_code(String currency_code) {
		this.currency_code = currency_code;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public void setExp_month(String exp_month) {
		this.exp_month = exp_month;
	}

	public void setExp_year(String exp_year) {
		this.exp_year = exp_year;
	}

	public void setMerchant_city(String merchant_city) {
		this.merchant_city = merchant_city;
	}

	public void setMerchant_country(String merchant_country) {
		this.merchant_country = merchant_country;
	}

	public void setMerchant_name(String merchant_name) {
		this.merchant_name = merchant_name;
	}

	public void setMerchant_state(String merchant_state) {
		this.merchant_state = merchant_state;
	}

	public void setTxn_type(String txn_type) {
		this.txn_type = txn_type;
	}

	@Override
	public String toString() {
		return "APIRequest [txn_type=" + txn_type + ", amount=" + amount + ", card_number=" + card_number
				+ ", card_source=" + card_source + ", exp_month=" + exp_month + ", exp_year=" + exp_year + ", cvv="
				+ cvv + ", card_holder_name=" + card_holder_name + ", address=" + address + ", merchant_name="
				+ merchant_name + ", merchant_city=" + merchant_city + ", merchant_state=" + merchant_state
				+ ", merchant_country=" + merchant_country + ", currency_code=" + currency_code + "]";
	}
}
