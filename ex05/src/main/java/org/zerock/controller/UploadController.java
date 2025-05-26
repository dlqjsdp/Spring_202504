package org.zerock.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Log4j
public class UploadController {

	@GetMapping("/uploadForm")
	public void uploadForm() {
		log.info("upload from...........");
	}
	
	@PostMapping("/uploadForm")
	public void uploadFormPost(MultipartFile[] uploadFile, Model model) {
		
		String uploadFolder = "c:\\upload";
		
		for(MultipartFile multipartFile : uploadFile) {
			log.info("---------------------");
			log.info("Upload File Name : " + multipartFile.getOriginalFilename());
			log.info("Upload File size : " + multipartFile.getSize());
			
							// c:\\upload\\000001.jpg
			File savedFile = new File(uploadFolder, multipartFile.getOriginalFilename());
			
			try {
				multipartFile.transferTo(savedFile);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = new Date();
		
		String str = sdf.format(date); //2025-05-26
		return str.replace("-", File.separator); // 윈도우 -> 2025-05-26 -> 2025/05/26
	}
	
	private boolean checkImageType(File file) {
		
		try {
			
			String contentType = Files.probeContentType(file.toPath());
					
			return contentType.startsWith("image");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@GetMapping("/uploadAjax")
	public void uploadAjax() {
		log.info("uploadAjax......");
	}
	
	
	@PostMapping("/uploadAjaxAction")
	public @ResponseBody String uploadAjaxAction(MultipartFile[] uploadFile, Model model) {
		
		String uploadFolder = "c:\\upload";
		
		// c:\\upload\\2025\\05\\26
		File uploadPath = new File(uploadFolder, getFolder());

		if(uploadPath.exists() == false) {
			uploadPath.mkdirs(); //mkdir() -> 하위폴더 1개만 만들때, mkdirs() -> 복수개 하위폴더 생성시
		}
		
		for(MultipartFile multipartFile : uploadFile) {
			log.info("---------------------");
			log.info("Upload File Name : " + multipartFile.getOriginalFilename());
			log.info("Upload File size : " + multipartFile.getSize());

			String uploadFileName = multipartFile.getOriginalFilename();
			
			UUID uuid = UUID.randomUUID();
			
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			
			// c:\\upload\\2025\\05\\26\\001.jpg
			File savedFile = new File(uploadPath, uploadFileName);
			
			try {
				multipartFile.transferTo(savedFile);
				
				// 섬네일 파일 생성
				if(checkImageType(savedFile)) {
					FileOutputStream thumbnail = new FileOutputStream(
							new File(uploadPath , "s_" + uploadFileName)
							);
					
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
					
					thumbnail.close();
				}
				
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return "success";
		
	}
	
	@PostMapping("/uploadAjaxAction_old")
	public @ResponseBody String uploadAjaxAction_old(MultipartFile[] uploadFile, Model model) {
		
		String uploadFolder = "c:\\upload";
		
		for(MultipartFile multipartFile : uploadFile) {
			log.info("---------------------");
			log.info("Upload File Name : " + multipartFile.getOriginalFilename());
			log.info("Upload File size : " + multipartFile.getSize());
			
							// c:\\upload\\000001.jpg
			File savedFile = new File(uploadFolder, multipartFile.getOriginalFilename());
			
			try {
				multipartFile.transferTo(savedFile);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return "success";
		
	}
	
}
