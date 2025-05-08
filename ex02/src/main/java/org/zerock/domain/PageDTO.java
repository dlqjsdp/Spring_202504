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
	
	/*
		cri.getPageNum() = 15
		cri.getAmount() = 10
		total = 272
	 */
	
	public PageDTO(Criteria cri, int total) {
		this.cri = cri; //pageNum=15&amount=10
		this.total = total; //272
	
		//endPage							// 15/10.0 = 1.5 | Math.ceil(1.5) = 2.0 | 2.0 * 10 = 20
		this.endPage = (int)(Math.ceil(cri.getPageNum()/10.0))*10;
		this.startPage = this.endPage - 9;	// 20 - 9 = 11
		
		// 전체 페이지 목록에서 마지막 페이지
		int realEnd = (int)(Math.ceil((total*1.0)/cri.getAmount())); // 272/10 = 27.2 | Math.ceil(27.2) = 28
										// 272 / 10 = 27.2 → 28
			//realEnd = 28, endPage = 20
		if(realEnd < this.endPage) { // 28 < 20 → false //조건 충족 안 하므로 endPage 유지됨
			this.endPage = realEnd;
		}
		
		this.prev = this.startPage > 1;  // 11 > 1 → true
		this.next = this.endPage < realEnd; // 20<28 → true
	}
	

}
