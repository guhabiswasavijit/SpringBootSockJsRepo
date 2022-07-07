package org.self.controller;

import java.util.Optional;

import javax.websocket.server.PathParam;

import org.self.model.User;
import org.self.request.CreateUserRequest;
import org.self.service.IUserService;
import org.self.service.SecurityService;
import org.self.util.CreateUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;
@Controller
@Slf4j
public class UserController {
	@Autowired
    private SecurityService securityService;
    @Autowired
    private UserValidator userValidator;
	private IUserService userService;

	@Autowired
	public UserController(IUserService userService) {
		this.userService = userService;
	}

	@PostMapping("/create")
	public CreateUserResponse createUser(@RequestBody CreateUserRequest request) {
		User newUser = new User();
		newUser.setEmail(request.getEmail());
		newUser.setEnabled(request.getEnabled());
		newUser.setPassword(request.getPassword());
		newUser.setUsername(request.getUsername());		
		User user = userService.createNewUser(newUser);
		CreateUserResponse response = new CreateUserResponse();
		response.setUsername(user.getUsername());
		return response;
	}


    @GetMapping("/registration")
    public String registration(Model model) {
    	log.info("Called registration Page");
        if (securityService.isAuthenticated()) {
            return "/";
        }
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        log.info("Called registration Page {}",user);
        User nUsr = userService.createNewUser(user);
        log.info("Called registration Page {}",nUsr.getId());
        securityService.autoLogin(nUsr.getUsername(),nUsr.getPasswordConfirm());
        return "welcome";
    }
    @GetMapping("/login")
    public ModelAndView loginPage(@PathParam("error") Boolean error,@PathParam("errorMsg") String errorMsg) {
    	log.info("Called login page {}",error);
    	ModelAndView model = new ModelAndView();
    	model.addObject("userForm", new User());
    	model.setViewName("login");
    	boolean erroring = Optional.ofNullable(error).isPresent();
    	if(erroring){
    		log.info("login error {}",errorMsg);
    		model.addObject("error",errorMsg);
    	}
    	log.info("First time login {}",erroring);
	    return model;
    }
    @GetMapping("/welcome")
    public ModelAndView welcome() {
    	log.info("Redirecting to home page");
    	ModelAndView model = new ModelAndView();
    	model.setViewName("welcome");
        return model;
    }
}

