package vttp2023.batch3.ssf.frontcontroller.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	public String postLoginCredentials(@Valid @ModelAttribute("login") Login login, BindingResult result, Model m, HttpSession session) throws Exception{
		
		if(result.hasErrors()){
            return "view0";
        }

		String username = login.getUsername();
		System.out.println("Frontcontoller username: " + username);
		String password = login.getPassword();
		System.out.println("Frontcontoller password: " + password);
		boolean authBool = service.authenticate(username, password);

		if(authBool) {
			//return secret page
			session.setAttribute("user", username);
			session.removeAttribute("attempts");
			return "view1";
		} else if ((int) session.getAttribute("attempts") < 4){
			//populate captcha
			String captchaString = login.getCaptchaString();
			int captchaAnswer = login.getCaptchaAnswer();
			m.addAttribute("captcha" , captchaString);
			session.setAttribute("captchaAnswer", captchaAnswer);
			
			//set attempts in session
			int attempts = (int) session.getAttribute("attempts");
			if(0 == attempts) {
				session.setAttribute("attempts", 1);
			} else {
				session.setAttribute("attempts", attempts+1);
			}

			return "view0";

		} else {

			//exceed attempts
			return "view2";

		}

	}
}
