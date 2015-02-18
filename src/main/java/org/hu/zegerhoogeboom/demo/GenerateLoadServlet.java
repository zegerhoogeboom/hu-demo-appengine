package org.hu.zegerhoogeboom.demo;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Zeger Hoogeboom
 */
@WebServlet(name = "GenerateLoadServlet")
public class GenerateLoadServlet extends HttpServlet
{
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		String guestbookName = req.getParameter("guestbookName");
		Key guestbookKey = KeyFactory.createKey("Guestbook", guestbookName);
		String content = req.getParameter("content");
		Date date = new Date();
		Iterable<Entity> greetings = new ArrayList<>();
		for (int i = 0; i <= 25; i++) {
			Entity greeting = new Entity("Greeting", guestbookKey);
			greeting.setProperty("user", user);
			greeting.setProperty("date", date);
			greeting.setProperty("content", String.format("Generated message %s",i));
			((ArrayList)greetings).add(greeting);
		}

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(greetings);

		resp.sendRedirect("/guestbook.jsp?guestbookName=" + guestbookName);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

	}
}
