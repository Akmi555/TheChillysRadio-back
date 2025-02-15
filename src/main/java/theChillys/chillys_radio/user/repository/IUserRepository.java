package theChillys.chillys_radio.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import theChillys.chillys_radio.user.entity.ConfirmationCode;
import theChillys.chillys_radio.user.entity.User;

import java.util.List;
import java.util.Optional;


public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByName(String username);
    List<User> findByNameContainingOrEmailContaining(String name, String email);
    Optional<User> findFirstByCodesContains(ConfirmationCode code);



}


