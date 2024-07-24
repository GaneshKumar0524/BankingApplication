package Mapper;

import com.project.Banking_App.Account;

import Dto.AccountDto;

public class AccountMapper {
	
	public static Account mapToAccount (AccountDto AccountDto) {
		
		
		Account account = new Account(
				AccountDto.getId(),
				AccountDto.getHolderName(),
				AccountDto.getBalance());
		return account;
		
	}
	
public static AccountDto mapToAccountDto (Account account) {
		
		
	AccountDto accountdto = new AccountDto(
			
			account.getId(),
			account.getHolderName(),
			account.getBalance()
				);
		return accountdto;		
	}

}
