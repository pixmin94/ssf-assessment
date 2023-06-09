package vttp2023.batch3.ssf.frontcontroller.respositories;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AuthenticationRepository {

	@Autowired 
    @Qualifier("auth")
    private RedisTemplate<String, Object> template;
	// TODO Task 5
	// Use this class to implement CRUD operations on Redis
	public void addDisabledUser(String username) {
        template.opsForValue().set("disabled_"+username, username, Duration.ofMinutes(1));
    }

    public boolean get(String username){
        String json = (String)template.opsForValue().get("disabled_"+username);
        if(null == json || json.trim().length() <= 0){
            return false;
        }
        return true;
    }
}
