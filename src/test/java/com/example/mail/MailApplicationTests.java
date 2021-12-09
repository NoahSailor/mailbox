package com.example.mail;

import com.example.mail.service.MailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MailApplicationTests {

	@Autowired
	MailService mailService;

	@Test
	void contextLoads() {
	}

	@Test
	void mail1Test(){
		mailService.sendMail1();
	}

	@Test
	void mail2Test(){
		mailService.sendMail2();
	}

	@Test
	void mail3Test(){
		mailService.sendMail3();
	}
}
