package br.com.fr.cupuama;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController implements ErrorController{

    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    public String error() {
    	String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    	
    	String error = "{\"timestamp\":\"" + now + "\", " +
				    	"\"status\":null, " +
				    	"\"error\":null, " +
				    	"\"message\":\"Não foi possivel encontrar o endereco especificado!\", " +
				    	"\"path\":\"" + getErrorPath() + "\"}";
    	
        return error;
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}