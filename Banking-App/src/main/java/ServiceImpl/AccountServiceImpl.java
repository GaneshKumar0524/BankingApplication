package ServiceImpl;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.project.Banking_App.Account;

import Dto.AccountDto;
import Mapper.AccountMapper;
import Repository.AccountRepository;
import Service.AccountService;

public class AccountServiceImpl implements AccountService{

	AccountRepository accountrepository;

	
	public AccountServiceImpl(AccountRepository accountrepository) {
		super();
		this.accountrepository = accountrepository;
	}
	
	@Override
	public AccountDto createAccount(AccountDto accountdto) {

		Account account = AccountMapper.mapToAccount(accountdto);

		Account savedAccount = accountrepository.save(account);
		
		return AccountMapper.mapToAccountDto(savedAccount);
	}

	@Override
	public AccountDto GetaccountById(Long id) {
		
		Account account =  accountrepository
				.findById(id)
				.orElseThrow(()-> new RuntimeException("The user does not Exist"));
		
		return AccountMapper.mapToAccountDto(account);
	}

	@Override
	public AccountDto Deposit(long id, double amount) {
		
		Account account =  accountrepository
				.findById(id)
				.orElseThrow(()-> new RuntimeException("The user does not Exist"));
		
		double total = amount + account.getBalance();
		account.setBalance(total);
		Account SavedAccount  = accountrepository.save(account);
		return AccountMapper.mapToAccountDto(SavedAccount);

	}

	@Override
	public AccountDto withdraw(long id, double amount) {
		Account account =  accountrepository
				.findById(id)
				.orElseThrow(()-> new RuntimeException("The user does not Exist"));
		
		if(account.getBalance()< amount) {
			
			throw new RuntimeException("Insufficient Balance");		
		}
		double total = amount - account.getBalance();
		account.setBalance(total);
		Account SavedAccount  = accountrepository.save(account);
		return AccountMapper.mapToAccountDto(SavedAccount);
	}

	@Override
	public List<AccountDto> getAllAcounts() {
		
		List<Account> accounts = accountrepository.findAll();
		
		return accounts
				.stream()
				.map((account)->AccountMapper.mapToAccountDto(account))
				.collect(Collectors.toList());
	}

	@Override
	public void deleteAccount(Long id) {
		
		Account account =  accountrepository
				.findById(id)
				.orElseThrow(()-> new RuntimeException("The user does not Exist"));
		
		return;
		
	}
}
