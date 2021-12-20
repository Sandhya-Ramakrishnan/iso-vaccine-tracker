package com.tcs.isometrix.GT.Controller;

import org.apache.poi.ss.usermodel.Workbook;

public class SendPyData {

	private byte[] zipFile ;
	private Workbook dbData;
	public byte[] getZipFile() {
		return zipFile;
	}
	public void setZipFile(byte[] zipFile) {
		this.zipFile = zipFile;
	}
	public Workbook getDbData() {
		return dbData;
	}
	public void setDbData(Workbook dbData) {
		this.dbData = dbData;
	}
	

	
}
