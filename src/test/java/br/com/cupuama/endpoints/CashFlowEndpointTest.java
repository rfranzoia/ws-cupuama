package br.com.cupuama.endpoints;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.cupuama.dto.CashFlowDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CashFlowEndpointTest {

	private final String localhost = "http://localhost:";

	@Value("${local.server.port}")
	private int port;

	private TestRestTemplate restTemplate = new TestRestTemplate();

	@Test
	@Ignore
	public void testListCashFlows() throws InterruptedException {

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString());
		headers.add(HttpHeaders.AUTHORIZATION,
				"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTU1MTY2MzY1Mn0.keYSIdZUcbZnnG8LHM9sRhvvjArKf40c0BnNX6ewZPLwjgCE84PbW4UG6y0gSb64v3HkM9z6CZAs2XymRXyNPQ");

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.localhost + this.port + "/v1/cashFlows/");

		HttpEntity<?> entity = new HttpEntity<>(headers);

		ResponseEntity<CashFlowDTO[]> response = this.restTemplate.exchange(builder.build().encode().toUri(),
				HttpMethod.GET, entity, CashFlowDTO[].class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		Thread.sleep(500L);

		CashFlowDTO[] cashFlows = response.getBody();

		assertThat(cashFlows.length).isEqualTo(3);

	}

	@SuppressWarnings("unused")
	@Test(expected = RestClientException.class)
	public void testListCarsAuthenticationFail() throws InterruptedException {

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString());

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.localhost + this.port + "/v1/cashflows/");

		ResponseEntity<CashFlowDTO[]> response = this.restTemplate.getForEntity(builder.build().encode().toUri(),
				CashFlowDTO[].class);

	}

}
