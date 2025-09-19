package service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entity.LoginEntity;
import repository.LoginRepository;

@Service
public class LoginServiceImpl implements LoginService  {

	@Autowired
	LoginRepository loginRepo;
	
	@Override
	public LoginEntity save(LoginEntity le) {
		return loginRepo.save(le);
	}

	@Override
	public List<LoginEntity> getAll() {
		return loginRepo.findAll();
	}

	@Override
	public LoginEntity getById(Integer id) {
		Optional<LoginEntity> byId = loginRepo.findById(id);
		return byId.orElse(null);
	}

	@Override
	public LoginEntity update(Integer id, LoginEntity ule) {
       if(loginRepo.existsById(id)) {
    	   ule.setId(id);
    	   return loginRepo.save(ule);
       }
		
		return null;
	}

	@Override
	public void delete(Integer id) {
		loginRepo.deleteById(id);		
	}
	
	
	  @Override
	    public LoginEntity getByEmail(String email) {
	        return loginRepo.findByEmail(email); 
	    }
	
}
