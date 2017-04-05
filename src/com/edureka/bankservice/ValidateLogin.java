package com.edureka.bankservice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ValidateLogin")
public class ValidateLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String FILENAME = "/home/edureka/DevOpsCertificationProject/src/Login.txt";
    public ValidateLogin() {
        super();
        }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//extracting the login id and password from the form 
		String loginId=request.getParameter("username");
		String password=request.getParameter("password");
		
		//reading user id(account) from the file and validating 
		BufferedReader br = null;
		int flag=-1;
		FileReader fr = null;
		try 
		{
			fr = new FileReader(FILENAME);
			br = new BufferedReader(fr);
			String sCurrentLine;
			br = new BufferedReader(new FileReader(FILENAME));

			while ((sCurrentLine = br.readLine()) != null) 
			{
				//splitting the line into words and matching the userid and password
				String []logindata=sCurrentLine.split(",");
				if(logindata[0].equals(loginId) && logindata[1].equals(password))
				{
					HttpSession session=request.getSession();
					 session.setAttribute("account_number",loginId);
					flag=1;
					break;
				}
			}
			//control will come down here after break
			if(flag==1)
			{
				RequestDispatcher rd = request.getRequestDispatcher("GetAccountAndBalance");
		        rd.forward(request, response);
			}
			else
			{
				RequestDispatcher rd = request.getRequestDispatcher("Home.html");
		        rd.forward(request, response);
			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}
		
		
	}

}
