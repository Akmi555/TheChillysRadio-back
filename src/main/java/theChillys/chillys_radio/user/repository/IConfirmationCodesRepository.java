package theChillys.chillys_radio.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import theChillys.chillys_radio.user.entity.ConfirmationCode;

import java.time.LocalDateTime;
import java.util.Optional;

public interface IConfirmationCodesRepository extends JpaRepository<ConfirmationCode, Long> {
    Optional<ConfirmationCode> findByCodeAndExpiredDateTimeAfter(String code, LocalDateTime now);
}
