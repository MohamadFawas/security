package com.defect.tracker.event.listener;

import java.util.UUID;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.defect.tracker.event.RegistrationCompleteEvent;
import com.defect.tracker.user.User;
import com.defect.tracker.user.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

	private final UserService userService;
	@Override
	public void onApplicationEvent(RegistrationCompleteEvent event) {
		 // 1. Get the newly registered user
        User theUser = event.getUser();
        //2. Create a verification token for the user
        String verificationToken = UUID.randomUUID().toString();
        //3. Save the verification token for the user
        userService.saveUserVerificationToken(theUser, verificationToken);
        //4 Build the verification url to be sent to the user
        String url = event.getApplicationUrl()+"/register/verifyEmail?token="+verificationToken;
        //5. Send the email.
   /*     try {
            sendVerificationEmail(url);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        */
        log.info("Click the link to verify your registration :  {}", url);
    
		
	}
	
}
