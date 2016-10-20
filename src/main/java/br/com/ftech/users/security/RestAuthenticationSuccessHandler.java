package br.com.ftech.users.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class RestAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		clearAuthenticationAttributes(request);
		
		/*response.setStatus(HttpServletResponse.SC_OK);
		UserLogged userDetails = (UserLogged) authentication.getPrincipal();
		PrintWriter writer = response.getWriter();
		mapper.writeValue(writer, userDetails);
		writer.flush(); */
	}
}