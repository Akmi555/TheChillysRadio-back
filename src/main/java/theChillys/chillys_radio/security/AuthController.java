package theChillys.chillys_radio.security;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import theChillys.chillys_radio.user.dto.UserRequestDto;
import theChillys.chillys_radio.user.dto.UserResponseDto;
import theChillys.chillys_radio.user.entity.User;
import theChillys.chillys_radio.user.service.UserServiceImpl;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tags(value = {@Tag(name = "Auth")})
public class AuthController {

    private final AuthService authService;
    private final UserServiceImpl service;

    @Operation(summary = "Login user", description = "Login a user by their username and password")
    @PostMapping("/login")
    public TokenResponseDto login(@RequestBody UserLoginDto user) {
        try {
            return authService.login(user);

        } catch (AuthException e) {
            return new TokenResponseDto(null, null);
        }
    }

    @Operation(summary = "Refresh access token", description = "Login a user by new generated access token")
    @PostMapping("/refresh")
    public TokenResponseDto getNewAccessToken(@RequestBody RefreshRequestDto dto) {
        return authService.getNewAccessToken(dto.getRefreshToken());
    }

    @Operation(summary = "Logout user", description = "Log out from the system")
    @GetMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) {

        Cookie cookie = new Cookie("Authorization", null);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Register new user", description = "Register for an account")
    @PostMapping("/register")
    public UserResponseDto registrationUser(@Valid @RequestBody UserRequestDto user) {
        return service.createUser(user);
    }

    @Operation(summary = "Confirm registration", description = "Confirmation new user registration by checking a code")
    @GetMapping("/confirm/{confirm-code}")
    public UserResponseDto getConfirmation(@PathVariable("confirm-code") String confirmCode){
        return service.confirm(confirmCode);
    }

}

