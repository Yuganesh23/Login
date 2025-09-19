package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import entity.LoginEntity;
@Repository
public interface LoginRepository  extends JpaRepository<LoginEntity, Integer> {

    LoginEntity findByEmail(String email);

}