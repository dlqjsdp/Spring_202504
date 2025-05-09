<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../includes/header.jsp" %>  
  
<div class="row">
    <div class="col-lg-12">
        <h1 class="page-header">Tables</h1>
    </div>
    <!-- /.col-lg-12 -->
</div>
<!-- /.row -->
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                Board Read Page
            </div>
            <!-- /.panel-heading -->
            <div class="panel-body">
				<form role="form" action="/board/modify" method="post">
				
                	<input type="hidden" name="pageNum" value='<c:out value="${cri.pageNum}" />'>
                	<input type="hidden" name="amount" value='<c:out value="${cri.amount}" />'>
                	<input type="hidden" name="keyword" value='<c:out value="${cri.keyword}" />'>
                	<input type="hidden" name="type" value='<c:out value="${cri.type}" />'>
            		
	            	<div class="form-group">
	            		<label>Bno</label><input class="form-control" name="bno"
	            				value="<c:out value='${board.bno}' />" readonly="readonly">
	           		</div>
	            
	           	 	<div class="form-group">
	            		<label>Title</label><input class="form-control" name="title"
	            				value="<c:out value='${board.title}' />">
	           		</div>
	           		<div class="form-group">		
	            		<label>Text ares</label>
	            		<textarea rows="3" class="form-control" name="content" >
	            			<c:out value='${board.content}' />
	            		</textarea>
	            	</div>
	            	<div class="form-group">
	            		<label>Writer</label><input class="form-control" name="writer"
	            				value="<c:out value='${board.writer}' />" readonly="readonly">
	            	</div>	
	            	
	            		<button type="submit" data-oper='modify' class="btn btn-default">Modify</button>
	            		<button type="submit" data-oper='remove' class="btn btn-danger">Remove</button>	            		
	            		<button type="submit" data-oper='list' class="btn btn-info">List</button>
            	 </form>
            </div>
            <!-- /.panel-body -->
        </div>
        <!-- end panel -->
    </div>
    <!-- /.col-lg-12 -->
</div>
<!-- /.row -->

<script>
	$(document).ready(function(){
		let formObj = $("form");
		
		$("button").on("click", function(e){
			e.preventDefault(); // 이벤트의 기본 동작을 막는 역할
			// console.log("=====");
			
			let operation = $(this).data("oper");
			// console.log("=operation===");
			
			if(operation == 'remove'){
				formObj.attr("action", "/board/remove")
			}else if(operation == 'list'){
				formObj.attr("action", "/board/list").attr("method", "get");
				let pageNumTag = $("input[name='pageNum']").clone();
				let amountTag = $("input[name='amount']").clone();
				let typeTag = $("input[name='type']").clone();
				let keywordTag = $("input[name='keyword']").clone();
				
				formObj.empty(); // input 태그 name속성값을 클리어.
				formObj.append(pageNumTag);
				formObj.append(amountTag);
				
				formObj.append(typeTag);
				formObj.append(keywordTag);
				
			}
			formObj.submit();
		});
 		
	});
</script>

<%@ include file="../includes/footer.jsp" %>


        