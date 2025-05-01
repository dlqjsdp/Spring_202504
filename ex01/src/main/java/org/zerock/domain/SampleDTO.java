package org.zerock.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder  // @Builder 사용시에는 @AllArgsConstructor, @NoArgsConstructor는 세트로 사용
@AllArgsConstructor
@NoArgsConstructor
public class SampleDTO {

	private String name;
	private int age;
	
	/*
	@NoArgsConstructor
	public SampleDTO() {
	
	}
	*/
	
	/*
	@AllArgsConstructor
	public SampleDTO(String name, int age) {
		this.name = name;
		this.age = age;
	}
	*/
}
