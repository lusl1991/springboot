package com.softel.springboot.init;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import com.softel.springboot.util.RSAUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InitializeService implements InitializingBean {

	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("实例化service");
		RSAUtils.generateKeyPair();
	}

}
