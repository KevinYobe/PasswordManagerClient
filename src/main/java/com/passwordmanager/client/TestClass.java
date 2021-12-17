package com.passwordmanager.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import passwordmanager.client.rest.AuditRestClient;

@Component
public class TestClass {
	
	@Autowired
	AuditRestClient auditRestClient;
	
	String url = "http://localhost:8080/audit/getAuditById/1";
	
	public TestClass() {
		
	}
	
	public void print() {
		
	}
	
	public void test() {
		print();
	}

}
