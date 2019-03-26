package test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

;

public class GetData extends HttpServlet {

	public GetData() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doDelete(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doGet(req, resp);
		req.setCharacterEncoding("utf-8");
		System.out.println("birthplace:"+req.getParameter("birthplace"));
		System.out.println("username:"  +req.getParameter("username"));
		System.out.println("password:"  +req.getParameter("password"));
		System.out.println("username:"  +req.getParameter("username"));
		System.out.println("gender:"	   +req.getParameter("gender"));
		System.out.println("hobit:"	   +req.getParameter("hobit"));
		System.out.println("phone:"	   +req.getParameter("phone"));
		System.out.println("type:"	   +req.getParameter("type"));
		System.out.println("selfdesc:"  +req.getParameter("selfdesc"));
		System.out.println(req.getParameter("phone"));
		System.out.println(req.getParameter("phone"));
		System.out.println(req.getParameter("phone"));
		System.out.println(req.getParameter("phone"));
		
	}
	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doHead(req, resp);
	}

	@Override
	protected void doOptions(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doOptions(arg0, arg1);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPut(req, resp);
	}

	@Override
	protected void doTrace(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doTrace(arg0, arg1);
	}

	@Override
	protected long getLastModified(HttpServletRequest req) {
		// TODO Auto-generated method stub
		return super.getLastModified(req);
	}

	/*@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.service(arg0, arg1);
	}

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.service(req, res);
	}*/
	

}
