package com.huateng.stl4j.action;

import java.io.File;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import om.util.OmUtils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.huateng.services.FileUploadService;

public class FileUploadAction extends BaseAction {
	private static Logger logger = Logger.getLogger(FileUploadAction.class);
	
	public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		super.init(request);
		String forward = "success";
		String message = "";
		InputStream is = null;
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 设置内存缓冲区，超过后写入临时文件
			factory.setSizeThreshold(10240000); 
			// 设置临时文件存储位置
			//String base = request.getSession().getServletContext().getRealPath("/") + "files";
			String base = "/temp";
			File file = new File(base);
			if(!file.exists())
				file.mkdirs();
			factory.setRepository(file);
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 设置单个文件的最大上传值
			upload.setFileSizeMax(10485760l);
			// 设置整个request的最大值
			upload.setSizeMax(10485760l);
			upload.setHeaderEncoding("UTF-8");
			
			List<?> items = upload.parseRequest(request);
			FileItem item = null;
			String fileName = null;
			String primaryKey = null;
			String bussType = null;
			for (int i = 0 ;i < items.size(); i++){
				item = (FileItem) items.get(i);
				if (!item.isFormField() && item.getName().length() > 0) { //文件
					//fileName = base + File.separator + item.getName();
					//System.out.println("fileName=" + fileName);
					//item.write(new File(fileName)); //写文件
					fileName = item.getName();
					is = item.getInputStream();
				}
				else if(item.isFormField() && "primaryKey".equals(item.getFieldName())) { //参数primaryKey
					primaryKey = item.getString();
					//System.out.println("primaryKey=" + primaryKey);
				}
				else if(item.isFormField() && "bussType".equals(item.getFieldName())) { //参数bussType
					bussType = item.getString();
					//System.out.println("bussType=" + bussType);
				}
			}
			if(OmUtils.isEmpty(message)) {
				FileUploadService.processUploadedFile(fileName, is, primaryKey, bussType);
			}
		} catch(Exception e) {
			logger.error("上传失败", e);
			message = e.getMessage();
		} finally {
			if(null != is) {
				try { is.close(); } catch(Exception e) {}
			}
		}
		request.setAttribute("MESSAGE", message);
		
		return mapping.findForward(forward);
	}
}
