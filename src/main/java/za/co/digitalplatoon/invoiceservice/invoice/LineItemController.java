package za.co.digitalplatoon.invoiceservice.invoice;


import java.net.URI;
import java.util.List;
import java.util.Optional;
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
public class LineItemController {
	
	
	
	@Autowired
	private LineItemRepository lineItemRepository;
	

	
	
	//View all Invoices
	@GetMapping("/lineitems")
	public List<LineItem> viewAllLineItems(){
		
		return lineItemRepository.findAll();
	}
	
	
	//view one/specific invoice
	@GetMapping("/lineitems/{id}")
	public Resource<LineItem> viewLineItem(@PathVariable Long id) {
		Optional<LineItem> lineitem = lineItemRepository.findById(id);
		
		if(!lineitem.isPresent())
			throw new NotFoundExceptionHandler("id-"+ id);
		
		Resource<LineItem> resource = new Resource<LineItem>(lineitem.get());
		
		return resource;
	}
	
    //add Invoice
	@PostMapping("/lineitems")
	public ResponseEntity<Object> addLineItem(@Valid @RequestBody LineItem lineItem) {
		
		LineItem lineTobeSaved = lineItemRepository.save(lineItem);
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(lineTobeSaved.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	

}
