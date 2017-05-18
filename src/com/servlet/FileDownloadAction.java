package com.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FileDownloadAction
 */
@WebServlet("/FileDownloadAction")
public class FileDownloadAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String dataPath;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileDownloadAction() {
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
		//将字符集设置，放在方法体首位
        response.setCharacterEncoding("UTF-8"); 
        request.setCharacterEncoding("UTF-8");
		//处理请求
		//读取要下载的文件
		String theFileName = request.getParameter("filename");
		File f = new File(dataPath+theFileName);
		if(f.exists()){
			FileInputStream fis = new FileInputStream(f);
			String filename=URLEncoder.encode(f.getName(),"utf-8");
			byte[] b = new byte[fis.available()];
			fis.read(b);
			response.setHeader("Content-Disposition","attachment;filename="+filename+"");
			//获取响应报文输出流对象
			ServletOutputStream out = response.getOutputStream();
			out.write(b);
			out.flush();
			out.close();
			fis.close();
			System.out.println("下载成功");
		}else{
			System.out.println("下载失败");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
