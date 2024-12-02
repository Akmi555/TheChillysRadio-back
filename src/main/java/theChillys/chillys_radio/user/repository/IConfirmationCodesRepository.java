package theChillys.chillys_radio.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import theChillys.chillys_radio.user.entity.ConfirmationCode;

public interface IConfirmationCodesRepository extends JpaRepository<ConfirmationCode, Long> {
}
