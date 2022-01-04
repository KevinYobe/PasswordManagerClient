package com.passwordmanager.client.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.passwordmanager.client.model.Notification;

@Component("messenger")
public class Messenger{

	@Autowired
	private JavaMailSender emailSender;
	
	public Messenger() {
		
	}

	public void sendNotification(Notification notification) {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(notification.getDestination());
		message.setSubject("Please confirm your account");
		message.setText(notification.getMessage());
		emailSender.send(message);

	}

}
