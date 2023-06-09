package vttp2023.batch3.ssf.frontcontroller.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping(path="/protected")
public class ProtectedController {

	// TODO Task 5
	// Write a controller to protect resources rooted under /protected
	@GetMapping(path="/view1")
	public String getProtected(HttpSession session){
		if (session.getAttribute("user") != null) {
			return "view1";
		} else {
			return "view0";
		}
	}
}
