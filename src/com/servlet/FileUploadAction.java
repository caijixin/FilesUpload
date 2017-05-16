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
    	System.out.println("**-- Servlet��ʼ�� -->init()");
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
		//�ж��Ƿ��ϴ��ļ�
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		System.out.println("isMultipart:"+isMultipart);
		InputStream input = null;
		OutputStream out = null;
		String fileName = null;
		if(isMultipart){
			try{
				FileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				//�õ����еı�������Ŀǰ��������FileItem
				//�������ϴ��ļ�����Ҫ��commons-io��commons-fileupload�����������ұ������lib�²��ܱ�����
				List<FileItem> fileItems = upload.parseRequest(new ServletRequestContext(request));
				Iterator<FileItem> iter = fileItems.iterator();
				//���δ���ÿ������
				while(iter.hasNext()){
					FileItem item = (FileItem) iter.next();
					if(item.isFormField()){
						//����������ı���
						String name = item.getFieldName();
						String value = item.getString();
						System.out.println("������Ϊ:"+name+" ����ֵΪ:"+value);
					}else{
						//���item���ļ��ϴ�����
						//����ļ�����·��
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
