<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
</head>
<body>
	<h1>Upload with Ajax</h1>
	
	<div class='uploadDiv'>
		<input type="file" name="uploadFile" multiple="multiple">
	</div>
	
	<button id="uploadBtn">Upload</button>
	
<script type="text/javascript">
	$(document).ready(function(){
		
		let regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$")
		let maxSize = 5242880; //5MB
		
		function checkExtension(fileName, fileSize){
			if(fileSize >= maxSize){
				alert("파일 사이즈 초과");
				return false;
			}
			
			if(regex.test(fileName)){
				alert("해당 종류의 파일은 업로드 할 수 없습니다.")
				return false;
			}
			
			return true;
		}
		
		$("#uploadBtn").on("click", function(e){
			let formData = new FormData();
			let inputFile = $("input[name='uploadFile']");
			
			let files = inputFile[0].files;
			console.log(inputFile)
			console.log("---------------")
			console.log(files)	
			
			for(let i=0; i<files.length; i++){
				
				if(!checkExtension(files[i].name, files[i].size)){
					return false;
				}
					
				formData.append("uploadFile", files[i]);
			}
			console.log("formData");
			console.log(formData);
			
			$.ajax({
				url: "/uploadAjaxAction",
				type: "post",
				processData: false, // 필수 -> 데이터를 문자열로 반환하지말라!
				contentType: false, // contentType 설정하지 않음(자동으로 enctype="multipart/form-data")
				data: formData,
				success: function(result){
					alert(result);
				}
			});
		});
	});
</script>
	
</body>
</html>