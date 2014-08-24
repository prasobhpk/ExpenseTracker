package com.pk.et.exp.web.controllers;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/fileupload")
public class FileUploadController {

	@RequestMapping(method=RequestMethod.GET)
	public String fileUploadForm() {
		return "uploadForm";
	}

	@RequestMapping(method=RequestMethod.POST)
	public @ResponseBody String processUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request, Model model) throws IOException, InvalidFormatException {
		StringBuilder sb=new StringBuilder("File '" + file.getOriginalFilename() + "' uploaded successfully");
		
		try {

			Workbook workBook = WorkbookFactory.create(file.getInputStream());
			Sheet sheet = workBook.getSheetAt(0);
			Iterator<Row> rows = sheet.iterator();
			while (rows.hasNext()) {
				sb.append("<row>\n");
				Row row = rows.next();
				
				// once get a row its time to iterate through cells.
				Iterator<Cell> cells = row.cellIterator();

				while (cells.hasNext()) {
					Cell cell = cells.next();

					sb.append("<cell>" + cell + "</cell>\n");
					
				}
				sb.append("</row>\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
}

