package vttp2023.batch3.ssf.frontcontroller.respositories;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;

public class AuthenticationRepository {

	@Autowired 
    @Qualifier("auth")
    private RedisTemplate<String, Object> template;
	// TODO Task 5
	// Use this class to implement CRUD operations on Redis
	public void addDisabledUser(String username) {
        template.opsForValue().set("disabled", username, Duration.ofMinutes(1));
    }
}
