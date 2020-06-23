package com.jdc.app.entity;

import java.util.List;

public class SaleDTO {

	private Sale sale;
	private List<SaleDeatil> details;

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public List<SaleDeatil> getDetails() {
		return details;
	}

	public void setDetails(List<SaleDeatil> details) {
		this.details = details;
	}

}
