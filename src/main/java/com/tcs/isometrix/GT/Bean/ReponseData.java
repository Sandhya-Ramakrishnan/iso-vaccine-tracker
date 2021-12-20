package com.tcs.isometrix.GT.Bean;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReponseData {
	private XSSFWorkbook errorWorkBook;
	private HSSFWorkbook hssfWorkbook;
	private List<List<List<String>>> list3 =null;
	private String flag1 ;
	private String flag2 ;
	private String maxDate;
	
	public XSSFWorkbook getErrorWorkBook() {
		return errorWorkBook;
	}

	public void setErrorWorkBook(XSSFWorkbook errorWorkBook) {
		this.errorWorkBook = errorWorkBook;
	}
	public HSSFWorkbook getHssfWorkbook() {
		return hssfWorkbook;
	}

	public void setHssfWorkbook(HSSFWorkbook hssfWorkbook) {
		this.hssfWorkbook = hssfWorkbook;
	}


	public String getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(String maxDate) {
		this.maxDate = maxDate;
	}

	public List<List<List<String>>> getList3() {
		return list3;
	}

	public void setList3(List<List<List<String>>> list3) {
		this.list3 = list3;
	}

	public String getFlag1() {
		return flag1;
	}

	public void setFlag1(String flag1) {
		this.flag1 = flag1;
	}

	public String getFlag2() {
		return flag2;
	}

	public void setFlag2(String flag2) {
		this.flag2 = flag2;
	}
	
}
