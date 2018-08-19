package za.co.digitalplatoon.invoiceservice.invoice;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import io.swagger.annotations.ApiModel;

@ApiModel(description="Invoice Description")
@Entity
public class Invoice {
	
	@Id
	@GeneratedValue
	private Long id;
	private String client;
	private Long vatRate;
	private LocalDateTime invoiceDate ;
	
	@OneToMany(mappedBy="invoice")
	private List<LineItem> lineItems;
	

	
	
	public Invoice() {}
	
	
	public Invoice(Long id, String client, Long vatRate, LocalDateTime invoiceDate) {
		super();
		this.id = id;
		this.client = client;
		this.vatRate = vatRate;
		this.invoiceDate = invoiceDate;
	}
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public Long getVatRate() {
		return vatRate;
	}
	public void setVatRate(Long vatRate) {
		this.vatRate = vatRate;
	}
	public LocalDateTime getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(LocalDateTime invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public List<LineItem> getLineItems() {
		return lineItems;
	}

	public void setLineItems(List<LineItem> lineItems) {
		this.lineItems = lineItems;
	}

	
     //subTotal
	public BigDecimal getSubTotal() {
		BigDecimal subTotal= BigDecimal.ZERO;
		for (Iterator<LineItem> i = lineItems.iterator(); i.hasNext();) {
			LineItem item = i.next();
			subTotal=item.getLineItemTotal();
		}
		return subTotal;
	}
	
	//getVAT
    public BigDecimal getVat() {
    	BigDecimal convertedVat = new BigDecimal(vatRate);
    	BigDecimal vat= convertedVat.divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP);
		return vat;
	}
    
    
    
   //return Total
   public BigDecimal getTotal() {
	BigDecimal Total= BigDecimal.ZERO;
	BigDecimal VatCalc= BigDecimal.ZERO;

	VatCalc=getSubTotal().multiply(getVat());
	Total= VatCalc.add(getSubTotal());
	   
		
 	return Total;
	}
    
    
    

	
}
