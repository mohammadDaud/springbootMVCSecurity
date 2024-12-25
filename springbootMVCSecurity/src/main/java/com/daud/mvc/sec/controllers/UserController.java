package com.daud.mvc.sec.controllers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.daud.mvc.sec.repositories.UserRepository;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping("/index")
	public String helloUser(Model model) {
		model.addAttribute("title","Home");
		return "/user/user_dashboard";
	}
	
	@RequestMapping("/pdf")
	public ResponseEntity<byte[]> generatePDFReports() {
		JRBeanCollectionDataSource dataSource=new JRBeanCollectionDataSource(userRepository.findAll());
		try {
			String reportPath = "D:\\rep";
			JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/userDetails.jrxml"));
			Map<String, Object> map=new HashMap<>();
			map.put("COMP_NM", "MOHAMMAD DAUD AND Company Pvt.Ltd.");
			map.put("REP_NM", "ALL USER DETAILS REPORT");
			JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, map,dataSource);
			//JasperExportManager.exportReportToPdfFile(jasperPrint,reportPath+"\\userdetails.pdf");
			byte[] data = JasperExportManager.exportReportToPdf(jasperPrint);
			HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Disposition", "inline; filename=citiesreport.pdf");

	        return ResponseEntity
	                .ok()
	                .headers(headers)
	                .contentType(MediaType.APPLICATION_PDF)
	                .body(data);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (JRException e) {
			e.printStackTrace();
			return null;
		}
	}
}
