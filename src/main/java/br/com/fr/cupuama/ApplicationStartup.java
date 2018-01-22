package br.com.fr.cupuama;

import org.apache.log4j.Logger;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent>{

	private final Logger logger = Logger.getLogger(getClass());
	
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		logger.info("onApplicationEvent() ... ");
	}

}
