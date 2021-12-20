package com.tcs.isometrix.GT.Service;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.chart.AxisCrosses;
import org.apache.poi.xddf.usermodel.chart.AxisPosition;
import org.apache.poi.xddf.usermodel.chart.BarDirection;
import org.apache.poi.xddf.usermodel.chart.ChartTypes;
import org.apache.poi.xddf.usermodel.chart.LegendPosition;
import org.apache.poi.xddf.usermodel.chart.MarkerStyle;
import org.apache.poi.xddf.usermodel.chart.XDDFBarChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFCategoryAxis;
import org.apache.poi.xddf.usermodel.chart.XDDFChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFChartLegend;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFDataSourcesFactory;
import org.apache.poi.xddf.usermodel.chart.XDDFDateAxis;
import org.apache.poi.xddf.usermodel.chart.XDDFLineChartData;
import org.apache.poi.xddf.usermodel.chart.XDDFNumericalDataSource;
import org.apache.poi.xddf.usermodel.chart.XDDFValueAxis;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.isometrix.GT.GtApplication;
import com.tcs.isometrix.GT.RowData;
import com.tcs.isometrix.GT.Bean.GTBean;
import com.tcs.isometrix.GT.Bean.ReponseData;
import com.tcs.isometrix.GT.Repo.GTRepo;

import javassist.bytecode.Descriptor.Iterator;



@Service
public class GTService {
	@Autowired
	GTRepo repo;
	
	private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

	private static final Logger log = LogManager.getLogger(GtApplication.class.getName());
	
	public String checkConn() {
	String res=null;
	try {
		res =repo.checkConn();
	}
	catch(Exception e) {
		res= "error";
	}
		return res;
	}


	public String saveDetials(GTBean bean) {
		String res=null;
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		try {
			res = repo.saveDetials(bean);
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error(this.getClass().getName() + " - Exception", e);
		}
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		return res;
	}
	
	synchronized public List<List<List<String>>> getDetails(GTBean bean) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		List<List<List<String>>> data= null;
		try {
			data = repo.getDetails(bean);
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error(this.getClass().getName() + " - Exception", e);
		}
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		return data;
	}
	 public List<List<List<String>>> getLiveDashBoardData(GTBean bean) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		List<List<List<String>>> data= null;
		try {
			data = repo.getLiveDashBoardData(bean);
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error(this.getClass().getName() + " - Exception", e);
		}
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		return data;
	}
	 public List<List<List<String>>> getCardsData(GTBean bean) {
		 log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		 List<List<List<String>>> data= null;
		 try {
			 data = repo.getCardsData(bean);
		 }
		 catch(Exception e) {
			 e.printStackTrace();
			 log.error(this.getClass().getName() + " - Exception", e);
		 }
		 log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		 return data;
	 }
	public List<List<List<String>>> getDriveDetails(GTBean bean) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		List<List<List<String>>> data= null;
		try {
			data = repo.getDriveDetails(bean);
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error(this.getClass().getName() + " - Exception", e);
		}
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		return data;
	}
	public List<List<List<String>>> getSubmissionData(GTBean bean) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		List<List<List<String>>> data= null;
		try {
			data = repo.getSubmissionData(bean);
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error(this.getClass().getName() + " - Exception", e);
		}
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		return data;
	}
	
	
	
	public Workbook downloadreport(GTBean bean) {

		List<List<List<String>>> data=null;
		Workbook wb=null;
		try {
			data =repo.downloadreport(bean);
			
			if(!data.isEmpty()) {
				
					wb = new XSSFWorkbook();
				 	Font headerFont = wb.createFont();
			        headerFont.setBold(true);
			        headerFont.setFontHeightInPoints((short) 12);
			        headerFont.setColor(IndexedColors.WHITE.getIndex());
				 
				 	CellStyle headerCellStyle = wb.createCellStyle();
			        headerCellStyle.setFont(headerFont);
			        // fill foreground color ...
			        headerCellStyle.setFillForegroundColor(IndexedColors.SEA_GREEN .index); //OLIVE_GREEN SEA_GREEN 
			        // and solid fill pattern produces solid grey cell fill
			        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
			        headerCellStyle.setFont(headerFont);
			
				
				List<List<String>> header =data.get(0);
				XSSFSheet sheet= (XSSFSheet) wb.createSheet("Live Vaccination Report");
				XSSFRow row =sheet.createRow(0);
				XSSFCell cell=null;
				for(int i=0;i<header.size();i++) {
					cell =row.createCell(i);
					
					
					
					cell.setCellValue(header.get(i).get(0));
					cell.setCellStyle(headerCellStyle);
				}
				header.clear();
				
				
				CellStyle CellStyle = wb.createCellStyle();
				CellStyle.setAlignment(HorizontalAlignment.CENTER);
				List<List<String>> values =data.get(1);
				
				int v =1;
				for(int i=0;i<values.size();i++) {
					row =sheet.createRow(v+i);
					for(int j=0;j<values.get(i).size();j++) {
						cell=row.createCell(j);
						
						String val =values.get(i).get(j);
						if(val.contains("^")) {
							//val.split(("^")
							//System.out.println(val.split("\\^")[1]);
							XSSFCellStyle sty=setCellStyles(wb,val.split("\\^")[1],"252,252,252");
							try{val =val.split("\\^")[0];}
							catch(Exception e) {
								val="";
							}
							/*if(isNumeric(val)) {
								try{cell.setCellValue(Integer.parseInt(val));cell.setCellStyle(sty);}catch(Exception e1) {cell.setCellValue(Double.parseDouble(val));}
							}
							else {
								cell.setCellValue(val);
								cell.setCellStyle(sty);
							}*/
							cell.setCellValue(val);
							cell.setCellStyle(sty);
						}
						else {
							/*if(isNumeric(val)) {
								try{cell.setCellValue(Integer.parseInt(val));}catch(Exception e1) {cell.setCellValue(Double.parseDouble(val));}
							}
							else {
								cell.setCellValue(val);
							}*/
							cell.setCellValue(val);
							cell.setCellStyle(CellStyle);
						}
						
						sheet.autoSizeColumn(cell.getColumnIndex());
					}
				}
				values.clear();
				data.clear();
				
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return wb;
	}
	
	
	public boolean isNumeric(String strNum) {
		
	    if (strNum == null) {
	        return false; 
	    }
	    return pattern.matcher(strNum.trim()).matches();
	}

	
	public String checkEmp(GTBean bean) {
		String res=null;
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		try {
			res = repo.checkEmp(bean);
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error(this.getClass().getName() + " - Exception", e);
		}
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		return res;
	}
	synchronized public String generateToken(GTBean bean) {
		String res=null;
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		try {
			res = repo.generateToken(bean);
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error(this.getClass().getName() + " - Exception", e);
		}
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		return res;
	}
	public String sumbitStatus(GTBean bean) {
		String res=null;
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		try {
			res = repo.sumbitStatus(bean);
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error(this.getClass().getName() + " - Exception", e);
		}
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		return res;
	}
	public String formSubmit(GTBean bean) {
		String res=null;
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		try {
			res = repo.formSubmit(bean);
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error(this.getClass().getName() + " - Exception", e);
		}
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		return res;
	}
	public String addMember(GTBean bean) {
		String res=null;
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		try {
			res = repo.addMember(bean);
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error(this.getClass().getName() + " - Exception", e);
		}
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		return res;
	}
	public String saveVendor(GTBean bean) {
		String res=null;
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		try {
			res = repo.saveVendor(bean);
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error(this.getClass().getName() + " - Exception", e);
		}
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		return res;
	}
	public String processSubmit(GTBean bean) {
		String res=null;
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		try {
			res = repo.processSubmit(bean);
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error(this.getClass().getName() + " - Exception", e);
		}
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		return res;
	}
	public List<List<List<String>>> getvaccinationLocation(GTBean bean) {
		List<List<List<String>>> res=null;
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		try {
			res = repo.getvaccinationLocation(bean);
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error(this.getClass().getName() + " - Exception", e);
		}
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		return res;
	}
	public ReponseData getDasboardAccess(GTBean bean, ReponseData res) {
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		try {
			res = repo.getDasboardAccess(bean,res);
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error(this.getClass().getName() + " - Exception", e);
		}
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		return res;
	}
	public List<List<List<String>>> getFormFields(GTBean bean) {
		List<List<List<String>>> res=null;
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		try {
			res = repo.getFormFields(bean);
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error(this.getClass().getName() + " - Exception", e);
		}
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		return res;
	}
	public List<List<List<String>>> getCategoryDrop(GTBean bean) {
		List<List<List<String>>> res=null;
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		try {
			res = repo.getCategoryDrop(bean);
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error(this.getClass().getName() + " - Exception", e);
		}
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		return res;
	}
	



public XSSFCellStyle setCellStyles(Workbook wb, String bg, String clr) {
	
	XSSFFont headerFont = (XSSFFont) wb.createFont();
    headerFont.setBold(true);
    headerFont.setFontHeightInPoints((short) 12);
    int[] arr =getRGB(clr);
    //System.out.println(arr);
    XSSFColor myColor = new XSSFColor(new java.awt.Color(arr[0],arr[1],arr[2]), new DefaultIndexedColorMap()); // 
    headerFont.setColor(myColor);
 
    XSSFCellStyle headerCellStyle =(XSSFCellStyle) wb.createCellStyle();
   // headerCellStyle.setFont(headerFont);
    // fill foreground color ...
    arr =null;
     arr =getRGB(bg);
    //System.out.println(arr);
    XSSFColor grey = new XSSFColor(new java.awt.Color(arr[0],arr[1],arr[2]), new DefaultIndexedColorMap());
    headerCellStyle.setFillForegroundColor(grey);
    // and solid fill pattern produces solid grey cell fill
    headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
    //headerCellStyle.setFont(headerFont);
	
    return headerCellStyle;
    }
public int[] getRGB(String str) {
	
	String[] arr=str.split(",");
	int[] array = Arrays.stream(arr).mapToInt(Integer::parseInt).toArray();
	
	return array;
	
}


public XSSFWorkbook  CVCDownload(GTBean bean,String datedata,String todate) {

	
	XSSFWorkbook workbook =new XSSFWorkbook();
	XSSFSheet sheet1 = null;
	
		//sheet1 = workbook.createSheet("CVC_Report");
	
	XSSFRow row = null;
	XSSFRow row1=null;
	int count=0;
	int repeatcount=0;
	List<List<List<String>>> finalList = null;
	
	try {
		String[] sheetNames = {"CVC1","CVC2"};
		for(short a=0;a<sheetNames.length;a++) {
			count = 0;
			finalList = repo. CVCDownload(bean,sheetNames[a],datedata,todate);
		sheet1 = workbook.createSheet(sheetNames[a]);
		
		//finalList = repo. CVCDownload(bean,datedata,todate);

		if(!finalList.isEmpty()) {
			
			//wb = new XSSFWorkbook();
		 	Font headerFont = workbook.createFont();
	        headerFont.setBold(true);
	        headerFont.setFontHeightInPoints((short) 12);
	        headerFont.setColor(IndexedColors.WHITE.getIndex());
		 
		 	CellStyle headerCellStyle = workbook.createCellStyle();
	        headerCellStyle.setFont(headerFont);
	        // fill foreground color ...
	        headerCellStyle.setFillForegroundColor(IndexedColors.SEA_GREEN .index); //OLIVE_GREEN SEA_GREEN 
	        // and solid fill pattern produces solid grey cell fill
	        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
	        headerCellStyle.setFont(headerFont);
	//Font valueFont = workbook.createFont();
	CellStyle CellStyle = workbook.createCellStyle();
	CellStyle.setAlignment(HorizontalAlignment.CENTER);
	
		List<List<String>> header =finalList.get(0);
		List<List<String>> header1 =finalList.get(1);
		//XSSFSheet sheet= (XSSFSheet) workbook.createSheet();
		 row =sheet1.createRow(0);
		 row1=sheet1.createRow(1);
		XSSFCell cell=null;
		XSSFCell cell1 =null;
		XSSFCell cell2 = null;
		if(a==0) {
		for(int i=0;i<header.size();i++) {
			if(!(header.get(i).get(0).equals(header1.get(i).get(0))) ){
				
			cell =row.createCell(i);
			cell1=row1.createCell(i);
			cell2=row1.createCell(i+1);
			cell.setCellValue(header.get(i).get(0));
			cell1.setCellValue(header1.get(i).get(0));
			if(header.get(i).get(0).equals(header.get(i+1).get(0)))
			{
				cell2.setCellValue(header1.get(i+1).get(0));
				if(a==0)
				{
			sheet1.addMergedRegion(new CellRangeAddress(0,0,i,i+1));
			
			i++;
				}else if(a==1) {
					repeatcount++;
					sheet1.addMergedRegion(new CellRangeAddress(0,0,i,repeatcount));	
				}
			
			}
			cell.setCellStyle(headerCellStyle);
			cell1.setCellStyle(headerCellStyle);
			cell2.setCellStyle(headerCellStyle);
			sheet1.autoSizeColumn(cell1.getColumnIndex());
			sheet1.autoSizeColumn(cell2.getColumnIndex());
			/*if(cell!=null) {
			i++;
			}*/
		}
			else  {
				repeatcount=0;
				cell =row.createCell(i);				
				cell.setCellValue(header.get(i).get(0));
				sheet1.addMergedRegion(new CellRangeAddress(0,1,i,i));				
				cell.setCellStyle(headerCellStyle);
				//sheet1.autoSizeColumn(cell.getColumnIndex());				 
						sheet1.setColumnWidth(i,5500);				
			}
			}
			
		}
		else {
			for(int i=0;i<header.size();i++) {
if(!(header.get(i).get(0).equals(header1.get(i).get(0))) ){
					
					cell =row.createCell(i);
				
					cell1=row1.createCell(i);
					cell2=row1.createCell(i+1);
					
					cell.setCellValue(header.get(i).get(0));
					cell1.setCellValue(header1.get(i).get(0));
					
					  if(header.get(i).get(0).equals(header.get(i+1).get(0))) {
					  cell2.setCellValue(header1.get(i+1).get(0)); 
					  repeatcount++;
						
					  }
					 
							
					cell.setCellStyle(headerCellStyle);
					cell1.setCellStyle(headerCellStyle);
					cell2.setCellStyle(headerCellStyle);
					//sheet1.autoSizeColumn(cell1.getColumnIndex());
					//sheet1.autoSizeColumn(cell2.getColumnIndex());
					
				}
					else  {
						repeatcount=0;
						cell =row.createCell(i);				
						cell.setCellValue(header.get(i).get(0));
						sheet1.addMergedRegion(new CellRangeAddress(0,1,i,i));						
						cell.setCellStyle(headerCellStyle);
						//sheet1.autoSizeColumn(cell.getColumnIndex());						 
								sheet1.setColumnWidth(i,5500);
							
					}
			
			}
			sheet1.addMergedRegion(new CellRangeAddress(0,0,1,5));
			sheet1.addMergedRegion(new CellRangeAddress(0,0,9,22));
			sheet1.addMergedRegion(new CellRangeAddress(0,0,23,25));
			
		}
		header.clear();
		
		List<List<String>> values =finalList.get(2);
		if(a==0) {
		int v =2;
		for(int i=0;i<values.size();i++) {
			row =sheet1.createRow(v+i);
			for(int j=0;j<values.get(i).size();j++) {
				cell=row.createCell(j);
				
				String val =values.get(i).get(j);
				cell.setCellValue(val);
				cell.setCellStyle(CellStyle);
				
				//sheet1.autoSizeColumn(cell.getColumnIndex());
			}
		}
		}else if(a==1) {
			int v =2;
			
			for(int i=0;i<values.size();i++) {
				row =sheet1.createRow(v+i);
				for(int j=0;j<values.get(i).size();j++) {
					cell=row.createCell(j);
					
					String val =values.get(i).get(j);
					if(values.get(i).get(j).contains("#"))
					{
						cell.setCellValue(values.get(i).get(j).split("#")[0]);
						cell.setCellStyle(CellStyle);
						String mergecount = values.get(i).get(j).split("#")[1];
						sheet1.addMergedRegion(new CellRangeAddress(i+3-Integer.parseInt(mergecount),i+2,j,j));
					}
					else
					{
					cell.setCellValue(val);
					cell.setCellStyle(CellStyle);
					}
					
				}
			}
		}
		values.clear();
		
		
	}
	
}
	} catch (Exception e) {
		e.printStackTrace();
		

	} finally {
	
	}
	
	return workbook;
}
public XSSFWorkbook  reconReportDownload(GTBean bean,String datedata,String todate,String spocid) {

	

	
	
	XSSFWorkbook workbook =new XSSFWorkbook();
	XSSFSheet sheet1 = null;
	
	
		//sheet1 = workbook.createSheet("reconReportDownload");
	
	
	XSSFRow row = null;
	int count = 0;
	
	List<List<List<String>>> finalList = null;
	
	try {
		String[] sheetNames = {"Associates","Contractors"};
		for(short a=0;a<sheetNames.length;a++) {
			count = 0;
			finalList = repo. reconReportDownload(bean,sheetNames[a],datedata,todate,spocid);
		sheet1 = workbook.createSheet(sheetNames[a]);
		

		if(!finalList.isEmpty()) {
			
			//workbook = new XSSFWorkbook();
		 	Font headerFont = workbook.createFont();
	        headerFont.setBold(true);
	        headerFont.setFontHeightInPoints((short) 12);
	        headerFont.setColor(IndexedColors.WHITE.getIndex());
		 
		 	CellStyle headerCellStyle = workbook.createCellStyle();
	        headerCellStyle.setFont(headerFont);
	        // fill foreground color ...
	        headerCellStyle.setFillForegroundColor(IndexedColors.SEA_GREEN .index); //OLIVE_GREEN SEA_GREEN 
	        // and solid fill pattern produces solid grey cell fill
	        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
	        headerCellStyle.setFont(headerFont);
	
	        CellStyle CellStyle = workbook.createCellStyle();
			CellStyle.setAlignment(HorizontalAlignment.CENTER);
		List<List<String>> header =finalList.get(0);
		//XSSFSheet sheet= (XSSFSheet) workbook.createSheet();
		//int c=0;
		XSSFCell cell=null;
		row = sheet1.createRow(0);
		for(int i=0;i<header.size();i++) {
			
             cell =row.createCell(i);
			
			cell.setCellValue(header.get(i).get(0));
			cell.setCellStyle(headerCellStyle);
			// sheet1.protectSheet("PMO@TCS");
			sheet1.setColumnWidth(i,6500);
		}
		header.clear();
		
		List<List<String>> values =finalList.get(1);
		int v =1;
		for(int i=0;i<values.size();i++) {
			row =sheet1.createRow(v+i);
			for(int j=0;j<values.get(i).size();j++) {
				cell=row.createCell(j);
				
				String val =values.get(i).get(j);
				cell.setCellValue(val);
				cell.setCellStyle(CellStyle);
				/* sheet1.autoSizeColumn(cell.getColumnIndex()); */
				
			}
		}
		values.clear();
		finalList.clear();
		
	}
	
}
	

	} catch (Exception e) {
		e.printStackTrace();
		

	} finally {
	
	}
	
	return workbook;
}
public XSSFWorkbook  turnoutReportDownload(GTBean bean,String datedata,String todate,String spocid) {

	XSSFWorkbook workbook =new XSSFWorkbook();
	XSSFSheet sheet1 = null;
	
	
		//sheet1 = workbook.createSheet("reconReportDownload");
	
	
	XSSFRow row = null;
	XSSFRow row1=null;
	int count = 0;
	
	List<List<List<String>>> finalList = null;
	
	try {
		String sheetName = "Turnout_Pivot";
		
			count = 0;
			finalList = repo. turnoutReportDownload(bean,sheetName,datedata,todate,spocid);
		sheet1 = workbook.createSheet(sheetName);
		

		if(!finalList.isEmpty()) {
			
			//workbook = new XSSFWorkbook();
		 	Font headerFont = workbook.createFont();
	        headerFont.setBold(true);
	        headerFont.setFontHeightInPoints((short) 12);
	        headerFont.setColor(IndexedColors.WHITE.getIndex());
		 
		 	CellStyle headerCellStyle = workbook.createCellStyle();
	        headerCellStyle.setFont(headerFont);
	        // fill foreground color ...
	        headerCellStyle.setFillForegroundColor(IndexedColors.SEA_GREEN .index); //OLIVE_GREEN SEA_GREEN 
	        // and solid fill pattern produces solid grey cell fill
	        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
	        headerCellStyle.setFont(headerFont);
	
	        CellStyle CellStyle = workbook.createCellStyle();
			CellStyle.setAlignment(HorizontalAlignment.CENTER);
			List<List<String>> header =finalList.get(0);
			List<List<String>> header1 =finalList.get(1);
			//XSSFSheet sheet= (XSSFSheet) workbook.createSheet();
			 row =sheet1.createRow(0);
			 row1=sheet1.createRow(1);
			XSSFCell cell=null;
			XSSFCell cell1 =null;
			XSSFCell cell2 = null;

		for(int i=0;i<header.size();i++) {
if(!(header.get(i).get(0).equals(header1.get(i).get(0))) ){
				
				cell =row.createCell(i);
			
				cell1=row1.createCell(i);
				cell2=row1.createCell(i+1);
				
				cell.setCellValue(header.get(i).get(0));
				cell1.setCellValue(header1.get(i).get(0));
				
				  if(header.get(i).get(0).equals(header.get(i+1).get(0))) {
				  cell2.setCellValue(header1.get(i+1).get(0)); 
				
					
				  }
				 
						
				cell.setCellStyle(headerCellStyle);
				cell1.setCellStyle(headerCellStyle);
				cell2.setCellStyle(headerCellStyle);
				//sheet1.autoSizeColumn(cell1.getColumnIndex());
				//sheet1.autoSizeColumn(cell2.getColumnIndex());
				
			}
				else  {
					
					cell =row.createCell(i);				
					cell.setCellValue(header.get(i).get(0));
					sheet1.addMergedRegion(new CellRangeAddress(0,1,i,i));						
					cell.setCellStyle(headerCellStyle);
					//sheet1.autoSizeColumn(cell.getColumnIndex());						 
							sheet1.setColumnWidth(i,5500);
						
				}
		
		}
		sheet1.addMergedRegion(new CellRangeAddress(0,0,1,3));
		
		
	
		header.clear();
		
		List<List<String>> values =finalList.get(2);
		int v =2;
		for(int i=0;i<values.size();i++) {
			row =sheet1.createRow(v+i);
			for(int j=0;j<values.get(i).size();j++) {
				cell=row.createCell(j);
				
				String val =values.get(i).get(j);
				cell.setCellValue(val);
				cell.setCellStyle(CellStyle);
				/* sheet1.autoSizeColumn(cell.getColumnIndex()); */
				
			}
		}
	
		values.clear();
		finalList.clear();
		
	}
	

	

	} catch (Exception e) {
		e.printStackTrace();
		

	} finally {
	
	}
	
	return workbook;
}

public XSSFWorkbook  trendReportDownload(GTBean bean,String datedata,String todate,String spocid,String tcity) throws  IOException {
	XSSFWorkbook wb = new XSSFWorkbook();
	XSSFSheet sheet = null;	
	//XSSFRow row = null;
	int count = 0;
	
	List<List<List<String>>> finalList = null;
	List<List<String>> values =null;
	try{
		String[] sheetNames = {"Chennai","Other_location"};
		for(short a=0;a<sheetNames.length;a++) {
			count = 0;
			finalList = repo. trendReportDownload(bean,sheetNames[a],datedata,todate,spocid);
		sheet = wb.createSheet(sheetNames[a]);
		Row row = sheet.createRow(0);
		if(!finalList.isEmpty()) {
			Font headerFont = wb.createFont();
	        headerFont.setBold(true);
	        headerFont.setFontHeightInPoints((short) 11);
	        headerFont.setColor(IndexedColors.WHITE.getIndex());
		 
		 	CellStyle headerCellStyle = wb.createCellStyle();
	        //headerCellStyle.setFont(headerFont);
	        // fill foreground color ...
	        headerCellStyle.setFillForegroundColor(IndexedColors.DARK_BLUE .index); //OLIVE_GREEN SEA_GREEN 
	        // and solid fill pattern produces solid grey cell fill
	        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
	        headerCellStyle.setFont(headerFont);
	
	        CellStyle cellStyle = wb.createCellStyle();
	        Font totalFont = wb.createFont();
	        totalFont.setBold(true);
	        cellStyle.setFont(totalFont);
	        //cellStyle.setAlignment(HorizontalAlignment.CENTER); 
	        
		List<List<String>> header =finalList.get(0);
		
		Cell cell=null;
		
		for(int i=0;i<header.size();i++) {
			
             cell =row.createCell(i);
			
			cell.setCellValue(header.get(i).get(0));
			cell.setCellStyle(headerCellStyle);
			sheet.autoSizeColumn(cell.getColumnIndex());
		}
		
		header.clear();
		
		 values =finalList.get(1);
		int v =1;
		for(int i=0;i<values.size();i++) {
			row =sheet.createRow(v+i);
			for(int j=0;j<values.get(i).size();j++) {
				cell=row.createCell(j);
				if(i==2) {
					if(j==0) {
					cell.setCellValue(values.get(i).get(j));
					cell.setCellStyle(cellStyle);
					}else {
						int val = Integer.parseInt(values.get(i).get(j));
						cell.setCellValue(val);
						//cellStyle.setDataFormat((short)0);
						cell.setCellStyle(cellStyle);
					}
					
				}else
				{
				if(j==0) {
					String val = values.get(i).get(j);
					cell.setCellValue(val);
				}else {
				int val = Integer.parseInt(values.get(i).get(j));
				cell.setCellValue(val);
				//cellStyle.setDataFormat((short)9);
				//cell.setCellStyle(cellStyle);
				}
			}		
			
			}
		}
	
		//values.clear();
		//finalList.clear();
		
	}
	
int v = values.get(0).size();

			//row = sheet.createRow(0);
			
		//String sheetName = "Chennai" ;

	/* sheet = wb.createSheet(sheetNames[a]);

		// Country Names
		Row row = sheet.createRow((short) 0);

		Cell cell = row.createCell((short) 0);
		cell.setCellValue("20-JUL-20");

		cell = row.createCell((short) 1);
		cell.setCellValue("21-JUL-20");

		cell = row.createCell((short) 2);
		cell.setCellValue("22-JUL-20");

		cell = row.createCell((short) 3);
		cell.setCellValue("23-JUL-20");

		cell = row.createCell((short) 4);
		cell.setCellValue("24-JUL-20");

		cell = row.createCell((short) 5);
		cell.setCellValue("25-JUL-20");

		cell = row.createCell((short) 6);
		cell.setCellValue("26-JUL-20");

		// Country Area
		row = sheet.createRow((short) 1);

		cell = row.createCell((short) 0);
		cell.setCellValue(170);

		cell = row.createCell((short) 1);
		cell.setCellValue(998);

		cell = row.createCell((short) 2);
		cell.setCellValue(982);

		cell = row.createCell((short) 3);
		cell.setCellValue(959);

		cell = row.createCell((short) 4);
		cell.setCellValue(851);

		cell = row.createCell((short) 5);
		cell.setCellValue(774);

		cell = row.createCell((short) 6);
		cell.setCellValue(328);

		// Country Population
		row = sheet.createRow((short) 2);

		cell = row.createCell((short) 0);
		cell.setCellValue(145);

		cell = row.createCell((short) 1);
		cell.setCellValue(351);

		cell = row.createCell((short) 2);
		cell.setCellValue(329);

		cell = row.createCell((short) 3);
		cell.setCellValue(143);

		cell = row.createCell((short) 4);
		cell.setCellValue(211);

		cell = row.createCell((short) 5);
		cell.setCellValue(253);

		cell = row.createCell((short) 6);
		cell.setCellValue(137);

		// turnout
				row = sheet.createRow((short) 3);

				cell = row.createCell((short) 0);
				cell.setCellValue(14);

				cell = row.createCell((short) 1);
				cell.setCellValue(35);

				cell = row.createCell((short) 2);
				cell.setCellValue(32);

				cell = row.createCell((short) 3);
				cell.setCellValue(14);

				cell = row.createCell((short) 4);
				cell.setCellValue(21);

				cell = row.createCell((short) 5);
				cell.setCellValue(25);

				cell = row.createCell((short) 6);
				cell.setCellValue(13);*/

		XSSFDrawing drawing = sheet.createDrawingPatriarch();
		XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, 5, 10, 20);

		XSSFChart chart = drawing.createChart(anchor);
		chart.setTitleText(sheetNames[a]);
		//chart.setTitleText("Chennai");
		chart.setTitleOverlay(false);

		XDDFChartLegend legend = chart.getOrAddLegend();
		legend.setPosition(LegendPosition.TOP_RIGHT);

		//XDDFCategoryAxis bottomAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
		XDDFDateAxis bottomAxis = chart.createDateAxis(AxisPosition.BOTTOM);
		//bottomAxis.setTitle("Date");
		XDDFValueAxis leftAxis = chart.createValueAxis(AxisPosition.LEFT);
		//leftAxis.setTitle("Planned & Actuals");
		
		/*XDDFValueAxis rightAxis = chart.createValueAxis(AxisPosition.RIGHT);
		rightAxis.setTitle("Turnout");
		rightAxis.setCrosses(AxisCrosses.AUTO_ZERO);*/

		XDDFDataSource<String> date = XDDFDataSourcesFactory.fromStringCellRange(sheet,
				new CellRangeAddress(0, 0, 1, v-1));

		XDDFNumericalDataSource<Double> plan = XDDFDataSourcesFactory.fromNumericCellRange(sheet,
				new CellRangeAddress(1, 1, 1, v-1));

		XDDFNumericalDataSource<Double> actual = XDDFDataSourcesFactory.fromNumericCellRange(sheet,
				new CellRangeAddress(2, 2, 1, v-1)); 
		//values.get(0).size()-1
		
		XDDFNumericalDataSource<Double> turnout = XDDFDataSourcesFactory.fromNumericCellRange(sheet,
				new CellRangeAddress(3, 3, 1, v-1));

		XDDFLineChartData data = (XDDFLineChartData) chart.createData(ChartTypes.LINE, bottomAxis, leftAxis);
		
		
		//XDDFChartData data1 = chart.createData(ChartTypes.BAR, bottomAxis, leftAxis);
        
        
		
		XDDFLineChartData.Series series1 = (XDDFLineChartData.Series) data.addSeries(date, plan);
		series1.setTitle("Planned", null);
		series1.setSmooth(false);
		series1.setMarkerStyle(MarkerStyle.STAR);
		//series1.setShowLeaderLines(true);
		
		XDDFLineChartData.Series series2 = (XDDFLineChartData.Series) data.addSeries(date, actual);
		series2.setTitle("Actuals", null);
		series2.setSmooth(true);
		//series2.setMarkerSize((short) 6);
		series2.setMarkerStyle(MarkerStyle.SQUARE);
		//series2.setShowLeaderLines(true);
		
		
		XDDFLineChartData.Series series3 = (XDDFLineChartData.Series) data.addSeries(date, turnout);
		series3.setTitle("Turnout", null);
		series3.setSmooth(true);
		series3.setMarkerStyle(MarkerStyle.TRIANGLE);
		
		/*XDDFChartData.Series series3 = (XDDFChartData.Series) data1.addSeries(date, turnout);
		series3.setTitle("Turnout", null);*/
		
		
		chart.plot(data);//line
		//chart.plot(data1);//bar

		//to change column bar direction
		//XDDFBarChartData bar = (XDDFBarChartData) data1;
       // bar.setBarDirection(BarDirection.BAR);
        //bar.setBarDirection(BarDirection.COL);
       
		
		values.clear();
		finalList.clear();
	}	
	}catch(Exception e) {
		 e.printStackTrace();
		 log.error(this.getClass().getName() + " - Exception", e);
	 }
	return wb;
}


public List<List<List<String>>> onclickdashboardcard(GTBean bean) {
 log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
 List<List<List<String>>> data= null;
 try {
	 data = repo.onclickdashboardcard(bean);
 }
 catch(Exception e) {
	 e.printStackTrace();
	 log.error(this.getClass().getName() + " - Exception", e);
 }
 log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
 return data;
}



public ReponseData insertStagingAndTarget(List<List<RowData>> cellDataList, GTBean bean, XSSFWorkbook errorWorkbook,ReponseData res) {
	// TODO Auto-generated method stub
	String result="";
	//ReponseData reponsedata = null;
	
	log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
	try {
		//reponsedata = new ReponseData();
		res = repo.insertStagingAndTarget(cellDataList,bean,errorWorkbook,res);
		//saveTransaction(true);
	}catch(Exception e) {
		 e.printStackTrace();
		 log.error(this.getClass().getName() + " - Exception", e);
	 }finally {
		}

	 log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
	
	
	return res;

	

}


public List<Object> ListUploadcheck(Workbook workBook, byte[] b, GTBean bean) throws IOException, SQLException {
	// TODO Auto-generated method stub

	List<com.tcs.isometrix.GT.RowData> cellDataListHeader = null;
	List<com.tcs.isometrix.GT.RowData> cellDataList = null;
	List<List<com.tcs.isometrix.GT.RowData>> masterDataList = null;
	String exceptionFlag = "";
	RowData rowData = new RowData();
	List<List<String>> checkListRow = null;
	List<Object> returnList = new ArrayList<Object>();
	
	int sheetIndex = 0;
	log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
	try {
	checkListRow=repo.getExcelCheck(bean);	
	masterDataList = new ArrayList<List<RowData>>();
	cellDataListHeader=readDataHeaderCommon(b,checkListRow);
		rowData = new RowData();
		
		if(!(cellDataListHeader.isEmpty() || cellDataListHeader==null)){
		rowData = cellDataListHeader.get(0);

		
			List<String> rowListUpCase=new ArrayList<String>();
			for(String element2:rowData.getrowList()){
				rowListUpCase.add((element2).toLowerCase());
				}

			List<String> checkListUpCase=new ArrayList<String>();
			//New header check
			for(String element:checkListRow.get(0)){
				//For ignoring case and space
				String checkUpCase=element.toLowerCase();
				checkListUpCase.add(checkUpCase);
				boolean isPresent=	rowListUpCase.contains(checkUpCase);
				if(!isPresent){
					exceptionFlag = "H";
					returnList.add("H");
					returnList.add(element);
					break;
				}
			}
			
			if(!exceptionFlag.equalsIgnoreCase("H"))
			{
	            int headerindex[]=new int[checkListRow.get(0).size()];
	        	//For ignoring case and space
	            for(int k=0;k<checkListUpCase.size();k++){
					headerindex[k]=rowListUpCase.indexOf(checkListUpCase.get(k));
				}
				//For reading whole sheet(both header and data)
	            cellDataList = readfullDataCommon(b,headerindex,checkListRow);
			}	
			
			masterDataList.add(cellDataList);
		}
		else{
			exceptionFlag = "BE";
			returnList.add("BE");
		}
	//Blank sheet check
	if(exceptionFlag==""){

		if ((masterDataList.get(0).size() == 1)
				|| (masterDataList.get(0).isEmpty())) {
			exceptionFlag = "BE";
			returnList.add("BE");
		}
	}
	}
	catch (Exception e) {
		 e.printStackTrace();
		 log.error(this.getClass().getName() + " - Exception", e);
	}
	log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
	//Return list contains list of errors
		if (returnList.size() == 0)
			returnList.add(masterDataList);
		return returnList;
	
}


private List<RowData> readDataHeaderCommon(byte[] b, List<List<String>> checkListRow) {
	// TODO Auto-generated method stub
	List<RowData> cellDataList = new ArrayList<RowData>();
	List<String> rowDataList = null;
	log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
	try {
		ByteArrayInputStream barr = new ByteArrayInputStream(b);
		XSSFWorkbook workBook = new XSSFWorkbook(barr);
		XSSFSheet xSSFSheet = workBook.getSheetAt(0);
		java.util.Iterator<Row> rowIterator = xSSFSheet.rowIterator();String cellValue="";
		while (rowIterator.hasNext()) {
			XSSFRow xSSFRow = (XSSFRow) rowIterator.next();
			java.util.Iterator<Cell> iterator = xSSFRow.cellIterator();
			com.tcs.isometrix.GT.RowData cellTempList = new com.tcs.isometrix.GT.RowData();
			rowDataList = new ArrayList<String>();
			//for finding which row contains header reading the first cell of each row comparing it with first element of db headers
			//So even if there are entries first column of db and first column of excel is not same then
			//it is blank sheet error to avoid that below while code written
			//For reading header
			XSSFCell xssfCellHeader =xSSFRow.getCell(0,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
			if(xssfCellHeader!= null){
				cellValue = xssfCellHeader.toString();
				if(cellValue.equalsIgnoreCase("")){
					cellValue = " ";
				}
			}
			//checking which row contains header
			String valWithoutSpace=cellValue.replaceAll("[\\n\\t\\r\\P{Print}]","").replaceAll(" ","").trim();
			String chekWithoutSpace=checkListRow.get(0).get(0).replaceAll(" ", "");
			if(valWithoutSpace.equalsIgnoreCase(chekWithoutSpace)){
			for(int i=0;i<xSSFRow.getLastCellNum();i++){
				//For reading blank cells 
				XSSFCell xssfCell =xSSFRow.getCell(i,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
				if(xssfCell!= null){

					cellValue = xssfCell.toString();
					if(cellValue.equalsIgnoreCase("")){
						cellValue = " ";
					}
				}
				else
				{
					cellValue="";
				}
				rowDataList.add(cellValue);
			}
			cellTempList.setrowList(rowDataList);
			cellDataList.add(cellTempList);
			//For reading header only
			break;
			}
			//For alerting no row contains header
			else{
				continue;
			}
		}
		//To avoid showing blank sheet error
		//If excel contains data but no rows has headers
		//as in database 
		if(cellValue!="" && cellDataList.size()==0){
			RowData cellTempList = new RowData();
			rowDataList = new ArrayList<String>();
			rowDataList.add(cellValue);
			cellTempList.setrowList(rowDataList);
			cellDataList.add(cellTempList);
		}
		
	
	}catch (Exception e) {
		 e.printStackTrace();
		 log.error(this.getClass().getName() + " - Exception", e);
	}
	log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
	
	return cellDataList;
}


public List<RowData> readfullDataCommon(byte[] b,int[] pos,List<List<String>> checkListRow) {
	// TODO Auto-generated method stub
	List<RowData> cellDataList = new ArrayList<RowData>();
	List<String> rowDataList = null;
	/*
	 * List<String> dummy = null; dummy.add("S.no."); dummy.add("Name");
	 * dummy.add("type");
	 * 
	 * List<List<String>> checkListRow = new ArrayList<List<String>>();
	 * checkListRow.add(dummy);
	 */
   
	log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
	try{
		
		ByteArrayInputStream barr = new ByteArrayInputStream(b);
		XSSFWorkbook workBook = new XSSFWorkbook(barr);
		XSSFSheet xSSFSheet = workBook.getSheetAt(0);
		java.util.Iterator<Row> rowIterator = xSSFSheet.rowIterator();
		String cellValue="";
	

		boolean headerRow=false;
		while (rowIterator.hasNext()) {
			XSSFRow xSSFRow = (XSSFRow) rowIterator.next();
			java.util.Iterator<Cell> iterator = xSSFRow.cellIterator();
			com.tcs.isometrix.GT.RowData cellTempList = new com.tcs.isometrix.GT.RowData();
			rowDataList = new ArrayList<String>();
			//For reading first cell in every row
			XSSFCell xssfCellHeader =xSSFRow.getCell(0,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
			if(xssfCellHeader!= null){
				cellValue = xssfCellHeader.toString();
				if(cellValue.equalsIgnoreCase("")){
					cellValue = " ";
				}
			}
			//Checking which row contains header
			boolean rowExist=true;
			String valWithoutSpace=cellValue.replaceAll("[\\n\\t\\r\\P{Print}]","").replaceAll(" ","").trim();
			String chekWithoutSpace=checkListRow.get(0).get(0).replaceAll(" ", "");
			if(valWithoutSpace.equalsIgnoreCase(chekWithoutSpace)|| headerRow==true){
			for (int i = 0; i < pos.length; i++) {

				//For reading blank cells 
					Cell xssfCell =xSSFRow.getCell(pos[i],Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
				
				if(xssfCell!= null){
					
					
					switch (xssfCell.getCellType()) {
				
					case STRING:
					    cellValue = xssfCell.getStringCellValue().trim();
					    cellValue = new DataFormatter().formatCellValue(xssfCell);
					    break;
					
					case NUMERIC:
						cellValue=String.valueOf(xssfCell.getNumericCellValue());
						cellValue=new DataFormatter().formatCellValue(xssfCell);

						/*
						 * if(org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(xssfCell)){
						 * 
						 * java.util.Date d=xssfCell.getDateCellValue(); int y=d.getYear()+1900;
						 * 
						 * String date=String.valueOf(d.getDate()); String
						 * mon=String.valueOf(d.getMonth()+1); String year=String.valueOf(y);
						 * if(date.length()==1){ date="0"+date; } if(mon.length()==1){ mon="0"+mon; }
						 * 
						 * 
						 * cellValue=mon+"/"+date+"/"+year;
						 * 
						 * } else{
						 * 
						 * cellValue=String.valueOf(xssfCell.getNumericCellValue()); cellValue=new
						 * DataFormatter().formatCellValue(xssfCell); }
						 */
						
					    break;
					
					case FORMULA:


						switch (xssfCell.getCachedFormulaResultType()) {
						
						case NUMERIC:
							
							cellValue=String.valueOf(xssfCell.getNumericCellValue());
							break;
					
						case STRING:
							cellValue = String.valueOf(xssfCell.getStringCellValue());
							
							break;
						}
					
					    break;
					default:
						cellValue="";
					}
						
					
						
					if(cellValue.equalsIgnoreCase("")){
						cellValue = " ";
					}
						}
				else
				{
					cellValue="";
				}
				rowDataList.add(cellValue);
				//System.out.println(rowDataList);
			
			}
			if(rowDataList.contains(" ")||rowDataList.contains("")) {
				for (int n = 0; n < rowDataList.size(); n++) {
					if(rowDataList.get(n)!=" ") {
						rowExist=true;
						break;
					}
					else {
						rowExist=false;
					}
					
				}
				
			}
			if(rowExist)
			{	cellTempList.setrowList(rowDataList);
		cellDataList.add(cellTempList);
		headerRow=true;}
		
		}

		}
	
	}
	catch (Exception e) {
		 e.printStackTrace();
		 log.error(this.getClass().getName() + " - Exception", e);
	}
	log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
	return cellDataList;
}

public List<List<List<String>>> getGridData(GTBean bean) {
	List<List<List<String>>> res=null;
	log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
	try {
		res = repo.getGridData(bean);
	}
	catch(Exception e) {
		e.printStackTrace();
		log.error(this.getClass().getName() + " - Exception", e);
	}
	log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
	return res;
}


public List<List<List<String>>> getTrendData(GTBean bean) {
	// TODO Auto-generated method stub
	log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
	List<List<List<String>>> data= null;
	try {
		data = repo.getTrendData(bean);
	}
	catch(Exception e) {
		e.printStackTrace();
		log.error(this.getClass().getName() + " - Exception", e);
	}
	log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
	return data;
}

}