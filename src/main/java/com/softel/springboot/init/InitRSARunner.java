package com.softel.springboot.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.softel.springboot.util.RSAUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class InitRSARunner implements CommandLineRunner {

	@Override
	public void run(String... arg0) throws Exception {
		log.info("初始化公钥私钥......");
		RSAUtils.generateKeyPair();
	}

}
