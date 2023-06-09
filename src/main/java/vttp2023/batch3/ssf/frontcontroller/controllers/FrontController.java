package vttp2023.batch3.ssf.frontcontroller.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import jakarta.validation.Valid;
import jakarta.servlet.http.HttpSession;

import vttp2023.batch3.ssf.frontcontroller.model.Login;
import vttp2023.batch3.ssf.frontcontroller.services.AuthenticationService;


@Controller
public class FrontController {
	@Autowired
	private AuthenticationService service;

	// TODO: Task 2, Task 3, Task 4, Task 6
	@GetMapping(path="/")
    public String showLandingPage(Model m){
        return "view0";
    }

	@PostMapping(path="/login")
	public String postLoginCredentials(@Valid Login login, Model m, BindingResult result, HttpSession session) throws Exception{
		
		if(result.hasErrors()){
            return "view0";
        }

		String username = login.getUsername();
		System.out.println("Frontcontoller username: " + username);
		String password = login.getPassword();
		System.out.println("Frontcontoller password: " + password);
		boolean rr = service.authenticate(username, password);

		if(rr) {
			session.setAttribute("user", username);
			return "view1";
		} else {
			String captchaString = Login.generateCaptcha();
			m.addAttribute("captcha" , captchaString);
			return "view0";
		}

	}
}
