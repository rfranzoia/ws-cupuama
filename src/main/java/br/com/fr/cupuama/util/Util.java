package br.com.fr.cupuama.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

public class Util {

	public static final Integer ZERO = Integer.valueOf(0);

    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("00");
    public static final SimpleDateFormat DATE_FORMAT_LONG_BR = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_LONG = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat DATE_FORMAT_SHORT = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat DATE_FORMAT_BR = new SimpleDateFormat("dd/MM/yyyy");
    public static final SimpleDateFormat DATE_FORMAT_COMPACT = new SimpleDateFormat("yyyyMMdd");
    
    public static final SimpleDateFormat HOUR_FORMAT = new SimpleDateFormat("HH:mm:ss");
    
    public static final SimpleDateFormat DATE_FORMAT_ANOMES = new SimpleDateFormat("yyyyMM");
    public static final SimpleDateFormat DATE_FORMAT_TIMESTAMP_COMPACT = new SimpleDateFormat("yyyyMMddHHmmss");
    
    public static final String PROTOCOLO_FORMAT = "([0-9]{4})([0-9]{2})([0-9]{2})([0-9]{4})";

    public static final String NUM_DEDO_REGEX = "([\\D]{0,5})([0-9]{0,2})";

    public static final String PATH_TO_FILES = "./";

    private Util() {
    	super();
    }
    
    public static String padLeft(String string, int size) {
        return String.format("%1$" + size + "s", string);
    }

    public static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }
    

	public static boolean isNull(Number valor) {
		return ((valor == null) || (ZERO.equals(valor)));
	}

	public static boolean isNotNull(Number valor) {
		return !isNull(valor);
	}

	public static boolean isNull(String valor) {
		return ((valor == null) || (valor.trim().isEmpty()));
	}

	public static boolean isNotNull(String valor) {
		return !isNull(valor);
	}

	public static boolean isNull(Character valor) {
		return ((valor == null) || (Character.isWhitespace(valor.charValue())));
	}

	public static boolean isNotNull(Character valor) {
		return !isNull(valor);
	}
	
	public static boolean isNull(List<?> lista) {
		return lista == null || lista.isEmpty();
	}
	
	public static boolean isNotNull(List<?> lista) {
		return !isNull(lista);
	}

	public static <TO, DTO> List<DTO> buildListDTO(List<TO> lista, Class<DTO> classDTO, PropertyMap<TO, DTO> propertyMap) {

		ArrayList<DTO> listaDTO = new ArrayList<>();

		ModelMapper mapper = new ModelMapper();

		if (propertyMap != null) {
			mapper.addMappings(propertyMap);
		}
		if (!lista.isEmpty()) {
			for (TO obj : lista) {
				listaDTO.add(mapper.map(obj, classDTO));
			}
		}

		return listaDTO;
	}

	public static <TO, DTO> List<DTO> buildListDTO(List<TO> lista, Class<DTO> classDTO) {
		return buildListDTO(lista, classDTO, null);
	}

	public static <TO, DTO> DTO buildDTO(TO obj, Class<DTO> classDTO, PropertyMap<TO, DTO> propertyMap) {

		if (obj == null) {
			return null;
		}
		ModelMapper mapper = new ModelMapper();

		if (propertyMap != null) {
			mapper.addMappings(propertyMap);
		}

		return mapper.map(obj, classDTO);

	}

	public static <TO, DTO> DTO buildDTO(TO obj, Class<DTO> classDTO) {
		return buildDTO(obj, classDTO, null);
	}

	public static <T> T jsonTo(InputStream stream, Class<T> clazz) {
		ObjectMapper mapper = new ObjectMapper();

		try {
			return mapper.readValue(stream, clazz);
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
	}
	
	public static int sendHttpMessage(String input) throws Exception {
		
		final String apiKey = "AIzaSyC_t3xv_ZSi9o9KL3J4BygfXf-NUCIPOvE";
		
		URL url = new URL("https://fcm.googleapis.com/fcm/send");
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setDoOutput(true);
	    conn.setRequestMethod("POST");
	    conn.setRequestProperty("Content-Type", "application/json");
	    conn.setRequestProperty("Authorization", "key=" + apiKey);

	    conn.setDoOutput(true);

	    OutputStream os = conn.getOutputStream();
	    os.write(input.getBytes());
	    os.flush();
	    os.close();

	    
	    int responseCode = conn.getResponseCode();
	    
	    return responseCode;
		
	}
}