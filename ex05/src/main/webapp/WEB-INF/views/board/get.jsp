<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
                	<div class="form-group">
                		<label>Bno</label><input class="form-control" name="bno" 
                				value="<c:out value='${board.bno}' />" readonly="readonly" >
                	</div>
                	
                	<div class="form-group">
                		<label>Title</label><input class="form-control" name="title" 
                				value="<c:out value='${board.title}' />" readonly="readonly">
                	</div>
                	<div class="form-group">
                		<label>Text area</label>
                		<textarea rows="3" class="form-control" name="content" readonly="readonly">
                			<c:out value='${board.content}' />
                		</textarea>
                	</div>
                	<div class="form-group">
                		<label>Writer</label><input class="form-control" name="writer"
                				value="<c:out value='${board.writer}' />" readonly="readonly">
                	</div>
                	
                	<button data-oper='modify' class="btn btn-info">Modify</button>
                	<button data-oper='list' class="btn btn-default">List</button>
                	
                	<form id="operForm" action="/board/modify" method="get">
                		<input type="hidden" id="bno" name="bno" value='<c:out value="${board.bno}" />'>
                		<input type="hidden" name="pageNum" value= '<c:out value="${cri.pageNum}"/>'>
                		<input type="hidden" name="amount" value='<c:out value="${cri.amount}"/>'>
                		<input type="hidden" name="keyword" value='<c:out value="${cri.keyword}" />'>
                		<input type="hidden" name="type" value="${cri.type}">
                	</form>
            </div>
            <!-- end panel-body -->           
        </div>
        <!-- end panel -->
    </div>
    <!-- /.col-lg-12 -->
</div>
<!-- /.row -->


<!-- /.row 댓글 처리-->
<div class="row">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <i class="fa fa-comments fa-fw"></i>Reply
                <button id='addReplyBtn' class='btn btn-primary btn-xs pull-right'>댓글 등록</button>
            </div>
            <!-- /.panel-heading -->
            <div class="panel-body">
                <ul class="chat">
                </ul>
            </div>
            <!-- end panel-body -->
			<div class="panel-footer">
			</div>
        </div>
        <!-- end panel -->
    </div>
    <!-- /.col-lg-12 -->
</div>
<!-- /.row -->


<!-- The Modal -->
<div class="modal" id="myModal">
  <div class="modal-dialog">
    <div class="modal-content">
    
      <!-- Modal Header -->
      <div class="modal-header">
        <h4 class="modal-title">Modal title</h4>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>
      
      <!-- Modal body -->
      <div class="modal-body">
	      	<div class="form-group">
	      		<label>Reply</label>
	      		<input class="form-control" name="reply" value="New Reply!!!">
	      	</div>
	      	<div class="form-group">
	      		<label>Replyer</label>
	      		<input class="form-control" name="replyer" value="Replyer">
	      	</div>
	      	<div class="form-group">
	      		<label>Reply Date</label>
	      		<input class="form-control" name="replyDate" value="">
	      	</div>
      </div>
      
      <!-- Modal footer -->
      <div class="modal-footer">
      	<button id="modalRegisterBtn" type="button" class="btn btn-primary">Register</button>
        <button id="modalModBtn" type="button" class="btn btn-info">Modify</button>
        <button id="modalRemoveBtn" type="button" class="btn btn-danger">remove</button>
        <button id="modalCloseBtn" type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
      
    </div>
  </div>
</div> <!-- end The Modal -->


<script type="text/javascript" src="/resources/js/reply.js"></script>

<script type="text/javascript">
	
	$(document).ready(function(){
		let bnoValue = '<c:out value="${board.bno}" />';
		
		let replyUL = $(".chat");
		
		showList(1);
		
		function showList(page){
			
			replyService.getList(
				{bno:bnoValue, page: page ||1},
				
				function(replyCnt, list){
					
					if(page == -1){ //마지막 페이지 이동
						pageNum = Math.ceil(replyCnt/10.0); //  172/10 = 17.2 = 18
						showList(pageNum);
						return;
					}
					
					let str="";
					
					if(list == null || list.length == 0){
						replyUL.html("");
						return;
					}
					for(let i=0; i<list.length; i++){
						str += "<li class='left clearfix' data-rno='"+list[i].rno+"'>"
						str += "<div>"
						str += "<div class='header'>"
						str += "<strong class='primary-font'>"+list[i].replyer+"</strong>"
						str += "<small class='pull-right text-muted'>"+ replyService.displayTime(list[i].replyDate)+"</small>"
						str += "</div>"
						str += "<p>"+list[i].reply+"</p>"
						str += "</div></li>"
					}
					replyUL.html(str);
					
					showReplyPage(replyCnt); //페이징 처리 호출
				}	
			)
		}; // end showList()
		
		let modal = $(".modal");
		let modalInputReply = modal.find("input[name='reply']");
		let modalInputReplyer = modal.find("input[name='replyer']");
		let modalInputReplyDate = modal.find("input[name='replyDate']");
		
		let modalRegisterBtn = $("#modalRegisterBtn");
		let modalModBtn = $("#modalModBtn")
		let modalRemoveBtn = $("#modalRemoveBtn")
		let modalCloseBtn = $("#modalCloseBtn")
		
		//댓글 등록 화면
		$("#addReplyBtn").on("click", function(e){
			modal.find("input").val("");
			modalInputReplyDate.closest("div").hide();
			modal.find("button[id != 'modalCloseBtn']").hide();
			
			modalRegisterBtn.show();
			
			modal.modal("show");
		});
		
		
		//댓글 처리(DB저장)
		modalRegisterBtn.on("click", function(e){
			
			let reply = {
					reply: modalInputReply.val(),
					replyer: modalInputReplyer.val(),
					bno: bnoValue
			}
			
			replyService.add(reply, function(result){
				alert(result);
				modal.find("input").val("");
				modal.modal("hide");
				
				showList(-1);
			});
		});
		
		// 댓글 클릭 이벤트 처리 - 이벤트 위임
		$(".chat").on("click", "li", function(e){
			let rno = $(this).data('rno');
			// console.log(rno);
			
			replyService.get(rno,
				function(reply){
					modalInputReply.val(reply.reply);
					modalInputReplyer.val(reply.replyer);
					modalInputReplyDate.val(replyService.displayTime(reply.replyDate))
					.attr("readonly", "readonly");
					modal.data("rno", reply.rno);
					
					modal.find("button[id != 'modalCloseBtn']").hide();
					modalModBtn.show();
					modalRemoveBtn.show();
					
					modal.modal("show");
				}
			);
		});
		
		// 댓글 수정 이벤트 처리
		modalModBtn.on("click", function(e){
			
			let reply = {
					rno:modal.data('rno'),
					reply:modalInputReply.val()
			};
			replyService.update(reply, function(result){
				alert(result);
				modal.modal("hide");
				showList(pageNum);
			})
		});
		
		// 댓글 삭제 이벤트 처리
		modalRemoveBtn.on("click", function(e){
			
			let rno = modal.data('rno');
			
			replyService.remove(rno, function(result){
				alert(result);
				modal.modal("hide");
				showList(pageNum);
			})
		});
		
		
		//페이징 처리
	      let pageNum = 1;
	      let replyPageFooter = $(".panel-footer");
	      
	      function showReplyPage(replyCnt){
	      
	         let endNum = Math.ceil(pageNum /10.0) * 10;
	         let startNum = endNum - 9;
	         
	         let prev = startNum != 1;  //이전버튼
	         let next = false;          //다음버튼
	         
	         //real page( 끝 페이지 재계산)
	         if(endNum * 10 >= replyCnt){
	            endNum = Math.ceil(replyCnt/10.0);
	         }
	         
	         //next버튼 유무 조건?
	         if(endNum *10 < replyCnt){ 
	            next = true;
	         }
	         
	         let str = "<ul class='pagination pull-right'>";
	         
	         if(prev){
	            str+= "<li class='page-item'>"
	            str+= "<a class='page-link' href='"+(strNum-1)+"'>Previous</a></li>";
	         }
	         
	         for(let i=startNum; i<=endNum; i++){
	            let active = pageNum == i? "active":"";
	            
	            str+= "<li class='page-item "+active+"'><a class='page-link' href='"+i+"'>" + i + "</a></li>";
	         }
	         
	         if(next){
	            str+= "<li class='page-item'>"
	            str+= "<a class='page-link' href='"+(endNum+1)+"'>Next</a></li>";
	         }
	         
	         str+= "</ul>";
	         
	         console.log(str);
	         
	         replyPageFooter.html(str);
	         
	      }  //end showReplyPage
	      
	      replyPageFooter.on("click", "li a", function(e){
	          e.preventDefault();
	          
	          let targetPageNum = $(this).attr("href");
	          
	          pageNum = targetPageNum;
	          
	          showList(pageNum);
	          
	       }); //end replyPageFooter
		
		
	});
	
	

	/*
	replyService.update(
			{rno: 41, reply: "방금 댓글 내용 수정"},
			function(result){
				alert(result)
			}			
	);
	*/

	/*
	replyService.get(41, 
			function(result){
					console.log(result);
	});
	*/
	
	/*
	replyService.remove(42,
			function(count){
				if(count == 'success'){
					alert("삭제 성공");
				}
			},
			function(err){
				alert("ERROR............." + err);
			}
	)
	*/
	
	/*
	replyService.getList({bno: bnoValue, page: 1},
			function(list){
			for(let i=0; i<list.length; i++){
				console.log(list[i]);
			}
		}
	);
	*/
	
	
	/*
	replyService.add(
		{reply:"JS Test", replyer: "tester", bno:7},
		function(result){
			alert("Result : " + result);
		},
		function(error){
			alert("error : " + error);
		}
	)
	*/
	
	

</script>


<script type="text/javascript">
	$(document).ready(function(){
		
		let operForm = $("#operForm");
		
		console.log(replyService);
		
		$("button[data-oper='modify']").on("click", function(e){
			operForm.attr("action", "/board/modify").submit();
		});
		
		$("button[data-oper='list']").on("click", function(e){
			operForm.find("#bno").remove();
			operForm.attr("action", "/board/list").submit();
		});
	});	
</script>



<%@ include file="../includes/footer.jsp" %>
