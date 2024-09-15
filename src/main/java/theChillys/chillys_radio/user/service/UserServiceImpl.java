package theChillys.chillys_radio.user.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import theChillys.chillys_radio.role.IRoleService;
import theChillys.chillys_radio.role.Role;
import theChillys.chillys_radio.station.dto.StationResponseDto;
import theChillys.chillys_radio.station.entity.Station;
import theChillys.chillys_radio.station.repository.IStationRepository;
import theChillys.chillys_radio.user.entity.User;
import theChillys.chillys_radio.user.dto.UserRequestDto;
import theChillys.chillys_radio.user.dto.UserResponseDto;
import theChillys.chillys_radio.user.repository.IUserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements IUserService, UserDetailsService {

    private final IUserRepository repository;
    private final IRoleService roleService;
    private final ModelMapper mapper;
    private final BCryptPasswordEncoder encoder;
    private final IStationRepository stationRepository;
//  private final UserDetailsServiceAutoConfiguration userDetailsServiceAutoConfiguration;

    public User findUserById(Long userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User with id:" + userId + " not found"));
    }

    @Override
    public UserResponseDto getUsersFavoriteStations(Long userId) {

        User user = findUserById(userId);

        List<StationResponseDto> favoriteStationsDto = user.getFavorites().stream()
                .map(st -> mapper.map(st, StationResponseDto.class))
                .collect(Collectors.toList());

        Set<Role> roles = user.getRoles();

        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                favoriteStationsDto,
                roles);
    }


    @Override
    public boolean setLike(Long userId, Long stationId) {

        User user = findUserById(userId);
        Station station = stationRepository.findById(stationId)
                .orElseThrow(() ->
                        new RuntimeException("Station not found with id: " + stationId));

        if (user.getFavorites().contains(station)) {
            return false;
        }

        user.getFavorites().add(station);
        repository.save(user);

        return true;
    }


    @Override
    public boolean logOut(Long userId) {

        return false;
    }

    @Override
    public List<UserResponseDto> getUsers() {
        List<User> customers = repository.findAll();

        return customers.stream().map(c->mapper.map(c, UserResponseDto.class)).toList();
    }

    @Override
    public UserResponseDto createUser(UserRequestDto dto) {

        repository.findUserByName(dto.getName()).ifPresent(u -> {
            throw new RuntimeException("User " + dto.getName() + " already exists");
        });

        Role role = roleService.getRoleByTitle("ROLE_USER");

        String encodedPass = encoder.encode(dto.getPassword());

        User newUser = new User();
        newUser.setName(dto.getName());
        newUser.setEmail(dto.getEmail());
        newUser.setPassword(encodedPass);
        newUser.setRoles(Collections.singleton(role));
        newUser.setFavorites(Collections.emptySet());

        User savedUser = repository.save(newUser);

        return mapper.map(savedUser, UserResponseDto.class);
    }

    @Override
    public UserResponseDto setAdminRole(String username) {
        // TODO Реализуйте логику назначения роли администратора

      return null;
    }



    @Override
    public Optional<UserResponseDto> getUserById(Long id) {

      return Optional.ofNullable(mapper.map(findUserById(id), UserResponseDto.class));
    }

    @Override
    public List<UserResponseDto> findUsersByNameOrEmail(String name, String email) {
        List<User> users = repository.findByNameContainingOrEmailContaining(name, email);

        return users.stream()
                .map(user -> mapper.map(user, UserResponseDto.class)).toList();
    }

    //как spring получает User по логину
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

      return repository.findUserByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("User with name: " + name + " not found"));
    }

    @Override
    public boolean toggleFavoriteStation(Long userId, String stationUuid) {
        User user = findUserById(userId);
        Object station = stationRepository.findByUuid(stationUuid)
                .orElseThrow(() -> new RuntimeException("Station not found with UUID: " + stationUuid));

        if (user.getFavorites().contains(station)) {

            user.getFavorites().remove(station);
            repository.save(user);
            return false;
        } else {
            user.getFavorites().add((Station) station);
            repository.save(user);
            return true;
        }
    }



}


