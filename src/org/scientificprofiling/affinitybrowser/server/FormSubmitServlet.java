package org.scientificprofiling.affinitybrowser.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class FormSubmitServlet extends HttpServlet {

	private static final long serialVersionUID = -3009627427359109645L;
 
	public String emailForm(String formData) {
		Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
		try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("info@semanticprofiling.net", "Affinity Browser Evaluation"));
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress("laurensdv@gmail.com", "Laurens De Vocht"));
            msg.setSubject("Your Example.com account has been activated");
            msg.setText("Test email "+formData);
            Transport.send(msg);

        } catch (Exception e) {
        	System.out.println(e.getMessage());
        }
        return "Message sent";
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		response.setStatus(HttpServletResponse.SC_OK);
		PrintWriter out = response.getWriter();
		
		Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        
        String actions = request.getParameter("actions");
        String mail = request.getParameter("email");
        String name = request.getParameter("name");
        String nonRelevantUsers = request.getParameter("nonRelevantUsers");
        String relevantUsers = request.getParameter("relevantUsers");  
        String comments = request.getParameter("comments");
        String remarks = request.getParameter("remarks");

        String responseString = "";
        String urlParameters = "";
        
        try {
        	String url = new String();

    		urlParameters =
    	        "user=" + URLEncoder.encode(request.getParameter("user"), "UTF-8") +
    	        "&name=" + URLEncoder.encode(name, "UTF-8") +
    	        "&age=" + URLEncoder.encode(request.getParameter("age"), "UTF-8") +
    	        "&sex=" + URLEncoder.encode(request.getParameter("sex"), "UTF-8") +
    	        "&location=" + URLEncoder.encode(request.getParameter("location"), "UTF-8") +
    	        "&email=" + URLEncoder.encode(mail, "UTF-8") +
    	        "&company=" + URLEncoder.encode(request.getParameter("company"), "UTF-8") +
    	        "&q0=" + URLEncoder.encode(request.getParameter("q0"), "UTF-8") +
    	        "&q1=" + URLEncoder.encode(request.getParameter("q1"), "UTF-8") +
    	        "&q2=" + URLEncoder.encode(request.getParameter("q2"), "UTF-8") +
    	        "&q3=" + URLEncoder.encode(request.getParameter("q3"), "UTF-8") +
    	        "&q4=" + URLEncoder.encode(request.getParameter("q4"), "UTF-8") +
    	        "&q5=" + URLEncoder.encode(request.getParameter("q5"), "UTF-8") +
    	        "&q6=" + URLEncoder.encode(request.getParameter("q6"), "UTF-8") +
    	        "&q7=" + URLEncoder.encode(request.getParameter("q7"), "UTF-8") +
    	        "&q8=" + URLEncoder.encode(request.getParameter("q8"), "UTF-8") +
    	        "&q9=" + URLEncoder.encode(request.getParameter("q9"), "UTF-8") +
    	        "&q10=" + URLEncoder.encode(request.getParameter("q10"), "UTF-8") +
    	        "&q11=" + URLEncoder.encode(request.getParameter("q11"), "UTF-8") +
    	        "&q12=" + URLEncoder.encode(request.getParameter("q12"), "UTF-8") +
    	        "&remarks=" + URLEncoder.encode(remarks, "UTF-8") +
    	        "&comments=" + URLEncoder.encode(comments, "UTF-8") +
    	        "&nonRelevantUsers=" + URLEncoder.encode(nonRelevantUsers, "UTF-8") +
    	        "&nonRelCount=" + URLEncoder.encode(request.getParameter("nonRelevantUsersCount"), "UTF-8") +
    	        "&relevantUsers=" + URLEncoder.encode(relevantUsers, "UTF-8") +
    	        "&relCount=" + URLEncoder.encode(request.getParameter("relevantUsersCount"), "UTF-8") +
    	        "&actions=" + URLEncoder.encode(actions, "UTF-8") ;
        	
    		URL toGet = new URL("http://api.semanticprofiling.net/dump_result.php?"+urlParameters);

    		HttpURLConnection yc = (HttpURLConnection)toGet.openConnection();
    		yc.setRequestMethod("GET");
    		yc.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
    		yc.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
    		yc.setUseCaches(false);
    		yc.setConnectTimeout(0);
    		yc.setDoInput(true);
    		//yc.setDoOutput(true);
    	    
    		//Send request
    	   // DataOutputStream wr = new DataOutputStream (yc.getOutputStream());
    	    //wr.writeBytes (urlParameters);
    	    //wr.flush ();
    	    //wr.close ();
    	      
    	    BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
    	    String inputLine;

    	    while ((inputLine = in.readLine()) != null) 
    	        responseString+=inputLine;
    	    in.close();
    	    out.println(responseString);
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    		out.println(e.getMessage());
    		try {
    		Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("laurensdv@gmail.com", "Affinity Browser Evaluation Error"));
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress("laurens@semanticprofiling.net", "Laurens De Vocht"));
            
			msg.setSubject(name + " filled out your evaluaton form, but encountered error");
			
            msg.setText(e.getMessage()+e.getStackTrace()+"\n\n" + remarks+"\n\n" + comments+"\nActions: "+actions+"\n Non relevant users: "+nonRelevantUsers+"\n Relevant users: "+relevantUsers);
            Transport.send(msg);
    		} catch (MessagingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				out.println(e1.getMessage());
			}
    	}
    	
		try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("laurensdv@gmail.com", "Affinity Browser Evaluation"));
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress("laurens@semanticprofiling.net", "Laurens De Vocht"));
            msg.setSubject(request.getParameter("name") + " filled out your evaluaton form");
            msg.setText(remarks + "\n" + comments + "\n\n Actions: "+actions+ "\n\n Relevant users: "+relevantUsers+ "\n\n Non Relevant users: "+nonRelevantUsers + "\n\n" +urlParameters);
            Transport.send(msg);
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        	out.println(e.getMessage());
        }
        out.close();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		processRequest(request, response);
	}

}