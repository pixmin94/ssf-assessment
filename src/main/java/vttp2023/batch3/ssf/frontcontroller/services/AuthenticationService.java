package vttp2023.batch3.ssf.frontcontroller.services;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.UriComponentsBuilder;

import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import vttp2023.batch3.ssf.frontcontroller.respositories.AuthenticationRepository;

@Service
public class AuthenticationService {
	@Value("${auth.service.url}")
    private String authUrl;

	@Autowired
	private AuthenticationRepository repository;

	// TODO: Task 2
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write the authentication method in here
	public boolean authenticate(String username, String password) throws Exception {
		String loginUrl = UriComponentsBuilder
            .fromUriString(authUrl)
            .toUriString();
		
		String jsonString = "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}";
		System.out.println("jsonString: "+jsonString);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<String>(jsonString, headers);

        RestTemplate template = new RestTemplate();
        try {
			ResponseEntity<String> r = template
			.postForEntity(loginUrl, request, String.class);
			System.out.println(r);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}

	// TODO: Task 3
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write an implementation to disable a user account for 30 mins
	public void disableUser(String username) {
		repository.addDisabledUser(username);
	}

	// TODO: Task 5
	// DO NOT CHANGE THE METHOD'S SIGNATURE
	// Write an implementation to check if a given user's login has been disabled
	public boolean isLocked(String username) {
		return false;
	}
}
