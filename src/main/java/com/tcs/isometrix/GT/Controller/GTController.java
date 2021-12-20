package com.tcs.isometrix.GT.Controller;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.codec.binary.Base64;

import com.tcs.isometrix.GT.GtApplication;
import com.tcs.isometrix.GT.Bean.GTBean;
import com.tcs.isometrix.GT.Bean.ReponseData;
import com.tcs.isometrix.GT.Service.GTService;
import org.apache.commons.codec.binary.StringUtils;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class GTController {
	
	private static final Logger log = LogManager.getLogger(GtApplication.class.getName());
	
	@Autowired
	GTService ser;
	
	 @GetMapping({"/"})
	 public String indexPage() {
		 log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		 return "/html/homepage.html";
	 }
	 
	 @GetMapping({"/Form"})
	 public String getSearch() {
		 log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		 return "/html/FormData.html";
	 }
	 @GetMapping({"/FormOld"})
	 public String getoldForm() {
		 log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		 return "/html/form.html";
	 }
	
	 
	 @GetMapping({"/Dashboard"})
	 public String getDashBoard() {
		 log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		 return "/html/Dashboard.html";
	 }

	 
	@RequestMapping("/checkConn")
	public @ResponseBody String checkConn() {
		String res="";
		try {
			res =ser.checkConn();
		}
		catch(Exception e) {
			e.printStackTrace();
			res= "error";
		}
		
		return res;
		
	}
	
	
	@PostMapping("/saveDetials")
	public @ResponseBody String saveDetials(@ModelAttribute GTBean form) {
		
		 log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		GTBean bean = null;
		String res="";
		try {
			bean = (GTBean) form;

			res = ser.saveDetials(bean);
		}
		catch(Exception e) {
			e.printStackTrace();
			res="error";
		 	log.error(this.getClass().getName() + " - Exception", e);
		}
		
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		return res;
	}
	
	
	@PostMapping("/getDetails")
	synchronized public @ResponseBody List<List<List<String>>> getDetails(@ModelAttribute GTBean form) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		GTBean bean = null;
		List<List<List<String>>> data = null;  // = new ArrayList<List<List<String>>>();
		try {
			bean = (GTBean) form;
			
			data=ser.getDetails(bean);
			
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error(this.getClass().getName() + " - Exception", e);
		}
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		return data;
	}
	
	@PostMapping("/getTrendData")
	public @ResponseBody List<List<List<String>>> getTrendData(@ModelAttribute GTBean form){
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		GTBean bean = null;
		List<List<List<String>>> data = null;  // = new ArrayList<List<List<String>>>();
		try {
			bean = (GTBean) form;
			
			data=ser.getTrendData(bean);
			
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error(this.getClass().getName() + " - Exception", e);
		}
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		return data;
	}
	
	@PostMapping("/downloadreport")
	public void downloadreport(HttpServletRequest request, HttpServletResponse response,@ModelAttribute GTBean form) {
		 log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		GTBean bean = null;
		Workbook wb =null;
		try {
			wb=new XSSFWorkbook();
			bean = (GTBean) form;
			wb=ser.downloadreport(bean);
			System.out.println("returned from service");
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    		response.addHeader("content-disposition", "attachment; filename=Vaccination Report.xlsx"); //filename=WorkingHoursReport.xlsx
			wb.write(response.getOutputStream());
			wb.close();
			System.out.println("the end");
		}
		catch(Exception e) {
			log.error(this.getClass().getName() + " - Exception", e);
			e.printStackTrace();
		}
		 log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
	}
	

	
	@PostMapping("/checkEmp")
	public @ResponseBody String checkEmp(@ModelAttribute GTBean form) {
		
		 log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		GTBean bean = null;
		String res="";
		try {
			bean = (GTBean) form;

			res = ser.checkEmp(bean);
			//   res = (res.equalsIgnoreCase("Y")) ? "pass" : "fail";
		}
		catch(Exception e) {
			e.printStackTrace();
			res="error";
		 	log.error(this.getClass().getName() + " - Exception", e);
		}
		
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		return res;
	}
	@PostMapping("/generateToken")
	synchronized public @ResponseBody String generateToken(@ModelAttribute GTBean form) {
		
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		GTBean bean = null;
		String res="";
		try {
			bean = (GTBean) form;
			
			res = ser.generateToken(bean);
			//   res = (res.equalsIgnoreCase("Y")) ? "pass" : "fail";
		}
		catch(Exception e) {
			e.printStackTrace();
			res="error";
			log.error(this.getClass().getName() + " - Exception", e);
		}
		
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		return res;
	}
	@PostMapping("/getvaccinationLocation")
	public @ResponseBody List<List<List<String>>> getvaccinationLocation(@ModelAttribute GTBean form) {
		
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		GTBean bean = null;
		List<List<List<String>>> res=null;
		try {
			bean = (GTBean) form;
			
			res = ser.getvaccinationLocation(bean);
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error(this.getClass().getName() + " - Exception", e);
		}
		
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		return res;
	}
	@PostMapping("/getDasboardAccess")
	public @ResponseBody ReponseData getDasboardAccess(@ModelAttribute GTBean form, @ModelAttribute ReponseData res) {
		
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		GTBean bean = null;
		//List<List<List<String>>> res=null;
		try {
			bean = (GTBean) form;
			
			res = ser.getDasboardAccess(bean,res);
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error(this.getClass().getName() + " - Exception", e);
		}
		
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		return res;
	}
	@PostMapping("/getFormFields")
	public @ResponseBody List<List<List<String>>> getFormFields(@ModelAttribute GTBean form) {
		
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		GTBean bean = null;
		List<List<List<String>>> res=null;
		try {
			bean = (GTBean) form;
			
			res = ser.getFormFields(bean);
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error(this.getClass().getName() + " - Exception", e);
		}
		
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		return res;
	}
	@PostMapping("/getCategoryDrop")
	public @ResponseBody List<List<List<String>>> getCategoryDrop(@ModelAttribute GTBean form) {
		
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		GTBean bean = null;
		List<List<List<String>>> res=null;
		try {
			bean = (GTBean) form;
			
			res = ser.getCategoryDrop(bean);
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error(this.getClass().getName() + " - Exception", e);
		}
		
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		return res;
	}
	
	
	@PostMapping("/getSubmissionData")
	public @ResponseBody List<List<List<String>>> getSubmissionData(@ModelAttribute GTBean form) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		GTBean bean = null;
		List<List<List<String>>> data = null;  // = new ArrayList<List<List<String>>>();
		try {
			bean = (GTBean) form;
			
			data=ser.getSubmissionData(bean);
			
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error(this.getClass().getName() + " - Exception", e);
		}
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		return data;
	}

	
	@PostMapping("/sumbitStatus")
	public @ResponseBody String sumbitStatus(@ModelAttribute GTBean form) {
		
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		GTBean bean = null;
		String res="";
		try {
			bean = (GTBean) form;
			
			res = ser.sumbitStatus(bean);
			//   res = (res.equalsIgnoreCase("Y")) ? "pass" : "fail";
		}
		catch(Exception e) {
			e.printStackTrace();
			res="error";
			log.error(this.getClass().getName() + " - Exception", e);
		}
		
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		return res;
	}
	
	
	
	@PostMapping("/getDriveDetails")
	public @ResponseBody List<List<List<String>>> getDriveDetails(@ModelAttribute GTBean form) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		GTBean bean = null;
		List<List<List<String>>> data = null;  // = new ArrayList<List<List<String>>>();
		try {
			bean = (GTBean) form;
			
			data=ser.getDriveDetails(bean);
			
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error(this.getClass().getName() + " - Exception", e);
		}
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		return data;
	}
	
	
	
	@PostMapping("/formSubmit")
	public @ResponseBody String formSubmit(@ModelAttribute GTBean form) {
		
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		GTBean bean = null;
		String res="";
		try {
			bean = (GTBean) form;
			
			res = ser.formSubmit(bean);
			//   res = (res.equalsIgnoreCase("Y")) ? "pass" : "fail";
		}
		catch(Exception e) {
			e.printStackTrace();
			res="error";
			log.error(this.getClass().getName() + " - Exception", e);
		}
		
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		return res;
	}
	
	@PostMapping("/addMember")
	public @ResponseBody String addMember(@ModelAttribute GTBean form) {
		
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		GTBean bean = null;
		String res="";
		try {
			bean = (GTBean) form;
			
			res = ser.addMember(bean);
			//   res = (res.equalsIgnoreCase("Y")) ? "pass" : "fail";
		}
		catch(Exception e) {
			e.printStackTrace();
			res="error";
			log.error(this.getClass().getName() + " - Exception", e);
		}
		
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		return res;
	}
	
	@PostMapping("/saveVendor")
	public @ResponseBody String saveVendor(@ModelAttribute GTBean form) {
		
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		GTBean bean = null;
		String res="";
		try {
			bean = (GTBean) form;
			
			res = ser.saveVendor(bean);
			//   res = (res.equalsIgnoreCase("Y")) ? "pass" : "fail";
		}
		catch(Exception e) {
			e.printStackTrace();
			res="error";
			log.error(this.getClass().getName() + " - Exception", e);
		}
		
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		return res;
	}
	
	
	@PostMapping("/processSubmit")
	public @ResponseBody String processSubmit(@ModelAttribute GTBean form) {
		
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		GTBean bean = null;
		String res="";
		try {
			bean = (GTBean) form;
			
			res = ser.processSubmit(bean);
		}
		catch(Exception e) {
			e.printStackTrace();
			res="error";
			log.error(this.getClass().getName() + " - Exception", e);
		}
		
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		return res;
	}
	



@PostMapping("/getLiveDashBoardData")
 public @ResponseBody List<List<List<String>>> getLiveDashBoardData(@ModelAttribute GTBean form) {
	log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
	GTBean bean = null;
	List<List<List<String>>> data = null;  // = new ArrayList<List<List<String>>>();
	try {
		bean = (GTBean) form;
		
		data=ser.getLiveDashBoardData(bean);
		
	}
	catch(Exception e) {
		e.printStackTrace();
		log.error(this.getClass().getName() + " - Exception", e);
	}
	log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
	return data;
}

@PostMapping("/getCardsData")
public @ResponseBody List<List<List<String>>> getCardsData(@ModelAttribute GTBean form) {
	log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
	GTBean bean = null;
	List<List<List<String>>> data = null;  // = new ArrayList<List<List<String>>>();
	try {
		bean = (GTBean) form;
		
		data=ser.getCardsData(bean);
		
	}
	catch(Exception e) {
		e.printStackTrace();
		log.error(this.getClass().getName() + " - Exception", e);
	}
	log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
	return data;
}



@PostMapping("/CVCDownload")
private void CVCDownload(HttpServletRequest request, HttpServletResponse response,@RequestParam("fileID") String fileid,@RequestParam("datedata") String datedata,@RequestParam("todate") String todate,@ModelAttribute GTBean bean) {
	HttpSession session = request.getSession();
	String filename;
	GTBean form = null;
	//Logger log = Logger.getRootLogger();
	
	

	log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
	form = (GTBean) bean;
	XSSFWorkbook workbook = null;

	try {
		workbook =new XSSFWorkbook();
			
			workbook = ser.CVCDownload(form,datedata,todate);
		
		response.setContentType("application/vnd.ms-excel");
    		response.addHeader("content-disposition", "attachment; filename=CVC_Report.xlsx"); //filename=WorkingHoursReport.xlsx
			workbook.write(response.getOutputStream());
			workbook.close();

	}

	catch (Exception e) {
		e.printStackTrace();
		log.error(this.getClass().getName() + " - Exception", e);

		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");

	}
}

@PostMapping("/reconReportDownload")
private void reconReportDownload(HttpServletRequest request, HttpServletResponse response,@RequestParam("fileID") String fileid,@RequestParam("spocID") String spocid,@RequestParam("datedata") String datedata,@RequestParam("todate") String todate,@ModelAttribute GTBean bean) throws IOException, SQLException {
	HttpSession session = request.getSession();
	String filename;
	GTBean form = null;
	//Logger log = Logger.getRootLogger();
	
	

	log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
	form = (GTBean) bean;
	XSSFWorkbook workbook =null;

	try {
		workbook = new XSSFWorkbook();
		workbook = ser.reconReportDownload(form,datedata,todate,spocid);
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    		response.addHeader("content-disposition", "attachment; filename=Recon_Report.xlsx"); //filename=WorkingHoursReport.xlsx
			workbook.write(response.getOutputStream());
			workbook.close();

	}

	catch (IndexOutOfBoundsException e) {
		e.printStackTrace();
		log.error(this.getClass().getName() + " - Exception", e);

		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");

	}
}
@PostMapping("/turnoutReportDownload")
private void turnoutReportDownload(HttpServletRequest request, HttpServletResponse response,@RequestParam("fileID") String fileid,@RequestParam("spocID") String spocid,@RequestParam("datedata") String datedata,@RequestParam("todate") String todate,@ModelAttribute GTBean bean) throws IOException, SQLException {
	//HttpSession session = request.getSession();
	//String filename;
	GTBean form = null;
	//Logger log = Logger.getRootLogger();
	
	

	log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
	form = (GTBean) bean;
	XSSFWorkbook workbook =null;

	try {
		workbook = new XSSFWorkbook();
		workbook = ser.turnoutReportDownload(form,datedata,todate,spocid);
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    		response.addHeader("content-disposition", "attachment; filename=Turnout_Report.xlsx"); 
			workbook.write(response.getOutputStream());
			workbook.close();

	}

	catch (IndexOutOfBoundsException e) {
		e.printStackTrace();
		log.error(this.getClass().getName() + " - Exception", e);

		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");

	}
}
@PostMapping("/trendReportDownload")
private void trendReportDownload(HttpServletRequest request, HttpServletResponse response,@RequestParam("fileID") String fileid,@RequestParam("spocID") String spocid,@RequestParam("datedata") String datedata,@RequestParam("todate") String todate,@RequestParam("tcity") String tcity,@ModelAttribute GTBean bean) throws IOException, SQLException {
	//HttpSession session = request.getSession();
	//String filename;
	GTBean form = null;
	//Logger log = Logger.getRootLogger();
	
	

	log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
	form = (GTBean) bean;
	XSSFWorkbook workbook =null;

	try {
		workbook = new XSSFWorkbook();
		workbook = ser.trendReportDownload(form,datedata,todate,spocid,tcity);
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    		response.addHeader("content-disposition", "attachment; filename=Trend_Report.xlsx"); 
			workbook.write(response.getOutputStream());
			workbook.close();

	}

	catch (IndexOutOfBoundsException e) {
		e.printStackTrace();
		log.error(this.getClass().getName() + " - Exception", e);

		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");

	}
}
 @PostMapping("/uploadschedule") 
 public @ResponseBody String excelUpload(HttpServletRequest request, HttpServletResponse response,@RequestParam("filedata") MultipartFile files,@RequestParam("spocID") String spocid,@ModelAttribute GTBean bean ,@ModelAttribute ReponseData reponsedata) throws IOException, SQLException{
				if(log.isInfoEnabled())
			log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"-uploadexcel Entering");
		List<Object> objectsList = null;
		//List<com.tcs.isometrix.GT.RowData> cellDataList1 = null;
		//List<List<com.tcs.isometrix.GT.RowData>> masterDataList = null;
		List<List<com.tcs.isometrix.GT.RowData>> cellDataList = null;
		
		String flag="";
		try {
			 byte[] b = files.getBytes();
	 			
				ByteArrayInputStream byteArr = new ByteArrayInputStream(b);
		
		        Workbook workBook = WorkbookFactory.create(byteArr);
					XSSFWorkbook errorWorkbook = null;
						objectsList=ser.ListUploadcheck(workBook,b,bean);
						if (objectsList.indexOf("H") != -1
								|| objectsList.indexOf("BE") != -1
								
								
								) {
		                 //Header check
							if (objectsList.indexOf("H") != -1) {
								reponsedata.setFlag1("H");
								flag="H";
								reponsedata.setFlag2(objectsList.get(1).toString());
							} 
							//Empty cell check
							
							else if (objectsList.indexOf("BE") != -1)
							{
							reponsedata.setFlag1("BE");
							flag="BE";
							}
							
						} 
						
					
						else
						{
							
						cellDataList = (List<List<com.tcs.isometrix.GT.RowData>>) objectsList.get(0);
						
						reponsedata = ser.insertStagingAndTarget(cellDataList,bean,errorWorkbook,reponsedata);
						reponsedata.setFlag1(reponsedata.getFlag1());
						flag=reponsedata.getFlag1();
						if(reponsedata.getFlag1().equalsIgnoreCase("N")) {
							
							 errorWorkbook = reponsedata.getErrorWorkBook();
							/* response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
					    		response.addHeader("content-disposition", "attachment; filename=Error_Report.xlsx"); 
					    		errorWorkbook .write(response.getOutputStream());
					    		errorWorkbook .close(); */
							 
							 
							/*ByteArrayOutputStream baos = new ByteArrayOutputStream();
							 errorWorkbook.write(baos);   
							byte[] ba = baos.toByteArray();*/
							 File theDir = new File("//E:/VaccinationTracker/"+spocid+"/");
								if (!theDir.exists()){
								    theDir.mkdirs();
								}
							File file = new File("//E:/VaccinationTracker/"+spocid+"/ErrorReport.xlsx");
							FileOutputStream out = new FileOutputStream(file);
							errorWorkbook.write(out);
							//out.write();;
							out.flush();
							out.close();
							errorWorkbook.close();
							
						}
						
						}
					
				//	}
					
				
					
		   } catch (IOException e) {
			
			   e.printStackTrace();
				log.error(this.getClass().getName() + " - Exception", e);
		}
		   catch (Exception e) {
			  
				
			   e.printStackTrace();
				log.error(this.getClass().getName() + " - Exception", e);
			}
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		return flag;
		
	}
	
 @PostMapping("/errorReportDownload")
	public void downloadErrorreport(HttpServletRequest request, HttpServletResponse response,@RequestParam("spocID") String spocid,@ModelAttribute GTBean form) {
		 log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		// HttpSession session = request.getSession(false);
			
		try {
			//XSSFWorkbook workbook = null;
			//workbook = (XSSFWorkbook) session.getAttribute("error_report");
			
			File file = new File("//E:/VaccinationTracker/"+spocid+"/ErrorReport.xlsx");
			
			byte[] bytes = new byte[(int) file.length()];
			FileInputStream fis = null;
			fis = new FileInputStream(file); //read file into bytes[] 
			fis.read(bytes); 
			
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.addHeader("content-disposition", "attachment; filename=ErrorReport.xlsx"); 
			OutputStream outputStream = null;
			 outputStream = response.getOutputStream();
	            outputStream.write(bytes, 0, bytes.length);
	            outputStream.flush();
	            outputStream.close();
	            response.flushBuffer();
	            if(file.delete()) {
	            System.out.println("deleted");
	            	
	            }else {
	            	System.out.println("not deleted");
	            }

		}
		catch(Exception e) {
			log.error(this.getClass().getName() + " - Exception", e);
			e.printStackTrace();
		}
		 log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		 
	}

@PostMapping("/onclickdashboardcard")
public @ResponseBody List<List<List<String>>> onclickdashboardcard(@ModelAttribute GTBean form) {
	log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
	GTBean bean = null;
	List<List<List<String>>> data = null;  // = new ArrayList<List<List<String>>>();
	try {
		bean = (GTBean) form;
		
		data=ser.onclickdashboardcard(bean);
		
	}
	catch(Exception e) {
		e.printStackTrace();
		log.error(this.getClass().getName() + " - Exception", e);
	}
	log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
	return data;
}
@PostMapping("/getGridData")
public @ResponseBody List<List<List<String>>> getGridData(@ModelAttribute GTBean form) {
	
	log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
	GTBean bean = null;
	List<List<List<String>>> res = null;
	try {
		bean = (GTBean) form;
		res = ser.getGridData(bean);
	}
	catch(Exception e) {
		e.printStackTrace();
	 	log.error(this.getClass().getName() + " - Exception", e);
	}
	
	log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
	return res;
}

}
