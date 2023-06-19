package EShopping.EShopping.wepApi.controllers;

import EShopping.EShopping.business.RefreshTokenService;
import EShopping.EShopping.business.UserService;
import EShopping.EShopping.dto.requests.LoginRequest;
import EShopping.EShopping.dto.requests.RefreshRequest;
import EShopping.EShopping.dto.requests.RegisterRequest;
import EShopping.EShopping.dto.responses.AuthResponse;
import EShopping.EShopping.entities.RefreshToken;
import EShopping.EShopping.entities.User;
import EShopping.EShopping.security.JwtTokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private RefreshTokenService refreshTokenService;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
                          UserService userService, PasswordEncoder passwordEncoder, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    @ResponseStatus(code = HttpStatus.CREATED)
    public AuthResponse login(@RequestBody @Validated LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = jwtTokenProvider.generateJwtToken(auth);
        User user = userService.getOneUserByUserName(loginRequest.getUserName());
        AuthResponse authResponse = new AuthResponse();
        authResponse.setAccessToken("Bearer " + jwtToken);
        authResponse.setMessage("login successfully");
        authResponse.setRefreshToken(refreshTokenService.createRefreshToken(user));
        authResponse.setUserId(user.getUserId());
        return authResponse;
    }

    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<AuthResponse> register(@RequestBody @Validated RegisterRequest registerRequest) {
        AuthResponse authResponse = new AuthResponse();
        if (userService.getOneUserByUserName(registerRequest.getUserName()) != null) {
            authResponse.setMessage("Username already in use.");
            return new
                    ResponseEntity<>(authResponse, HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setUserName(registerRequest.getUserName());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setBirthDate(registerRequest.getBirthDate());
        user.setAge(registerRequest.getAge());
        userService.saveOneUser(user);

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(registerRequest.getUserName(), registerRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtToken = jwtTokenProvider.generateJwtToken(auth);

        authResponse.setMessage("User successfully registered.");
        authResponse.setAccessToken("Bearer " + jwtToken);
        authResponse.setRefreshToken(refreshTokenService.createRefreshToken(user));
        authResponse.setUserId(user.getUserId());
        return new ResponseEntity<>(authResponse, HttpStatus.ACCEPTED);
    }

    @PostMapping("/refresh")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<AuthResponse> refresh(@RequestBody @Validated RefreshRequest refreshRequest) {
        AuthResponse response = new AuthResponse();
        RefreshToken token = refreshTokenService.getByUser(refreshRequest.getUserId());
        if (token.getToken().equals(refreshRequest.getRefreshToken()) &&
                !refreshTokenService.isRefreshExpired(token)) {
            User user = token.getUser();

            String jwtToken = jwtTokenProvider.generateJwtTokenByUserName(user.getUserId());

            response.setMessage("token successfully refreshed.");
            response.setAccessToken("Bearer " + jwtToken);
            response.setRefreshToken(refreshTokenService.createRefreshToken(user));
            response.setUserId(user.getUserId());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.setMessage("refresh token is not valid.");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

    }


}
