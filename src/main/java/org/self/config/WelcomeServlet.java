package org.self.config;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

@WebServlet(name="WelcomeServlet", urlPatterns={"/welcome", "/"}) 
public class WelcomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		TemplateEngine engine = new TemplateEngine();
		WebContext context = new WebContext(request, response, request.getServletContext());
		context.setVariable("recipient", "World");
		engine.process("welcome.html", context, response.getWriter());
	}

}
