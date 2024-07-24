package Controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Dto.AccountDto;
import Service.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
	
	AccountService accountservice;
	
	//Add account restApi
	@PostMapping
	public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountdto)
	{
		return new ResponseEntity<>(accountservice.createAccount(accountdto),HttpStatus.CREATED);
		
	}
	//Get Accountdetails RestApi
	@GetMapping("/{id}")
	public    ResponseEntity<AccountDto> getAccountDetails( @PathVariable Long id){
		
		AccountDto Accountdetails = accountservice.GetaccountById(id);	
		return ResponseEntity.ok(Accountdetails);
		
	}
	
	//Deposit RestApi
	@PutMapping("/{id}/deposit")
	public ResponseEntity<AccountDto>  Deposit( @PathVariable Long id , @RequestBody Map<String , Double> request)
	{
		double amount = request.get("amount");
		AccountDto accountdto = accountservice.Deposit(id,amount );
		return ResponseEntity.ok(accountdto);
		
	}
	//withdraw RestApi
	@PutMapping("/{id}/withdraw")
	public ResponseEntity<AccountDto>  withdraw( @PathVariable Long id , @RequestBody Map<String , Double> request)
	{
		double amount = request.get("amount");
		
		AccountDto accountdto = accountservice.Deposit(id,amount );
		return ResponseEntity.ok(accountdto);		
	}
	
	//To getAll users Api
	public ResponseEntity< List<AccountDto>> getAllusers()
	{
		List<AccountDto> accounts = accountservice.getAllAcounts();
		return ResponseEntity.ok(accounts);
		
	}
	
	//Delete user usingId RestApi
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteAccountDetails( @PathVariable Long id){
		
		 accountservice.deleteAccount(id);	
		return  ResponseEntity.ok("Account is deleted successfully !!!");
		
	}
	
}
