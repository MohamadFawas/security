package com.defect.tracker.user;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.defect.tracker.exception.UserAlreadyExistsException;
import com.defect.tracker.registration.RegistrationRequest;
import com.defect.tracker.registration.token.VerificationToken;
import com.defect.tracker.registration.token.VerificationTokenRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final VerificationTokenRepository tokenRepository;
	
	@Override
	public List<User> getUsers() {
		return userRepository.findAll();
	}

	@Override
	public User registerUser(RegistrationRequest request) {
		Optional<User> user = this.findByEmail(request.email());
		if(user.isPresent()) {
	           throw new UserAlreadyExistsException(
	                   "User with email "+request.email() + " already exists");
	       }
		var newUser = new User();
	    newUser.setFirstName(request.firstName());
	    newUser.setLastName(request.lastName());
	    newUser.setEmail(request.email());
	    newUser.setPassword(passwordEncoder.encode(request.password()));
	    newUser.setRole(request.role());
	    return userRepository.save(newUser);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public void saveUserVerificationToken(User theUser, String token) {
		var verificationToken = new VerificationToken(token, theUser);
        tokenRepository.save(verificationToken);
		
	}

	@Override
	public String validateToken(String theToken) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
