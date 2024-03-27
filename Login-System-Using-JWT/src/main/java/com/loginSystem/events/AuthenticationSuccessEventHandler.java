package com.loginSystem.events;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessEventHandler {

	@EventListener
	public void sendEmailOnLogin (AuthenticationSuccessEvent event) {
		String name = event.getAuthentication().getName();
		System.out.println("sending mail to " + name);
	}
}

/*
  When you manually set the Authentication object in the SecurityContextHolder,
  Spring Security is not aware of the authentication process, and therefore, it
  does not publish the AuthenticationSuccessEvent.
 */