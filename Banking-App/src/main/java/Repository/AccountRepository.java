package Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.Banking_App.Account;

public interface AccountRepository extends  JpaRepository<Account, Long>{

}
