package com.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;

/**
 * Servlet implementation class FileUploadAction
 */
@WebServlet("/FileUploadAction")
public class FileUploadAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String dataPath;
    final static String Result_S = "1";
    final static String Result_F = "0";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileUploadAction() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init(ServletConfig config)throws ServletException{
    	super.init(config);
    	ServletContext servletContext = config.getServletContext();
    	dataPath = servletContext.getRealPath("/data/");
    	File savePath = new File(dataPath);
    	if(!savePath.exists()){
    		savePath.mkdirs();
    	}
    	System.out.println("path:"+dataPath);
    	System.out.println("**-- Servlet初始化 -->init()");
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("**-- Servlet doGet-->doGet()");
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("**-- Servlet doPost-->doPost()");
		request.setCharacterEncoding("UTF-8");
		//判断是否上传文件
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		System.out.println("isMultipart:"+isMultipart);
		InputStream input = null;
		OutputStream out = null;
		String fileName = null;
		if(isMultipart){
			try{
				FileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				//得到所有的表单域，它们目前都被当作FileItem
				//！！！上传文件必须要有commons-io和commons-fileupload两个包，而且必须放在lib下才能被编译
				List<FileItem> fileItems = upload.parseRequest(new ServletRequestContext(request));
				Iterator<FileItem> iter = fileItems.iterator();
				//依次处理每个表单域
				while(iter.hasNext()){
					FileItem item = (FileItem) iter.next();
					if(item.isFormField()){
						//如果是正常的表单域
						String name = item.getFieldName();
						String value = item.getString();
						System.out.println("表单域名为:"+name+" 表单域值为:"+value);
					}else{
						//如果item是文件上传表单域
						//获得文件名及路径
						fileName = item.getName();
						System.out.println(dataPath+fileName);
						File saveFile = new File(dataPath,fileName);
						System.out.println(saveFile.exists());
						input = item.getInputStream();
						out = new FileOutputStream(saveFile);
						int temp = 0;
						byte data[] = new byte[1024];
						while((temp=input.read(data,0,1024))!=-1){
							out.write(data);
						}
						System.out.println("Save "+fileName+" Successful!");
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				input.close();
				out.close();
			}
		}
		response.getWriter().write("{\"code\":\"200\",\"filename\":\""+fileName+"\"}");
	}
}
