package com.passwordmanager.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/audit")
public class AuditController {
	
	public AuditController() {
		
	}
	
	@GetMapping("/viewAuditEvents")
	public void viewAuditEvents() {
		
	}

}
