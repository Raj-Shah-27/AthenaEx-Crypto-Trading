package com.rns2706.athenaex.service;

import com.rns2706.athenaex.domain.VerificationType;
import com.rns2706.athenaex.model.User;

public interface UserService {
    public User findUserProfileByJwt(String jwt) throws Exception;
    public User findUserbyEmail(String email) throws Exception;
    public User findUserById(Long userId) throws Exception;
    public User enableTwoFactorAuthentication(VerificationType verificationType, String sendTo, User user);

    User updatePassword(User user, String newPassword);
}
