package br.com.fr.cupuama;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTasks {

	private Logger logger = Logger.getLogger(getClass());
	
	@Scheduled(cron = "${horario.reset.senha}")
	public void resetSenhas() {
		logger.info("resetSenhas() ... ");
	}
	
}
