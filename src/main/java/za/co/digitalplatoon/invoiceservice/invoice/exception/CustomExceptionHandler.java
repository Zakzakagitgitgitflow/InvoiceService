package za.co.digitalplatoon.invoiceservice.invoice.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



@RestController
public class CustomExceptionHandler extends
ResponseEntityExceptionHandler{
	

	
	//Handling all exceptions
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request){
		
	ExceptionResponse exceptionResponse=new ExceptionResponse(LocalDateTime.now(), 
			ex.getMessage(), 
			request.getDescription(false));	
	
	 return new  ResponseEntity(exceptionResponse,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	
	//Handling not found exception
	@ExceptionHandler(NotFoundExceptionHandler.class)
	public final ResponseEntity<Object> handleUserNotFoundExceptions(NotFoundExceptionHandler ex, WebRequest request){
		
	ExceptionResponse exceptionResponse=new ExceptionResponse(LocalDateTime.now(), 
			ex.getMessage(), 
			request.getDescription(false));	
	
	 return new  ResponseEntity(exceptionResponse,HttpStatus.NOT_FOUND);
		
	}
	
	//Handling bad request exception
		@ExceptionHandler(BadRequestException.class)
		public final ResponseEntity<Object> handleBadRequestFoundExceptions(BadRequestException ex, WebRequest request){
			
		ExceptionResponse exceptionResponse=new ExceptionResponse(LocalDateTime.now(), 
				ex.getMessage(), 
				request.getDescription(true));	
		
		 return new  ResponseEntity(exceptionResponse,HttpStatus.BAD_REQUEST);
			
		}


}
