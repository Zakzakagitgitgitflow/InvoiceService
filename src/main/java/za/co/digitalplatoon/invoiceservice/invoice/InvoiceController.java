package za.co.digitalplatoon.invoiceservice.invoice;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import za.co.digitalplatoon.invoiceservice.invoice.exception.NotFoundExceptionHandler;






@RestController
public class InvoiceController {
	
	
	
	@Autowired
	private InvoiceRepository invoiceRepository;
	

	
	
	//View all Invoices
	@GetMapping("/invoices")
	public List<Invoice> viewAllInvoices(){
		
		return invoiceRepository.findAll();
	}
	
	
	//view one/specific invoice
	@GetMapping("/invoices/{id}")
	public Resource<Invoice> viewInvoice(@PathVariable Long id) {
		Optional<Invoice> invoice = invoiceRepository.findById(id);
		
		if(!invoice.isPresent())
			throw new NotFoundExceptionHandler("id-"+ id);
		
		Resource<Invoice> resource = new Resource<Invoice>(invoice.get());
		
		return resource;
	}
	
	
    //add Invoice
	@PostMapping("/invoices")
	public ResponseEntity<Object> addInvoice(@Valid @RequestBody Invoice invoice) {
		
		invoice.setInvoiceDate(LocalDateTime.now());
		Invoice invoiceTobeSaved = invoiceRepository.save(invoice);
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(invoiceTobeSaved.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	
	

}
