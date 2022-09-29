package tn.esprit.banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tn.esprit.banking.entity.Role;
import tn.esprit.banking.entity.User;
import tn.esprit.banking.enums.ERole;
import tn.esprit.banking.payload.request.LoginRequest;
import tn.esprit.banking.payload.request.SignupRequest;
import tn.esprit.banking.payload.response.JwtResponse;
import tn.esprit.banking.payload.response.MessageResponse;
import tn.esprit.banking.repository.RoleRepository;
import tn.esprit.banking.repository.UserRepository;
import tn.esprit.banking.securiry.jwt.JwtUtils;
import tn.esprit.banking.service.IUserService;
import tn.esprit.banking.service.UserDetailsImpl;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final String USER_BANNED_N_BAN_RAISON = "user banned /n ban raison : ";
    private static final String USER_NOT_SIGNED = "User not signed";
    private static final String ERROR_EMAIL_IS_ALREADY_IN_USE = "Error: Email is already in use!";
    private static final String ERROR_USERNAME_IS_ALREADY_TAKEN = "Error: Username is already taken!";
    private static final String USER_REGISTERED_SUCCESSFULLY = "User registered successfully!";
    private static final String ERROR_ROLE_IS_NOT_FOUND = "Error: Role is not found.";

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    IUserService userService;


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser( @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        User user = userService.getUserByUsername(loginRequest.getUsername());


        if (user.getIsBanned()) {
            return ResponseEntity.ok(USER_BANNED_N_BAN_RAISON + user.getBanRaison());
        }
        Boolean isAdmin = Boolean.FALSE;
        Set<Role> userRoles = user.getRoles();
        Iterator<Role> iter=userRoles.iterator();
        while(!isAdmin && iter.hasNext()) {
            Role role = iter.next();
            if (role.getName().equals(ERole.ADMIN)) {
                isAdmin = Boolean.TRUE;
            }
        }
        if (!isAdmin && !user.getIsSigned()) {
            return ResponseEntity.ok(USER_NOT_SIGNED);
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok(
                new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));

    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse(ERROR_USERNAME_IS_ALREADY_TAKEN));
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse(ERROR_EMAIL_IS_ALREADY_IN_USE));
        }
        // Create new user's account
        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.CLIENT)
                    .orElseThrow(() -> new RuntimeException(ERROR_ROLE_IS_NOT_FOUND));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "ADMIN":
                        Role adminRole = roleRepository.findByName(ERole.ADMIN)
                                .orElseThrow(() -> new RuntimeException(ERROR_ROLE_IS_NOT_FOUND));
                        roles.add(adminRole);
                        break;
                    case "AGENT":
                        Role modRole = roleRepository.findByName(ERole.AGENT)
                                .orElseThrow(() -> new RuntimeException(ERROR_ROLE_IS_NOT_FOUND));
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.CLIENT)
                                .orElseThrow(() -> new RuntimeException(ERROR_ROLE_IS_NOT_FOUND));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        user.setIsBanned(false);
        user.setIsSigned(false);
        user.setDateCreate(new Date());
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse(USER_REGISTERED_SUCCESSFULLY));
    }
}
