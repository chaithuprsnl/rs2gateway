package com.payarc.rs2.model;

import org.springframework.stereotype.Component;

/**
 * @author Krishna Chaithanya
 *
 */
@Component
public class APIResponse {

	private String txnid;
	private String amount;
	private String amount_refunded;
	private String amount_captured;
	private String amount_voided;
	private String type;
	private String captured;
	private String is_refunded;
	private String status;
	private String auth_code;
	private String failure_code;
	private String failure_message;

	public String getAmount() {
		return amount;
	}

	public String getAmount_captured() {
		return amount_captured;
	}

	public String getAmount_refunded() {
		return amount_refunded;
	}

	public String getAmount_voided() {
		return amount_voided;
	}

	public String getAuth_code() {
		return auth_code;
	}

	public String getCaptured() {
		return captured;
	}

	public String getFailure_code() {
		return failure_code;
	}

	public String getFailure_message() {
		return failure_message;
	}

	public String getIs_refunded() {
		return is_refunded;
	}

	public String getStatus() {
		return status;
	}

	public String getTxnid() {
		return txnid;
	}

	public String getType() {
		return type;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public void setAmount_captured(String amount_captured) {
		this.amount_captured = amount_captured;
	}

	public void setAmount_refunded(String amount_refunded) {
		this.amount_refunded = amount_refunded;
	}

	public void setAmount_voided(String amount_voided) {
		this.amount_voided = amount_voided;
	}

	public void setAuth_code(String auth_code) {
		this.auth_code = auth_code;
	}

	public void setCaptured(String captured) {
		this.captured = captured;
	}

	public void setFailure_code(String failure_code) {
		this.failure_code = failure_code;
	}

	public void setFailure_message(String failure_message) {
		this.failure_message = failure_message;
	}

	public void setIs_refunded(String is_refunded) {
		this.is_refunded = is_refunded;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setTxnid(String txnid) {
		this.txnid = txnid;
	}

	public void setType(String type) {
		this.type = type;
	}

}
