package br.com.fr.cupuama.util;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.ServerError;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.apache.coyote.http2.ConnectionException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.omg.CORBA.portable.ApplicationException;

public class HttpClientConnect {

	public static enum RequestType {
		GET, POST, PUT, DELETE
	}

	public static <T> T connectByJson(String url, RequestType type, Class<T> clazz) throws Exception {
		return HttpClientConnect.connect(url, type, MediaType.APPLICATION_JSON, null, clazz, null);
	}

	public static <T> T connectByJson(String url, RequestType type, Object param, Class<T> clazz) throws Exception {
		return HttpClientConnect.connect(url, type, MediaType.APPLICATION_JSON, param, clazz, null);
	}

	public static <T> T connectByJson(String url, RequestType type, TypeReference<T> typeReference) throws Exception {
		return HttpClientConnect.connect(url, type, MediaType.APPLICATION_JSON, null, null, typeReference);
	}

	/**
	 * Retorna o objeto correspondente ao serviço solicitado, retornando uma exececao RegistroNaoEncontradoException.
	 *
	 * @param url
	 * @param contentType
	 * @param clazz
	 * @return
	 * @throws ApplicationException
	 * @throws ConnectionException
	 * @throws RegistroNaoEncontradoException
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	public static <T> T connect(String url, RequestType type, String contentType, Object param, Class<T> clazz, TypeReference<?> typeReference) throws Exception {

		try {
			ObjectMapper mapper = new ObjectMapper();

			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpRequestBase http = null;
			switch (type) {
			case GET:
				http = new HttpGet(url);
				break;
			case POST:
				http = new HttpPost(url);
				if (param != null) {
					HttpPost post = (HttpPost) http;
					post.setEntity(new StringEntity(new ObjectMapper().writeValueAsString(param),"UTF-8"));
				}
				break;
			case PUT:
				http = new HttpPut(url);
				if (param != null) {
					HttpPut put = (HttpPut) http;
					put.setEntity(new StringEntity(new ObjectMapper().writeValueAsString(param),"UTF-8"));
				}
				break;	
			
			default:
				throw new IllegalArgumentException("RequestType nao encontrado");
			}

			http.addHeader("content-type", contentType);
			HttpResponse response = httpClient.execute(http);
			if (HttpServletResponse.SC_NO_CONTENT == response.getStatusLine().getStatusCode()) {
				return null;
			}
			
			System.out.println("ERRO HTTP: "+response.getStatusLine().getStatusCode());
			HttpEntity entity = response.getEntity();
			InputStream content = entity.getContent();

			if (HttpServletResponse.SC_NOT_FOUND == response.getStatusLine().getStatusCode()) {
			}

			if (HttpServletResponse.SC_INTERNAL_SERVER_ERROR == response.getStatusLine().getStatusCode()) {
			}

			if (HttpServletResponse.SC_BAD_REQUEST == response.getStatusLine().getStatusCode()) {
				throw new ServerErrorException(mapper.readValue(content, ServerError.class));
			}

			if (clazz != null) {
				return mapper.readValue(content, clazz);
			} else {
				return mapper.readValue(content, typeReference);
			}
		} catch (ServerErrorException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}
}