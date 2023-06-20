package com.defect.tracker.user;

import java.util.List;
import java.util.Optional;

import com.defect.tracker.registration.RegistrationRequest;

public interface IUserService {
	List<User> getUsers();
    User registerUser(RegistrationRequest request);
    Optional<User> findByEmail(String email);
    
    public void saveUserVerificationToken(User theUser, String verificationToken);
    
    String validateToken(String theToken);
}
