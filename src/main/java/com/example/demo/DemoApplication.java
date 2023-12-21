package com.example.demo;

import com.example.demo.model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;


//@SpringBootApplication
public class DemoApplication {
	private static final RestTemplate restTemplate = new RestTemplate();
	private static final String baseURL = "http://94.198.50.185:7081/api/users";
	public static void main(String[] args) {
		//SpringApplication.run(DemoApplication.class, args);
		useExchangeMethodsOfRestTemplates();
	}

	public static void useExchangeMethodsOfRestTemplates() {
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
		//System.out.println(getListOfUsers(requestEntity));

		String res = "";

		String cookies = getListOfUsers(requestEntity);
		System.out.println(cookies);
		headers.add("Cookie", cookies);

		User user = new User(3L, "James", "Brown", (byte) 14);
		requestEntity = new HttpEntity<>(user, headers);
		String save = saveUser(requestEntity);
		user.setName("Thomas");
		user.setLastName("Shelby");
		requestEntity = new HttpEntity<>(user, headers);
		String update = updateUser(requestEntity);
		String delete = deleteUser(requestEntity);
		res = save + update + delete;
		System.out.println(res);
	}

	public static String getListOfUsers(HttpEntity<Object> requestEntity) {
		ResponseEntity<String> responseEntity = restTemplate.exchange(baseURL,
				HttpMethod.GET,
				requestEntity,
				String.class);
		String headers = String.valueOf(responseEntity.getHeaders().getFirst("Set-Cookie"));
		String listOfUsers = responseEntity.getBody();
		System.out.println(listOfUsers);
		return headers.split(";")[0];
	}

	public static String saveUser(HttpEntity<Object> requestEntity) {
		ResponseEntity<String> responseEntity = restTemplate.exchange(baseURL,
				HttpMethod.POST,
				requestEntity,
				String.class);
		return responseEntity.getBody();
		//System.out.println(responseEntity.getHeaders());
	}

	public static String updateUser(HttpEntity<Object> requestEntity) {
		ResponseEntity<String> responseEntity = restTemplate.exchange(baseURL,
				HttpMethod.PUT,
				requestEntity,
				String.class);
		return responseEntity.getBody();
	}

	public static String deleteUser(HttpEntity<Object> requestEntity) {
		ResponseEntity<String> responseEntity = restTemplate.exchange(baseURL + "/3",
				HttpMethod.DELETE,
				requestEntity,
				String.class);
		return responseEntity.getBody();
	}

}
