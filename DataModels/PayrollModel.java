import java.sql.Connection;import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;

import java.util.Map;

import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.*;


public class PayrollModel {
	
	DataAccess dataobj = new DataAccess();
	
	public HashMap<String,Object> viewPayslip(int empID){
		HashMap<String,Object> dataset = new HashMap<String,Object>();
		try {
			dataset = dataobj.getPayrollDetails(empID);
		} catch (Exception e) {			
			e.printStackTrace();
		}	
		return dataset;	
	}

	public Boolean employeeIDCheck(int emloyeeID) throws SQLException {
		try {
			int details = dataobj.getEmployeeID(emloyeeID);
			if (details != -1)
				return true;
			else
				return false;
		} catch (Exception se) {
			se.getMessage();
			return false;
		}
	}
	
	public void generatePdfReport() {
		
		try{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=sample;integratedSecurity=true");

			InputStream inputStream = new FileInputStream((new File("C:\\Users\\admin\\Source\\ProjectHR\\PayrollDesign.jrxml")));

			JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			Map<String, String> map = new HashMap<String, String>();		

			map.put("jasper Report", "employee Report");

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, con);

			OutputStream outputstream = new FileOutputStream(new File("C:\\Users\\admin\\Source\\ProjectHR\\Payslip.pdf"));

			JasperExportManager.exportReportToPdfStream(jasperPrint, outputstream);	
		}
		catch (Exception e){
			
		}
	}
	
	

	
}