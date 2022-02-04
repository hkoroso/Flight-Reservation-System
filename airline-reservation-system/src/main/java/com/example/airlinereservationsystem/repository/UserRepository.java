package com.example.airlinereservationsystem.repository;

import com.example.airlinereservationsystem.domain.User;
import com.example.airlinereservationsystem.dto.UserLoginDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
/**
 * @author Abdi Wako Jilo
 */

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT u from User u where  u.email = :email  and u.password = :password")
    public List<User> login(@Param("email") String email,
                            @Param("password") String password);

    Optional<User> findByUsername(String username);
    User findUserByEmail(String email);
    Optional<User> findByID(long id);

}

//import java.util.Optional;

//@Repository
//public interface UserRepository extends CrudRepository<User, Integer> {
//    Optional<User> findByUsername(String username);
//}
