package com.rns2706.athenaex.controller;

import com.rns2706.athenaex.request.ForgotPasswordTokenRequest;
import com.rns2706.athenaex.domain.VerificationType;
import com.rns2706.athenaex.model.ForgotPasswordToken;
import com.rns2706.athenaex.model.User;
import com.rns2706.athenaex.model.VerificationCode;
import com.rns2706.athenaex.request.ResetPasswordRequest;
import com.rns2706.athenaex.response.ApiResponse;
import com.rns2706.athenaex.response.AuthResponse;
import com.rns2706.athenaex.service.EmailService;
import com.rns2706.athenaex.service.ForgotPasswordService;
import com.rns2706.athenaex.service.UserService;
import com.rns2706.athenaex.service.VerificationCodeService;
import com.rns2706.athenaex.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    private EmailService emailService;
    private String jwt;

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @GetMapping("/api/users/profile")
    public ResponseEntity<User> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping("/api/users/verification/{verificationType}/send-otp")
    public ResponseEntity<String> sendVerificationOTP(@RequestHeader("Authorization") String jwt,
                                                    @PathVariable VerificationType verificationType) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        VerificationCode verificationCode = verificationCodeService.getVerificationCodebyUser(user.getId());
        if(verificationCode==null){
            verificationCode = verificationCodeService.sendVerificationCode(user,verificationType);
        }

        if(verificationType.equals(VerificationType.EMAIL)){
            emailService.sendVerificationOTPEmail(user.getEmail(), verificationCode.getOtp());
        }
        return new ResponseEntity<>("Verification OTP sent", HttpStatus.OK);
    }

    @PatchMapping("/api/users/enable-two-factor/verify-otp/{otp}")
    public ResponseEntity<User> enableTwoFactorAuthentication(@PathVariable String otp, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);

        VerificationCode verificationCode = verificationCodeService.getVerificationCodebyUser(user.getId());
        String sendTo = verificationCode.getVerificationType().equals(VerificationType.EMAIL)?verificationCode.getEmail():verificationCode.getMobile();
        boolean isVerified = verificationCode.getOtp().equals(otp);
        if(isVerified){
            User updatedUser=userService.enableTwoFactorAuthentication(verificationCode.getVerificationType(),sendTo,user);
            verificationCodeService.deleteVerificationCodeById(verificationCode);
            return new ResponseEntity<User>(updatedUser, HttpStatus.OK);
        }
        throw new Exception("Incorrect OTP entered");
    }

    @PostMapping("/auth/users/reset-password/send-otp")
    public ResponseEntity<AuthResponse> sendForgotPasswordOTP(@RequestBody ForgotPasswordTokenRequest req) throws Exception {
        User user = userService.findUserbyEmail(req.getSendTo());
        String otp = OtpUtils.generateOTP();
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();

        ForgotPasswordToken token = forgotPasswordService.findByUser(user.getId());
        if(token == null){
            token = forgotPasswordService.createToken(user,id, otp,req.getVerificationType(), req.getSendTo());
        }

        if(req.getVerificationType().equals(VerificationType.EMAIL)){
            emailService.sendVerificationOTPEmail(user.getEmail(),token.getOtp());
        }
        AuthResponse response = new AuthResponse();
        response.setSession(token.getId());
        response.setMessage("Password reset OTP sent");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/api/users/reset-password/verify-otp")
    public ResponseEntity<ApiResponse> resetPassword(@RequestParam String id,
                                              @RequestBody ResetPasswordRequest req,
                                              @RequestHeader("Authorization") String jwt) throws Exception {
        ForgotPasswordToken forgotPasswordToken = forgotPasswordService.findById(id);
        boolean isVerified = forgotPasswordToken.getOtp().equals(req.getOtp());
        if(isVerified){
            userService.updatePassword(forgotPasswordToken.getUser(), req.getPassword());
            ApiResponse res= new ApiResponse();
            res.setMessage("Password updated successfully");
            return new ResponseEntity<>(res,HttpStatus.ACCEPTED);
        }
        throw new Exception("Incorrect OTP entered");
    }

}
