
import java.io.File;
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

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;







public class payrollTest {




public static void main(String[] args) {

		

		try{
			Date d = new Date();
			if(d.getDate() == 21){
				
			}

			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost;databaseName=sample;integratedSecurity=true");




			InputStream inputStream = new FileInputStream((new File("C:\\Users\\admin\\Source\\ProjectHR\\PayrollpdfDesign.jrxml")));




			JasperDesign jasperDesign = JRXmlLoader.load(inputStream);

			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);




			Map<String, String> map = new HashMap<String, String>();			

			map.put("jasper Report", "employee Report");




			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, con);

			OutputStream outputstream = new FileOutputStream(new File("C:\\Users\\admin\\Source\\ProjectHR\\Payslip.pdf"));




			JasperExportManager.exportReportToPdfStream(jasperPrint, outputstream);




		}

		catch (Exception e){}




	}
}