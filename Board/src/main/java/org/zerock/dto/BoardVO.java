package org.zerock.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class BoardVO {

	private int num;
	private String pass;
	private String name;
	private String email;
	private String title;
	private String content;
	private int readCount;        //readcount
	private Timestamp writeDate;  //writedate
}
