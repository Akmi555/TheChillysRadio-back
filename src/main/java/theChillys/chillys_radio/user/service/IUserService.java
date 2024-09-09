package theChillys.chillys_radio.user.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import theChillys.chillys_radio.user.dto.UserRequestDto;
import theChillys.chillys_radio.user.dto.UserResponseDto;

import java.util.List;

public interface IUserService extends UserDetailsService {
    UserResponseDto getUsersFavoriteStations(Long userId, Long stationId);
    public boolean setLike(Long userId, Long stationId);
    public boolean logOut (Long userId);

    public List<UserResponseDto> getUsers();
//  public UserResponseDto getUserById(Long id);
    public UserResponseDto createUser(UserRequestDto dto);
    public UserResponseDto setAdminRole(String username);
}
