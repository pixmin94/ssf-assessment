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
    public String showLandingPage(Model m, HttpSession session){
		session.removeAttribute("user");
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
			session.setAttribute("user", username); //save user in httpsession to check if logged in later
			session.removeAttribute("attempts");
			return "protected/view1";

		} else if (session.getAttribute("attempts") == null || (int) session.getAttribute("attempts") < 3){
			//validate captcha
			int cc = login.getUserResponse();
			login.validateCaptcha(login, cc);
			
			//populate captcha
			Login capLogin = Login.generateCaptcha();
			String captchaString = capLogin.getCaptchaString();
			m.addAttribute("captcha" , captchaString);
			
			
			//set attempts in session
			int attempts = 0;
			if (session.getAttribute("attempts") == null) {
				session.setAttribute("attempts", 1);
			} else {
				attempts = (int) session.getAttribute("attempts");
				session.setAttribute("attempts", attempts+1);
			}
			System.out.println(attempts);
			return "view0";

		} else {
			//exceed attempts
			service.disableUser(username);
			session.removeAttribute("attempts");
			return "view2";

		}

	}


}
