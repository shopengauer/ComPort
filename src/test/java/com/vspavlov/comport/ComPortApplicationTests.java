package com.vspavlov.comport;

import com.vspavlov.comport.serial.Stopbits;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ComPortApplication.class)
@WebAppConfiguration
public class ComPortApplicationTests {

	@Test
	public void contextLoads() {

	//	System.out.println(Stopbits.getValueByLabel("1"));

	}

}
