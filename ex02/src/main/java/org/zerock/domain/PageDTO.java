package org.zerock.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageDTO {

	private int startPage;
	private int endPage;
	private boolean prev, next;
	
	// 전체 레코드 개수
	private int total;
	
	// 페이지 정보, 페이지당 레코드 개수
	private Criteria cri;
	
	public PageDTO(Criteria cri, int total) {
		this.cri = cri;
		this.total = total;
	
		//endPage
		this.endPage = (int)(Math.ceil(cri.getPageNum()/10.0))*10;
	
		this.startPage = this.endPage - 9;
		
		// 전체 페이지 목록에서 마지막 페이지
		int realEnd = (int)(Math.ceil((total*1.0)/cri.getAmount()));
	
		if(realEnd < this.endPage) {
			this.endPage = realEnd;
		}
		
		this.prev = this.startPage > 1;
		this.next = this.endPage < realEnd;
	}
	

}
