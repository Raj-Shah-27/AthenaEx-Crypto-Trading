package com.rns2706.athenaex.service;

import com.rns2706.athenaex.repo.UserRepo;
import com.rns2706.athenaex.config.JwtProvider;
import com.rns2706.athenaex.domain.VerificationType;
import com.rns2706.athenaex.model.TwoFactAuth;
import com.rns2706.athenaex.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepo userRepo;
    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {
        String email = JwtProvider.getEmailFromToken(jwt);
        User user = userRepo.findByEmail(email);
        if(user==null){
            throw new Exception("User not fount");
        }
        return user;
    }

    @Override
    public User findUserbyEmail(String email) throws Exception {
        User user = userRepo.findByEmail(email);
        if(user==null){
            throw new Exception("User not fount");
        }
        return user;
    }

    @Override
    public User findUserById(Long userId) throws Exception {
        Optional<User> user = userRepo.findById(userId);
        if(user.isEmpty()) {
            throw new Exception("User not found");
        }
        return user.get();
    }

    @Override
    public User enableTwoFactorAuthentication(VerificationType verificationType, String sendTo, User user) {
        TwoFactAuth twoFactAuth = new TwoFactAuth();
        twoFactAuth.setEnabled(true);
        twoFactAuth.setSendTo(verificationType);
        user.setTwoFactAuth(twoFactAuth);

        return userRepo.save(user);
    }


    @Override
    public User updatePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        return userRepo.save(user);
    }
}
