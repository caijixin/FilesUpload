package com.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class deleteFileAction
 */
@WebServlet("/deleteFileAction")
public class deleteFileAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String dataPath;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public deleteFileAction() {
        super();
        // TODO Auto-generated constructor stub
    }
    public void init(ServletConfig config) throws ServletException{
    	super.init(config);
    	ServletContext servletContext = config.getServletContext();
    	dataPath = servletContext.getRealPath("/data/");
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//将字符集设置，放在方法体首位
        response.setCharacterEncoding("UTF-8"); 
        request.setCharacterEncoding("UTF-8");
		//处理请求
		//读取要下载的文件
		String theFileName = request.getParameter("filename");
		File f = new File(dataPath+theFileName);
		boolean flag = f.getAbsoluteFile().delete();
		if(flag)response.getWriter().write("{\"result\":\"success\"}");
		else response.getWriter().write("{\"result\":\"fail\"}");
	}

}
