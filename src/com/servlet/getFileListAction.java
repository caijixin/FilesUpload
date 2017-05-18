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
 * Servlet implementation class getFileListAction
 */
@WebServlet("/getFileListAction")
public class getFileListAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String dataPath;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getFileListAction() {
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
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//将字符集设置，放在方法体首位
        response.setCharacterEncoding("UTF-8"); 
        request.setCharacterEncoding("UTF-8");
        //读取data文件夹
		File file = new File(dataPath);
		String responseJson = "{\"files\":[";
		String[] fileList = file.list();
		int len = fileList.length;
		for(int i=0;i<len;i++){
			System.out.println(fileList[i]);
			responseJson+="{\"filename\":\""+fileList[i]+"\"}";
			if(i!=len-1)responseJson+=",";
		}
		responseJson+="]}";
		response.getWriter().write(responseJson);
	}

}
