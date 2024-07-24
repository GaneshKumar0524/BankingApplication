package Service;

import java.util.List;

import com.project.Banking_App.Account;

import Dto.AccountDto;

public interface AccountService {
	
	 AccountDto createAccount (AccountDto account);

	 AccountDto GetaccountById (Long id);
	 
	 AccountDto Deposit(long id , double amount);	
	 
	 AccountDto withdraw(long id , double amount);	
	 
	  List<AccountDto> getAllAcounts();
	 
	  void deleteAccount(Long id);
}
