package com.rns2706.athenaex.controller;

import com.rns2706.athenaex.repo.UserRepo;
import com.rns2706.athenaex.config.JwtProvider;
import com.rns2706.athenaex.model.TwoFactOTP;
import com.rns2706.athenaex.model.User;
import com.rns2706.athenaex.request.LoginRequest;
import com.rns2706.athenaex.response.AuthResponse;
import com.rns2706.athenaex.service.*;
import com.rns2706.athenaex.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private TwoFactOTPService twoFactOTPService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private WatchlistService watchlistService;


    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(@RequestBody User user) throws Exception {

        User isEmailPresent = userRepo.findByEmail(user.getEmail());
        if(isEmailPresent!=null){
            throw new Exception("Email is already registered with another account");
        }

        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepo.save(newUser);
        watchlistService.createWatchList(savedUser);

        Authentication auth = new UsernamePasswordAuthenticationToken(
                user.getEmail(),
                user.getPassword()
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = JwtProvider.generateToken(auth);

        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("Successfully registered");
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req) throws Exception {

        String userName = req.getEmail();
        String password = req.getPassword();
        Authentication auth = authenticate(userName, password);
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = JwtProvider.generateToken(auth);

        User authUser = userRepo.findByEmail(userName);

        if(authUser.getTwoFactAuth().isEnabled()){
            AuthResponse res = new AuthResponse();
            res.setMessage("Two factor authentication is enabled");
            res.setTwoFactAuthEnabled(true);
            String otp = OtpUtils.generateOTP();

            TwoFactOTP oldTwoFactOTP = twoFactOTPService.findByUser(authUser.getId());
            if(oldTwoFactOTP!=null){
                twoFactOTPService.deleteTwoFactOTP(oldTwoFactOTP);
            }

            TwoFactOTP newTwoFactOTP = twoFactOTPService.createTwoFactorOTP(authUser,otp,jwt);

            emailService.sendVerificationOTPEmail(userName,otp);

            res.setSession(newTwoFactOTP.getId());
            return new ResponseEntity<>(res,HttpStatus.CREATED);
        }

        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setStatus(true);
        res.setMessage("Login successful");
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    private Authentication authenticate(String userName, String password) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
        if(userDetails==null){
            throw new BadCredentialsException("Invalid username");
        }
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,password,userDetails.getAuthorities());
    }

    @PostMapping("/two-factor/otp/{otp}")
    public ResponseEntity<AuthResponse> verifySignInOTP(@PathVariable String otp, @RequestParam String id) throws Exception {
        TwoFactOTP twoFactOTP = twoFactOTPService.findById(id);
        if(twoFactOTPService.verifyTwoFactOTP(twoFactOTP, otp)){
            AuthResponse res = new AuthResponse();
            res.setMessage("Two factor authentication successful");
            res.setTwoFactAuthEnabled(true);
            res.setJwt(twoFactOTP.getJwt());
            return new ResponseEntity<>(res,HttpStatus.OK);
        }
        throw new Exception("Invalid OTP entered");
    }
}
