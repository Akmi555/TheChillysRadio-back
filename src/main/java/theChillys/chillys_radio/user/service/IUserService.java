package theChillys.chillys_radio.user.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import theChillys.chillys_radio.station.dto.StationResponseDto;
import theChillys.chillys_radio.user.dto.UserRequestDto;
import theChillys.chillys_radio.user.dto.UserResponseDto;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    UserResponseDto createUser(UserRequestDto dto);

    List<UserResponseDto> getUsers();

    Optional<UserResponseDto> getUserById(Long id);
    List<UserResponseDto> findUsersByNameOrEmail(String name, String email);

    UserResponseDto getUsersFavoriteStations(Long userId);
    boolean setLike(Long userId, Long stationId);
    boolean logOut(Long userId);

    UserResponseDto setAdminRole(String username);

    UserDetails loadUserByUsername(String name) throws UsernameNotFoundException;

    boolean toggleFavoriteStation(Long userId, String stationuuid);

}
