package com.tcs.isometrix.GT.Repo;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tcs.isometrix.GT.GtApplication;
import com.tcs.isometrix.GT.RowData;
import com.tcs.isometrix.GT.Bean.GTBean;
import com.tcs.isometrix.GT.Bean.ReponseData;

import oracle.jdbc.OracleTypes;

@Repository
public class GTRepo {
	@Autowired
	DataSource datasource;
	List<CallableStatement> callableStmtList = new ArrayList<CallableStatement>();
	private static final Logger log = LogManager.getLogger(GtApplication.class.getName());
/*	
	List<ResultSet> resultSetList = new ArrayList<ResultSet>();
	List<PreparedStatement> preparedStmtList = new ArrayList<PreparedStatement>();
	List<CallableStatement> callableStmtList = new ArrayList<CallableStatement>();
	List<Statement> statementList = new ArrayList<Statement>();*/
	
	
/*	public CallableStatement cs = null;
	public PreparedStatement pstmt = null;
	public CallableStatement csUploadCheck = null;
	public CallableStatement csTargetInsert = null;*/

	public String checkConn() {
		String res="";
		
		try (Connection conn=datasource.getConnection()){
			res= "success";
		}
		catch(Exception e) {
			e.printStackTrace();
			res= "error";
		}
		return res;
	}

	
	public List<List<List<String>>> CommonMethodListOfResulSet(List<ResultSet> rslist) {
		List<List<List<String>>> transferList = null;
		List<List<String>> subList = null;
		List<String> rowList = null;
		ResultSetMetaData rsmd = null;
		try {
			transferList = new ArrayList<List<List<String>>>();
			subList = new ArrayList<List<String>>();
			rowList = new ArrayList<String>();

			if (rslist != null) {
				for (int j = 0; j < rslist.size(); j++) {
					if (rslist.get(j) != null) {
						rsmd = rslist.get(j).getMetaData();
						subList = new ArrayList<List<String>>();
						while (rslist.get(j).next()) {
							rowList = new ArrayList<String>();
							for (int i = 1; i <= rsmd.getColumnCount(); i++) {
								String value = rslist.get(j).getString(i);
								if (value == null || value.equalsIgnoreCase(null)) {
									value = " ";
								}
								rowList.add(value);
							}
							subList.add(rowList);
						}
						transferList.add(subList);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return transferList;
	}


	public String saveDetials(GTBean bean) throws SQLException {
		String res=null;
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		try(Connection conn=datasource.getConnection()) {
			CallableStatement cs =null;
			try {
				String query="{call PKG_VACCINE_FORM_DETAILS.SP_FORM_UPDATE(?,?,?)}";
				cs=conn.prepareCall(query);
				cs.setString(1, bean.getEmpid());
				cs.setString(2,bean.getFlag());
				cs.registerOutParameter(3, OracleTypes.VARCHAR);
				cs.execute();
				
				res= cs.getString(3);
			}
			catch(Exception e) {
				e.printStackTrace();
				log.error(this.getClass().getName() + " - Exception", e);
			}
			finally {
				cs.close();
				conn.close();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error(this.getClass().getName() + " - Exception", e);
		}
		
		finally {
			log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		}
		
		return res;
	}

	
	synchronized public List<List<List<String>>> getDetails(GTBean bean) {

		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		List<List<List<String>>> data =null;
		try(Connection conn=datasource.getConnection()) {

			ResultSet c1=null;
			ResultSet c2=null;
			ResultSet c3=null;
			ResultSet c4=null;
			ResultSet c5=null;
			List<ResultSet> list =null;
			CallableStatement cs =null;
			
			try {
				String query="{call PKG_VACCINE_FORM_DETAILS.SP_FORM_QUESTIONS(?,?,?,?,?,?,?,?,?,?)}";
				cs=conn.prepareCall(query);
				
				cs.setString(1, bean.getSpocID());
				cs.setString(2, bean.getFlag());
				cs.setString(3, bean.getEmpid());
				cs.registerOutParameter(4, OracleTypes.CURSOR);
				cs.registerOutParameter(5, OracleTypes.CURSOR);
				cs.registerOutParameter(6, OracleTypes.CURSOR);
				cs.registerOutParameter(7, OracleTypes.CURSOR);
				cs.registerOutParameter(8, OracleTypes.CURSOR);
				cs.registerOutParameter(9, OracleTypes.CURSOR);
				cs.registerOutParameter(10, OracleTypes.VARCHAR);
				cs.execute();
				
				String msg = cs.getString(10);
				if(!msg.equalsIgnoreCase("N")) {
					 c1 = (ResultSet) cs.getObject(4);
					 c2 = (ResultSet) cs.getObject(5);
					 c3 = (ResultSet) cs.getObject(6);
					 c4 = (ResultSet) cs.getObject(7);
					 c5 = (ResultSet) cs.getObject(8);
					
					 list = new ArrayList<ResultSet>();
					data = new ArrayList<List<List<String>>>();
					
					list.add(c1);
					list.add(c2);
					list.add(c3);
					list.add(c4);
					list.add(c5);
					data =CommonMethodListOfResulSet(list);
					
					
				}
			}
			catch(Exception e) {
				e.printStackTrace();
				log.error(this.getClass().getName() + " - Exception", e);
			}
			finally {
				c1.close();
				c2.close();
				c3.close();
				c4.close();
				c5.close();
				list.clear();
				cs.close();
				conn.close();
			}
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error(this.getClass().getName() + " - Exception", e);
		}
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		
		return data;
		
	
	}
	 public List<List<List<String>>> getCardsData(GTBean bean) throws SQLException { //getLiveDashBoardData
		
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		List<List<List<String>>> data =null;
		
		try( Connection conn=datasource.getConnection()) {
			ResultSet c1=null;
			ResultSet c2=null;
			List<ResultSet> list =null;
			CallableStatement ccs =null;
			try {
				String query="{call PKG_VACCINE_FORM_DETAILS.SP_LIVE_CARD(?,?,?)}";
				ccs=conn.prepareCall(query);
				conn.setAutoCommit(false);
				ccs.setString(1, bean.getFlag());
				ccs.registerOutParameter(2, OracleTypes.CURSOR);
				ccs.registerOutParameter(3, OracleTypes.CURSOR);
				ccs.execute();
				
					 c1 = (ResultSet) ccs.getObject(2);
					 c2 = (ResultSet) ccs.getObject(3);
					
					 list = new ArrayList<ResultSet>();
					data = new ArrayList<List<List<String>>>();
					
					list.add(c1);
					list.add(c2);
					data =CommonMethodListOfResulSet(list);
				
			}
			catch(Exception e) {
				e.printStackTrace();
				log.error(this.getClass().getName() + " - Exception", e);
			}
			
			finally {
				log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
				 if(c1!=null) c1.close();
				 if(c2!=null) c2.close();
				list.clear();
				if(ccs!=null)ccs.close();
				conn.commit();
				if(conn!=null)conn.close();
			}
			}
		catch(Exception e){
			e.printStackTrace();
			log.error(this.getClass().getName() + " - Exception", e);
		}
			
			
		return data;
		
		
	}
	 public List<List<List<String>>> getLiveDashBoardData(GTBean bean) throws SQLException {
		 
		 log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		 List<List<List<String>>> data =null;
		 try(Connection conn=datasource.getConnection()) {
			 
			 ResultSet c1=null;
				ResultSet c2=null;
				ResultSet c3=null;
				List<ResultSet> list =null;
				CallableStatement cs =null;
			 
			 try {
				 String query="{call PKG_VACCINE_FORM_DETAILS.SP_OVERALL_DASHBOARD(?,?,?,?)}";
				 cs=conn.prepareCall(query);
				 
				 cs.setString(1, bean.getFlag());
				 cs.registerOutParameter(2, OracleTypes.CURSOR);
				 cs.registerOutParameter(3, OracleTypes.CURSOR);
				 cs.registerOutParameter(4, OracleTypes.CURSOR);
				 conn.setAutoCommit(false);
				 cs.execute();
				 
				  c1 = (ResultSet) cs.getObject(2);
				  c2 = (ResultSet) cs.getObject(3);
				  c3= (ResultSet) cs.getObject(4);
				 
				  list = new ArrayList<ResultSet>();
				 data = new ArrayList<List<List<String>>>();
				 
				 list.add(c1);
				 list.add(c2);
				 list.add(c3);
				 data =CommonMethodListOfResulSet(list);
			 }
			 catch(Exception e) {
				 e.printStackTrace();
				 log.error(this.getClass().getName() + " - Exception", e);
			 }
			 finally {
				 log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
				 if(c1!=null) c1.close();
				 if(c2!=null) c2.close();
				 if(c3!=null) c3.close();
				 list.clear();
				 if(cs!=null)cs.close();
				 conn.commit();
				 if(conn!=null)conn.close();
			 }
			 }
		 catch(Exception e){
				e.printStackTrace();
				log.error(this.getClass().getName() + " - Exception", e);
			}
			
		 return data;
		 
		 
	 }
	public List<List<List<String>>> getDriveDetails(GTBean bean) {
		
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		List<List<List<String>>> data =null;
		try(Connection conn=datasource.getConnection()) {
			
			ResultSet c1=null;
			ResultSet c2=null;
			ResultSet c3=null;
			ResultSet c4=null;
			ResultSet c5=null;
			List<ResultSet> list =null;
			CallableStatement cs =null;
			
			try {
				String query="{call PKG_VACCINE_ASSO_FORM_SUBMIT.SP_FORM_HEADERS(?,?,?,?,?,?,?)}";
				cs=conn.prepareCall(query);
				cs.setString(1, bean.getEmpid());
				cs.registerOutParameter(2, OracleTypes.CURSOR);
				cs.registerOutParameter(3, OracleTypes.CURSOR);
				cs.registerOutParameter(4, OracleTypes.CURSOR);
				cs.registerOutParameter(5, OracleTypes.CURSOR);
				cs.registerOutParameter(6, OracleTypes.CURSOR);
				cs.registerOutParameter(7, OracleTypes.VARCHAR);
				cs.executeUpdate();
				
				String msg = cs.getString(7);
				System.out.println(msg);
					 c1 = (ResultSet) cs.getObject(2);
					 c2 = (ResultSet) cs.getObject(3);
					 c3 = (ResultSet) cs.getObject(4);
					 c4 = (ResultSet) cs.getObject(5);
					 c5 = (ResultSet) cs.getObject(6);
					
					 list = new ArrayList<ResultSet>();
					data = new ArrayList<List<List<String>>>();
					
					list.add(c1);
					list.add(c2);
					list.add(c3);
					list.add(c4);
					list.add(c5);
					data =CommonMethodListOfResulSet(list);
					
			}
			catch(Exception e) {
				e.printStackTrace();
				log.error(this.getClass().getName() + " - Exception", e);
			}
			finally {
				cs.close();
				c1.close();
				c2.close();
				c3.close();
				c4.close();
				list.clear();
				cs.close();
				conn.close();
			}
			
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
		List<List<List<String>>> data =null;
		try(Connection conn=datasource.getConnection()) {
			
			ResultSet c1=null;
			ResultSet c2=null;
			ResultSet c3=null;
			ResultSet c4=null;
			List<ResultSet> list =null;
			CallableStatement cs =null;
			
			try {
				String query="{call PKG_VACCINE_FORM_DETAILS.SP_ASSOCIATE_VERIFICATION_DETAILS(?,?,?,?,?,?)}";
				cs=conn.prepareCall(query);
				cs.setString(1, bean.getFlag());
				cs.setString(2, bean.getEmpid());
				cs.registerOutParameter(3, OracleTypes.CURSOR);
				cs.registerOutParameter(4, OracleTypes.CURSOR);
				cs.registerOutParameter(5, OracleTypes.CURSOR);
				cs.registerOutParameter(6, OracleTypes.CURSOR);
				cs.execute();
				
					 c1 = (ResultSet) cs.getObject(3);
					 c2 = (ResultSet) cs.getObject(4);
					 c3 = (ResultSet) cs.getObject(5);
					 c4 = (ResultSet) cs.getObject(6);
					
					 list = new ArrayList<ResultSet>();
					data = new ArrayList<List<List<String>>>();
					
					list.add(c1);
					list.add(c2);
					list.add(c3);
					list.add(c4);
					data =CommonMethodListOfResulSet(list);
					
			}
			catch(Exception e) {
				e.printStackTrace();
				log.error(this.getClass().getName() + " - Exception", e);
			}
			finally {
				c1.close();
				c2.close();
				c3.close();
				c4.close();
				list.clear();
				cs.close();
				conn.close();
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error(this.getClass().getName() + " - Exception", e);
		}
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		
		return data;
		
		
	}


	public List<List<List<String>>> downloadreport(GTBean bean) {
		List<List<List<String>>> data = null; //= new ArrayList<List<List<String>>>();
		try (Connection conn=datasource.getConnection()) {
			
			ResultSet c1=null;
			ResultSet c2=null;
			List<ResultSet> list =null;
			CallableStatement cs =null;
			
			try {
				String query="{call PKG_VACCINE_FORM_DETAILS.SP_FORM_DWNLD(?,?,?)}";
				cs=conn.prepareCall(query);
				cs.setString(1,bean.getEmpid());
				cs.registerOutParameter(2, OracleTypes.CURSOR);
				cs.registerOutParameter(3, OracleTypes.CURSOR);
				cs.execute();
				
				 c1 = (ResultSet) cs.getObject(2);
				 c2 = (ResultSet) cs.getObject(3);
				list= new ArrayList<ResultSet>();
				list.add(c1);
				list.add(c2);
				data =CommonMethodListOfResulSet(list);
				//System.out.println(data);
				//System.out.println(data.size());
			
			}
			catch(Exception e) {
				e.printStackTrace();
				log.error(this.getClass().getName() + " - Exception", e);
			}
			finally {
				c1.close();
				c2.close();
				list.clear();
				cs.close();
				conn.close();
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error(this.getClass().getName() + " - Exception", e);
		}
		
		return data;
	}
	
	public List<List<List<String>>> getvaccinationLocation(GTBean bean) {
		List<List<List<String>>> data =null;
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		try(Connection conn=datasource.getConnection()) {
			
			ResultSet c1=null;
			ResultSet c2=null;
			ResultSet c3=null;
			ResultSet c4=null;
			ResultSet c5=null;
			ResultSet c6=null;
			List<ResultSet> list =null;
			CallableStatement cs =null;
				
			try {
				String query="{call PKG_VACCINE_FORM_DETAILS.SP_SPOC_LOCATION_DROPDOWN(?,?,?,?,?,?,?)}";
				cs=conn.prepareCall(query);
				cs.setString(1, bean.getEmpid());
				cs.registerOutParameter(2, OracleTypes.CURSOR);
				cs.registerOutParameter(3, OracleTypes.CURSOR);
				cs.registerOutParameter(4, OracleTypes.CURSOR);
				cs.registerOutParameter(5, OracleTypes.CURSOR);
				cs.registerOutParameter(6, OracleTypes.CURSOR);
				cs.registerOutParameter(7, OracleTypes.CURSOR);
				cs.execute();
				
				 c1 = (ResultSet) cs.getObject(2);
				 c2 = (ResultSet) cs.getObject(3);
				 c3 = (ResultSet) cs.getObject(4);
				 c4 = (ResultSet) cs.getObject(5);
				 c5 = (ResultSet) cs.getObject(6);
				 c6 = (ResultSet) cs.getObject(7);
				
				 list = new ArrayList<ResultSet>();
				data = new ArrayList<List<List<String>>>();
				
				list.add(c1);
				list.add(c2);
				list.add(c3);
				list.add(c4);
				list.add(c5);
				list.add(c6);
				data =CommonMethodListOfResulSet(list);
				
			}
			catch(Exception e) {
				e.printStackTrace();
				log.error(this.getClass().getName() + " - Exception", e);
			}
			finally {
				c1.close();
				c2.close();
				c3.close();
				c4.close();
				c5.close();
				c6.close();
				list.clear();
				cs.close();
				conn.close();
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error(this.getClass().getName() + " - Exception", e);
		}
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		return data;
	}
	public ReponseData getDasboardAccess(GTBean bean, ReponseData res) {
		List<List<List<String>>> data =null;
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		try(Connection conn=datasource.getConnection()) {
			
			ResultSet c1=null;
			List<ResultSet> list =null;
			CallableStatement cs =null;
			
			try {
				String query="{call PKG_VACCINE_FORM_DETAILS.SP_SPOC_AUTH_LIVE_DASHBOARD(?,?,?,?,?)}";
				cs=conn.prepareCall(query);
				cs.setString(1, bean.getEmpid());
				cs.registerOutParameter(2, OracleTypes.VARCHAR);
				cs.registerOutParameter(3, OracleTypes.CURSOR);
				cs.registerOutParameter(4, OracleTypes.VARCHAR);
				cs.registerOutParameter(5, OracleTypes.VARCHAR);
				cs.execute();
				
				c1 = (ResultSet) cs.getObject(3);
				res.setFlag1(cs.getString(2));
				res.setFlag2(cs.getString(4));
				res.setMaxDate(cs.getString(5));
				
				list = new ArrayList<ResultSet>();
				data = new ArrayList<List<List<String>>>();
				
				list.add(c1);
				data =CommonMethodListOfResulSet(list);
				
				res.setList3(data);
				
			}
			catch(Exception e) {
				e.printStackTrace();
				log.error(this.getClass().getName() + " - Exception", e);
			}
			finally {
				c1.close();
				list.clear();
				cs.close();
				conn.close();
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error(this.getClass().getName() + " - Exception", e);
		}
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		return res;
	}
	public List<List<List<String>>> getFormFields(GTBean bean) {
		List<List<List<String>>> data =null;
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		try(Connection conn=datasource.getConnection()) {
			
			ResultSet c1=null;
			List<ResultSet> list =null;
			CallableStatement cs =null;
			
			try {
				String query="{call PKG_VACCINE_FORM_DETAILS.SP_FORM_COMPANY_NAMES(?)}";
				cs=conn.prepareCall(query);
				cs.registerOutParameter(1, OracleTypes.CURSOR);
				cs.execute();
				
				 c1 = (ResultSet) cs.getObject(1);
				
				 list = new ArrayList<ResultSet>();
				data = new ArrayList<List<List<String>>>();
				
				list.add(c1);
				data =CommonMethodListOfResulSet(list);
				
				
			}
			catch(Exception e) {
				e.printStackTrace();
				log.error(this.getClass().getName() + " - Exception", e);
			}
			finally{
				
				c1.close();
				list.clear();
				cs.close();
				conn.close();
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error(this.getClass().getName() + " - Exception", e);
		}
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		return data;
	}
	public List<List<List<String>>> getCategoryDrop(GTBean bean) {
		List<List<List<String>>> data =null;
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		try(Connection conn=datasource.getConnection()) {
			
			ResultSet c1=null;
			List<ResultSet> list =null;
			CallableStatement cs =null;
			
			try {
				String query="{call PKG_VACCINE_FORM_DETAILS.SP_CATEGORY_DROPDOWN(?)}";
				cs=conn.prepareCall(query);
				cs.registerOutParameter(1, OracleTypes.CURSOR);
				cs.execute();
				
				c1 = (ResultSet) cs.getObject(1);
				
				list = new ArrayList<ResultSet>();
				data = new ArrayList<List<List<String>>>();
				
				list.add(c1);
				data =CommonMethodListOfResulSet(list);
				
				
			}
			catch(Exception e) {
				e.printStackTrace();
				log.error(this.getClass().getName() + " - Exception", e);
			}
			finally{
				
				c1.close();
				list.clear();
				cs.close();
				conn.close();
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error(this.getClass().getName() + " - Exception", e);
		}
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		return data;
	}
	
	
	public String checkEmp(GTBean bean) {
		String res=null;
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		try(Connection conn=datasource.getConnection()) {
			
			CallableStatement cs =null;
			
			try {
				String query="{call PKG_VACCINE_FORM_DETAILS.SP_SPOC_AUTHENTICATION(?,?,?)}";
				cs=conn.prepareCall(query);
				cs.setString(1, bean.getEmpid());
				cs.registerOutParameter(2, OracleTypes.VARCHAR);
				cs.registerOutParameter(3, OracleTypes.VARCHAR);
				cs.execute();
				
				res= cs.getString(2);
				if(!bean.getFlag().equalsIgnoreCase("Dashboard")) {
					if(res.equalsIgnoreCase("Y")) {
						res= cs.getString(3);
					}
				}
				
			}
			catch(Exception e) {
				e.printStackTrace();
				log.error(this.getClass().getName() + " - Exception", e);
			}
			finally {
				cs.close();
				conn.close();
			}
			
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
		try(Connection conn=datasource.getConnection()) {
			
			CallableStatement cs =null;
			try {
				String query="{call PKG_VACCINE_FORM_DETAILS.SP_FORM_TOKEN(?,?,?,?,?,?)}";
				cs=conn.prepareCall(query);
				cs.setString(1, bean.getSpocID());//flag
				cs.setString(2, bean.getEmpid());
				cs.setString(3, bean.getFlag());
				cs.setString(4, bean.getInput());
				cs.registerOutParameter(5, OracleTypes.VARCHAR);
				cs.registerOutParameter(6, OracleTypes.VARCHAR);
				cs.execute();
					
				res= cs.getString(5);
			}
			catch(Exception e) {
				e.printStackTrace();
				log.error(this.getClass().getName() + " - Exception", e);
			}
			finally {
				cs.close();
				conn.close();
			}
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
		try(Connection conn=datasource.getConnection()) {
			CallableStatement cs =null;
			try {
				String query="{call PKG_VACCINE_FORM_DETAILS.SP_ASSOCIATE_SAVE_STATUS(?,?,?,?)}";
				cs=conn.prepareCall(query);
				cs.setString(1, bean.getFlag());
				cs.setString(2, bean.getEmpid());
				cs.setString(3, bean.getInput());
				cs.registerOutParameter(4, OracleTypes.VARCHAR);
				cs.execute();
				
				res= cs.getString(4);
			}
			catch(Exception e) {
				e.printStackTrace();
				log.error(this.getClass().getName() + " - Exception", e);
			}
			finally {
				cs.close();
				conn.close();
			}
			
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
		try(Connection conn=datasource.getConnection()) {
			CallableStatement cs =null;
			try {
				String query="{call PKG_VACCINE_FORM_DETAILS.SP_ADD_WALK_IN_DETAILS(?,?,?,?)}";
				cs=conn.prepareCall(query);
				cs.setString(1, bean.getEmpid());
				cs.setString(2, bean.getInput());
				cs.registerOutParameter(3, OracleTypes.VARCHAR);
				cs.registerOutParameter(4, OracleTypes.VARCHAR);
				cs.execute();
				
				res= cs.getString(3);
				if(!res.equalsIgnoreCase("Y")) {
					res= cs.getString(4);
				}
				else res="no";
			}
			catch(Exception e) {
				e.printStackTrace();
				log.error(this.getClass().getName() + " - Exception", e);
			}
			finally {
				cs.close();
				conn.close();
			}
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
		try(Connection conn=datasource.getConnection()) {
			CallableStatement cs =null;
			try {
				String query="{call PKG_VACCINE_FORM_DETAILS.SP_ADD_WALK_IN_DETAILS(?,?,?,?,?,?)}";
				cs=conn.prepareCall(query);
				cs.setString(1, bean.getEmpid());//empp
				cs.setString(2, bean.getInput());//name
				cs.setString(3, bean.getSpocID());//relationship
				cs.setString(4, bean.getFlag());//cowin id
				cs.registerOutParameter(5, OracleTypes.VARCHAR);
				cs.registerOutParameter(6, OracleTypes.VARCHAR);
				cs.execute();
				
				res= cs.getString(5);
				if(res.equalsIgnoreCase("N")) {
					res= cs.getString(6);
				}
			}
			catch(Exception e) {
				e.printStackTrace();
				log.error(this.getClass().getName() + " - Exception", e);
			}
			finally {
				cs.close();
				conn.close();
			}
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
		try(Connection conn=datasource.getConnection()) {
			CallableStatement cs =null;
			try {
				String query="{call PKG_VACCINE_FORM_DETAILS.SP_ADD_VENDOR_DETAILS (?,?,?,?,?,?,?)}";
				cs=conn.prepareCall(query);
				cs.setString(1, bean.getEmpid());//name
				cs.setString(2, bean.getInput());//vnty
				cs.setString(3, bean.getSpocID());//relationship
				cs.setString(4, bean.getFlag());//phn
				cs.setString(5, bean.getVaccine());//vcty
				cs.registerOutParameter(6, OracleTypes.VARCHAR);
				cs.registerOutParameter(7, OracleTypes.VARCHAR);
				cs.execute();
				
				res= cs.getString(6);
				if(res.equalsIgnoreCase("N")) {
					res= cs.getString(7);
				}
			}
			catch(Exception e) {
				e.printStackTrace();
				log.error(this.getClass().getName() + " - Exception", e);
			}
			finally {
				cs.close();
				conn.close();
			}
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
		try(Connection conn=datasource.getConnection()) {
			CallableStatement cs =null;
			try {
				String query="{call PKG_VACCINE_FORM_DETAILS.SP_SCREEN_REFRESH_SAVE(?,?,?,?,?,?)}";
				cs=conn.prepareCall(query);
				cs.setString(1, bean.getToken());//empp
				cs.setString(2, bean.getCowin());//cowin 
				cs.setString(3, bean.getVaccine());//vaccie status
				cs.setString(4, bean.getExit());//exit
				cs.registerOutParameter(5, OracleTypes.VARCHAR);
				cs.registerOutParameter(6, OracleTypes.VARCHAR);
				cs.execute();
				
				res= cs.getString(6);
				if(res.equalsIgnoreCase("SUCCESS")) {
					res= cs.getString(5);
				}
				else {
					res="0";
				}
			}
			catch(Exception e) {
				e.printStackTrace();
				log.error(this.getClass().getName() + " - Exception", e);
			}
			finally {
				cs.close();
				conn.close();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error(this.getClass().getName() + " - Exception", e);
		}
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
		return res;
	}

	
	
public List<List<List<String>>> onclickdashboardcard(GTBean bean) throws SQLException { //getLiveDashBoardData
		
		log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- Entering");
		List<List<List<String>>> data =null;
		
		try( Connection conn=datasource.getConnection()) {
			ResultSet c1=null;
			ResultSet c2=null;
			List<ResultSet> list =null;
			CallableStatement ccs =null;
			try {
				String query="{call PKG_VACCINE_DASHBOARD.SP_VACCINE_CARD_ONCLICK(?,?,?)}";
				ccs=conn.prepareCall(query);
				conn.setAutoCommit(false);
				ccs.setString(1, bean.getFlag());
				ccs.setString(2, bean.getInput());
				ccs.registerOutParameter(3, OracleTypes.CURSOR);
				
				ccs.execute();
				
					
					 c2 = (ResultSet) ccs.getObject(3);
					
					 list = new ArrayList<ResultSet>();
					data = new ArrayList<List<List<String>>>();
					
					
					list.add(c2);
					data =CommonMethodListOfResulSet(list);
				
			}
			catch(Exception e) {
				e.printStackTrace();
				log.error(this.getClass().getName() + " - Exception", e);
			}
			
			finally {
				log.info(Thread.currentThread().getStackTrace()[1].getMethodName()+"- leaving");
				 if(c1!=null) c1.close();
				 if(c2!=null) c2.close();
				list.clear();
				if(ccs!=null)ccs.close();
				conn.commit();
				if(conn!=null)conn.close();
			}
			}
		catch(Exception e){
			e.printStackTrace();
			log.error(this.getClass().getName() + " - Exception", e);
		}
			
			
		return data;
		
		
	}
	 public List<List<List<String>>> CVCDownload(GTBean bean,String sheet,String datedata,String todate) throws IOException, SQLException {
			// TODO Auto-generated method stub

			CallableStatement cs = null;
			List<List<String>> rows = null;
			 List<List<List<String>>> rowsData = new ArrayList<>();
			 List<ResultSet> result1 = new ArrayList<ResultSet>();
			 
			 List<String> singleList = new ArrayList<String>();
			 List<List<String>> doubleList = new ArrayList<List<String>>();
			 List<List<List<String>>> tripleList = new ArrayList<List<List<String>>>();
			 
			 ResultSet headerdata = null;
			 ResultSet valuedata = null;
			 ResultSetMetaData rsmd = null;
			ResultSet headerdata1=null;

			try (Connection conn=datasource.getConnection()){
				
				String query="";
			
						query="{call PKG_VACCINE_DASHBOARD.SP_CVC_REPORT(?,?,?,?,?,?)}";
						cs=conn.prepareCall(query);
						cs.setString(1, sheet);
						cs.setString(2, datedata);
						cs.setString(3, todate);
						 cs.registerOutParameter(4, OracleTypes.CURSOR);//header1
					 cs.registerOutParameter(5, OracleTypes.CURSOR); //header2
						cs.registerOutParameter(6, OracleTypes.CURSOR); //value
					
						cs.execute();
						valuedata = (ResultSet) cs.getObject(6);
						for(int k=4;k<=5;k++) {
						headerdata = (ResultSet) cs.getObject(k);
				     rsmd = headerdata.getMetaData();
				     doubleList = new ArrayList<List<String>>();
						if(headerdata != null)
						{
							
							while(headerdata.next())
							{
								singleList = new ArrayList<String>();
						for(int i=1;i<=rsmd.getColumnCount();i++)
						{
							singleList.add(headerdata.getString(i));
						}
						doubleList.add(singleList);
					      }
							
						}
						
						tripleList.add(doubleList);
						
						}
						
						rsmd = valuedata.getMetaData();
						doubleList = new ArrayList<List<String>>();
						
						
						if(valuedata != null)
						{
						
							while(valuedata.next())
							{
							singleList = new ArrayList<String>();
						for(int i=1;i<=rsmd.getColumnCount();i++)
						{
							singleList.add(valuedata.getString(i));
						}
						doubleList.add(singleList);
							}
						}
						
						tripleList.add(doubleList);
						
						
				
			} catch (SQLException e) {
				if (log.isInfoEnabled()) {
					log.info(":downloadReportData() - Error Occurred - ", e);
				}
				throw new IOException(e);
			} 
			finally {
				cs.close();
				//headerdata.close();
				//valuedata.close();
			}
			return tripleList;


		}
	 public List<List<List<String>>> reconReportDownload(GTBean bean,String sheet,String datedata,String todate,String spocid) throws IOException, SQLException {
			// TODO Auto-generated method stub

			CallableStatement cs = null;
			List<List<String>> rows = null;
			 List<List<List<String>>> rowsData = new ArrayList<>();
			 List<ResultSet> result1 = new ArrayList<ResultSet>();
			 
			 List<String> singleList = new ArrayList<String>();
			 List<List<String>> doubleList = new ArrayList<List<String>>();
			 List<List<List<String>>> tripleList = new ArrayList<List<List<String>>>();
			 
			 ResultSet headerdata = null;
			 ResultSet valuedata = null;
			 ResultSetMetaData rsmd = null;
			ResultSet headerdata1=null;

			try (Connection conn=datasource.getConnection()){
				
				String query="";
			
						query="{call PKG_VACCINE_DASHBOARD.SP_RECON_REPORT(?,?,?,?,?,?)}";
						cs=conn.prepareCall(query);
						
						cs.setString(1, sheet);
						cs.setString(2,datedata );
						cs.setString(3,todate );
						cs.setNString(4, spocid);
					 cs.registerOutParameter(5, OracleTypes.CURSOR); //header
						cs.registerOutParameter(6, OracleTypes.CURSOR); //value
					
						cs.execute();
						valuedata = (ResultSet) cs.getObject(6);
						
						headerdata = (ResultSet) cs.getObject(5);
				     rsmd = headerdata.getMetaData();
						
						if(headerdata != null)
						{
						
							while(headerdata.next())
							{
							singleList = new ArrayList<String>();
						for(int i=1;i<=rsmd.getColumnCount();i++)
						{
							singleList.add(headerdata.getString(i));
						}
						doubleList.add(singleList);
							}
						}
						tripleList.add(doubleList);
						
						rsmd = valuedata.getMetaData();
						doubleList = new ArrayList<List<String>>();
						
						
						if(valuedata != null)
						{
						
							while(valuedata.next())
							{
							singleList = new ArrayList<String>();
						for(int i=1;i<=rsmd.getColumnCount();i++)
						{
							singleList.add(valuedata.getString(i));
						}
						doubleList.add(singleList);
							}
						}
						
						tripleList.add(doubleList);
						
						
				
			} catch (SQLException e) {
				if (log.isInfoEnabled()) {
					log.info(":downloadReportData() - Error Occurred - ", e);
				}
				throw new IOException(e);
			} 
			finally {
				cs.close();
				//headerdata.close();
				//valuedata.close();
			}
			return tripleList;


		}	
	 public List<List<List<String>>> trendReportDownload(GTBean bean,String sheet,String datedata,String todate,String spocid) throws IOException, SQLException {
			// TODO Auto-generated method stub

			CallableStatement cs = null;
			
			 
			 List<String> singleList = new ArrayList<String>();
			 List<List<String>> doubleList = new ArrayList<List<String>>();
			 List<List<List<String>>> tripleList = new ArrayList<List<List<String>>>();
			 
			 ResultSet headerdata = null;
			 ResultSet valuedata = null;
			 ResultSetMetaData rsmd = null;
			
			try (Connection conn=datasource.getConnection()){
				
				String query="";
			
				query="{call PKG_VACCINE_DASHBOARD.SP_VACCINATION_TREND(?,?,?,?,?)}";
				cs=conn.prepareCall(query);
				/* cs.setString(1, sheet); */
				cs.setString(1,datedata);
				cs.setString(2,todate);
				cs.setString(3, sheet);				
				cs.registerOutParameter(4, OracleTypes.CURSOR); //header
				cs.registerOutParameter(5, OracleTypes.CURSOR); //value			
				cs.execute();
						valuedata = (ResultSet) cs.getObject(5);
						
						headerdata = (ResultSet) cs.getObject(4);
				     rsmd = headerdata.getMetaData();
						
						if(headerdata != null)
						{
						
							while(headerdata.next())
							{
							singleList = new ArrayList<String>();
						for(int i=1;i<=rsmd.getColumnCount();i++)
						{
							singleList.add(headerdata.getString(i));
						}
						doubleList.add(singleList);
							}
						}
						tripleList.add(doubleList);
						
						rsmd = valuedata.getMetaData();
						doubleList = new ArrayList<List<String>>();
						
						
						if(valuedata != null)
						{
						
							while(valuedata.next())
							{
							singleList = new ArrayList<String>();
						for(int i=1;i<=rsmd.getColumnCount();i++)
						{
							singleList.add(valuedata.getString(i));
						}
						doubleList.add(singleList);
							}
						}
						
						tripleList.add(doubleList);
						
						
				
			} catch (SQLException e) {
				if (log.isInfoEnabled()) {
					log.info(":downloadReportData() - Error Occurred - ", e);
				}
				throw new IOException(e);
			} 
			finally {
				cs.close();
				//headerdata.close();
				//valuedata.close();
			}
			return tripleList;


		}	
	
	 public List<List<List<String>>> getGridData(GTBean bean) throws IOException, SQLException {

			// TODO Auto-generated method stub

			CallableStatement cs = null;
		 
			List<List<String>> rows = null;
			 List<List<List<String>>> rowsData = new ArrayList<>();
			 List<ResultSet> result1 = new ArrayList<ResultSet>();
			 
			 List<String> singleList = new ArrayList<String>();
			 List<List<String>> doubleList = new ArrayList<List<String>>();
			 List<List<List<String>>> tripleList = new ArrayList<List<List<String>>>();
			 
			 ResultSet headerdata = null;
			 ResultSet valuedata = null;
			 ResultSetMetaData rsmd = null;
			ResultSet headerdata1=null;

			try (Connection conn=datasource.getConnection()){
				
				String query="";
			
						query="{call PKG_VACCINE_DASHBOARD.SP_TURNOUT_REPORT(?,?,?,?,?)}";
						cs=conn.prepareCall(query);
						/* cs.setString(1, sheet); */
						cs.setString(1,bean.getFromDate());
						cs.setString(2,bean.getToDate());
						 cs.registerOutParameter(3, OracleTypes.CURSOR);//header1
					 cs.registerOutParameter(4, OracleTypes.CURSOR); //header2
						cs.registerOutParameter(5, OracleTypes.CURSOR); //value
					
						cs.execute();
						valuedata = (ResultSet) cs.getObject(5);
						for(int k=3;k<=4;k++) {
						headerdata = (ResultSet) cs.getObject(k);
				     rsmd = headerdata.getMetaData();
				     doubleList = new ArrayList<List<String>>();
						if(headerdata != null)
						{
							
							while(headerdata.next())
							{
								singleList = new ArrayList<String>();
						for(int i=1;i<=rsmd.getColumnCount();i++)
						{
							singleList.add(headerdata.getString(i));
						}
						doubleList.add(singleList);
					      }
							
						}
						
						tripleList.add(doubleList);
						
						}
						
						rsmd = valuedata.getMetaData();
						doubleList = new ArrayList<List<String>>();
						
						
						if(valuedata != null)
						{
						
							while(valuedata.next())
							{
							singleList = new ArrayList<String>();
						for(int i=1;i<=rsmd.getColumnCount();i++)
						{
							singleList.add(valuedata.getString(i));
						}
						doubleList.add(singleList);
							}
						}
						
						tripleList.add(doubleList);
						
						
				
			} catch (SQLException e) {
				if (log.isInfoEnabled()) {
					log.info(":getgridData() - Error Occurred - ", e);
				}
				throw new IOException(e);
			} 
			finally {
				cs.close();
				//headerdata.close();
				//valuedata.close();
			}
			return tripleList;


		
		 
	 }
	 public List<List<List<String>>>  turnoutReportDownload(GTBean bean,String sheet,String datedata,String todate,String spocid) throws IOException, SQLException {
			// TODO Auto-generated method stub

			CallableStatement cs = null;
		 
			List<List<String>> rows = null;
			 List<List<List<String>>> rowsData = new ArrayList<>();
			 List<ResultSet> result1 = new ArrayList<ResultSet>();
			 
			 List<String> singleList = new ArrayList<String>();
			 List<List<String>> doubleList = new ArrayList<List<String>>();
			 List<List<List<String>>> tripleList = new ArrayList<List<List<String>>>();
			 
			 ResultSet headerdata = null;
			 ResultSet valuedata = null;
			 ResultSetMetaData rsmd = null;
			ResultSet headerdata1=null;

			try (Connection conn=datasource.getConnection()){
				
				String query="";
			
						query="{call PKG_VACCINE_DASHBOARD.SP_TURNOUT_REPORT(?,?,?,?,?)}";
						cs=conn.prepareCall(query);
						/* cs.setString(1, sheet); */
						cs.setString(1, datedata);
						cs.setString(2, todate);
						 cs.registerOutParameter(3, OracleTypes.CURSOR);//header1
					 cs.registerOutParameter(4, OracleTypes.CURSOR); //header2
						cs.registerOutParameter(5, OracleTypes.CURSOR); //value
					
						cs.execute();
						valuedata = (ResultSet) cs.getObject(5);
						for(int k=3;k<=4;k++) {
						headerdata = (ResultSet) cs.getObject(k);
				     rsmd = headerdata.getMetaData();
				     doubleList = new ArrayList<List<String>>();
						if(headerdata != null)
						{
							
							while(headerdata.next())
							{
								singleList = new ArrayList<String>();
						for(int i=1;i<=rsmd.getColumnCount();i++)
						{
							singleList.add(headerdata.getString(i));
						}
						doubleList.add(singleList);
					      }
							
						}
						
						tripleList.add(doubleList);
						
						}
						
						rsmd = valuedata.getMetaData();
						doubleList = new ArrayList<List<String>>();
						
						
						if(valuedata != null)
						{
						
							while(valuedata.next())
							{
							singleList = new ArrayList<String>();
						for(int i=1;i<=rsmd.getColumnCount();i++)
						{
							singleList.add(valuedata.getString(i));
						}
						doubleList.add(singleList);
							}
						}
						
						tripleList.add(doubleList);
						
						
				
			} catch (SQLException e) {
				if (log.isInfoEnabled()) {
					log.info(":downloadReportData() - Error Occurred - ", e);
				}
				throw new IOException(e);
			} 
			finally {
				cs.close();
				//headerdata.close();
				//valuedata.close();
			}
			return tripleList;


		}


	public ReponseData insertStagingAndTarget(List<List<RowData>> masterDataList, GTBean bean,
			XSSFWorkbook errorWorkbook,ReponseData res) throws SQLException {
		// TODO Auto-generated method stub
		
		
		List<RowData> cellDataList = null;
		String delQuery = "";
		String insertQuery = "";
		String excelChkFlag = "";
		String successFlag = "";
		ResultSet error_header_rs = null;
		ResultSet error_value_rs = null;
		CallableStatement cs = null;
		PreparedStatement pstmt = null;
		//ReponseData res=new ReponseData();
		try(Connection conn=datasource.getConnection()){
			
			
				String query="{call PKG_VACCINE_DASHBOARD.SP_SCHEDULER_STAGING(?,?)}";
			
				cs=conn.prepareCall(query);
				cs.registerOutParameter(1, OracleTypes.VARCHAR);//delete
				cs.registerOutParameter(2, OracleTypes.VARCHAR);//insert
				cs.execute();
				
					delQuery = (String) cs.getObject(1);
					insertQuery = (String) cs.getObject(2);
					
					pstmt = conn.prepareStatement(delQuery);
					pstmt.executeQuery();
					pstmt.close();
					conn.setAutoCommit(false);
					conn.commit();
					RowData rowData = new RowData();
					List<String> rowDataList = new ArrayList<String>();
					cellDataList = masterDataList.get(0);
					// rowData = cellDataList.get(0);

					for (int i = 1; i < cellDataList.size(); i++) {
						rowData = cellDataList.get(i);
						rowDataList = rowData.getrowList();
						int headerSize = rowData.getrowList().size();
						int count = rowDataList.size();
						if (count == headerSize) {
							pstmt = conn.prepareStatement(insertQuery);
							for (int a = 0; a < count; a++) {
								if (rowDataList.get(a) == null) {
									pstmt.setString(a + 1, "-");
								} else if (rowDataList.get(a) == "") {
									pstmt.setString(a + 1, "-");
								} else if (rowDataList.get(a) == " ") {
									pstmt.setString(a + 1, "-");
								}else if (rowDataList.get(a) == "  ") {
									pstmt.setString(a + 1, "-");
								}
								else if(rowDataList.get(a).equalsIgnoreCase(""))
								{
									pstmt.setString(a + 1, "-");
								}

								else {

									//pstmt.setString(a + 1, rowDataList.get(a));
									pstmt.setString(a + 1, rowDataList.get(a).trim());
								}
							}
							try {
							pstmt.executeQuery();
							pstmt.close();
							conn.setAutoCommit(false);
							conn.commit();
							}
							catch(Exception e) {
								e.printStackTrace();
								
							}

						}
						}
			//Errorcheck
					String query1="{call PKG_VACCINE_DASHBOARD.SP_SCHEDULER_ERRCHK(?,?,?)}";
					
					cs=conn.prepareCall(query1);
					
					cs.registerOutParameter(1, OracleTypes.VARCHAR);//flag
					cs.registerOutParameter(2, OracleTypes.CURSOR);// error header
					cs.registerOutParameter(3, OracleTypes.CURSOR);// error Data
					cs.execute();
					excelChkFlag = (String) cs.getObject(1);
					error_header_rs = (ResultSet) cs.getObject(2);
					error_value_rs = (ResultSet) cs.getObject(3);
				
					
					
					if(excelChkFlag.equalsIgnoreCase("Y")) {
					String query2="{call PKG_VACCINE_DASHBOARD.SP_SCHEDULER_TARGET(?,?)}";
					
					cs=conn.prepareCall(query2);
					
					cs.registerOutParameter(1, OracleTypes.VARCHAR);// output flag
					cs.registerOutParameter(2, OracleTypes.VARCHAR);//no of rows rejected
					cs.execute();
					
					successFlag = (String) cs.getObject(1);
					// rowsInserted=(String) cs.getObject(2);
					 res.setFlag1(successFlag);
					}else {
						 res.setFlag1(excelChkFlag);
						 XSSFWorkbook errorWorkBookxlsx = null;
							errorWorkBookxlsx = addErrorSheetXlsx(error_header_rs, error_value_rs, errorWorkBookxlsx);
							res.setErrorWorkBook(errorWorkBookxlsx);
							
					}
		}catch (SQLException e) {
			if (log.isInfoEnabled()) {
				log.info(":insertStagingAndTarget - Error Occurred - ", e);
			}
		}finally {
			callableStmtList.add(cs);
			cs.close();
			
			
		}
		
		return res;

		
	}
	
	
	public XSSFWorkbook addErrorSheetXlsx(ResultSet headerdata, ResultSet valuedata,
			XSSFWorkbook errorWorkbook) {
		List<String> singleList = new ArrayList<String>();
		 List<List<String>> doubleList = new ArrayList<List<String>>();
		 List<List<List<String>>> tripleList = new ArrayList<List<List<String>>>();
		 
		 ResultSetMetaData rsmd = null;

		List<String> rowList = null;
		int colCount = 0;
		try {
			  rsmd = headerdata.getMetaData();
			if(headerdata != null)
			{
			
				while(headerdata.next())
				{
				singleList = new ArrayList<String>();
			for(int i=1;i<=rsmd.getColumnCount();i++)
			{
				singleList.add(headerdata.getString(i));
			}
			doubleList.add(singleList);
				}
			}
			tripleList.add(doubleList);
			
			rsmd = valuedata.getMetaData();
			doubleList = new ArrayList<List<String>>();
			
			
			if(valuedata != null)
			{
			
				while(valuedata.next())
				{
				singleList = new ArrayList<String>();
			for(int i=1;i<=rsmd.getColumnCount();i++)
			{
				singleList.add(valuedata.getString(i));
			}
			doubleList.add(singleList);
				}
			}
			
			tripleList.add(doubleList);
			
	
			errorWorkbook = createErrorReportXlsx(tripleList);
			
			
			
		} catch (Exception e) {
			if (log.isInfoEnabled()) {
				log.info(":addErrorSheetXlsx() - Error Occured" + e.getMessage());
			}
		}
		
		
		return errorWorkbook;
	}

	// create error report
	public static XSSFWorkbook createErrorReportXlsx(List<List<List<String>>> tripleList) {
		if (log.isInfoEnabled()) {
			log.info(":createErrorReportXlsx() - Entering");
		}
		XSSFWorkbook workbook =new XSSFWorkbook();
		XSSFSheet sheet1 = null;
		XSSFRow row = null;
		List<List<String>> header =tripleList.get(0);
		List<List<String>> values =tripleList.get(1);
		try {

			String sheetNames = "Error_Report";
			
				
			sheet1 = workbook.createSheet(sheetNames);
			

			if(!values.isEmpty()) {
				
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
			//List<List<String>> header =finalList.get(0);
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
			
			//List<List<String>> values =finalList.get(1);
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
			tripleList.clear();
			
		}
		
	
	
		}

		catch (Exception e) {

			// e.printStackTrace();
			if (log.isInfoEnabled()) {
				log.info("createErrorReport() - Error Occurred - " + e.getMessage());
			}
		} finally {
			
		}
		if (log.isInfoEnabled()) {
			log.info( ":createErrorReportXlsx() - Leaving");
		}
		return workbook;
	}


public List<List<String>> getExcelCheck(GTBean bean) throws IOException, SQLException {
		// TODO Auto-generated method stub
		List<List<String>> finalList = new ArrayList<List<String>>();
		List<String> firstLevel = null;
		CallableStatement cs =null;
		 ResultSet headerdata = null;		
		try (Connection conn=datasource.getConnection()){			
			String query="";	
					query="{call PKG_VACCINE_DASHBOARD.SP_SCHEDULER_DOWNLOAD(?)}";
					cs=conn.prepareCall(query);				
					cs.registerOutParameter(1, OracleTypes.CURSOR); //header		
					cs.execute();	
					headerdata = (ResultSet) cs.getObject(1);     
					if (headerdata != null) {
						firstLevel = new ArrayList<String>();
						while (headerdata.next()) {
							firstLevel.add(headerdata.getString(1));
						}
						finalList.add(firstLevel);					
					}		
			
		} catch (SQLException e) {
			if (log.isInfoEnabled()) {
				log.info(":getExcelCheck() - Error Occurred - ", e);
			}
			throw new IOException(e);
		} 
		finally {
			cs.close();
			
		}
		return finalList;		
	}


public List<List<List<String>>> getTrendData(GTBean bean) throws IOException, SQLException {
	// TODO Auto-generated method stub


	// TODO Auto-generated method stub

	CallableStatement cs = null;
 
	
	 List<ResultSet> result1 = new ArrayList<ResultSet>();
	 
	 List<String> singleList = new ArrayList<String>();
	 List<List<String>> doubleList = new ArrayList<List<String>>();
	 List<List<List<String>>> tripleList = new ArrayList<List<List<String>>>();
	 
	 ResultSet headerdata = null;
	 ResultSet valuedata = null;
	 ResultSetMetaData rsmd = null;
	ResultSet headerdata1=null;

	try (Connection conn=datasource.getConnection()){
		
		String query="";
	
				query="{call PKG_VACCINE_DASHBOARD.SP_VACCINATION_TREND_GRAPH(?,?,?,?)}";
				cs=conn.prepareCall(query);
				/* cs.setString(1, sheet); */
				cs.setString(1,bean.getFromDate());
				cs.setString(2,bean.getToDate());
				cs.setString(3,bean.getCity());
				 cs.registerOutParameter(4, OracleTypes.CURSOR);
			
			
				cs.execute();
				valuedata = (ResultSet) cs.getObject(4);
				
				
				rsmd = valuedata.getMetaData();
				doubleList = new ArrayList<List<String>>();
				
				
				if(valuedata != null)
				{
				
					while(valuedata.next())
					{
					singleList = new ArrayList<String>();
				for(int i=1;i<=rsmd.getColumnCount();i++)
				{
					singleList.add(valuedata.getString(i));
				}
				doubleList.add(singleList);
					}
				}
				
				tripleList.add(doubleList);
				
				
		
	} catch (SQLException e) {
		if (log.isInfoEnabled()) {
			log.info(":getgridData() - Error Occurred - ", e);
		}
		throw new IOException(e);
	} 
	finally {
		cs.close();
		//headerdata.close();
		//valuedata.close();
	}
	return tripleList;


		}	
	
}