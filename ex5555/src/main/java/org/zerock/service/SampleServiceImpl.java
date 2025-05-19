package org.zerock.service;

import org.springframework.stereotype.Service;

@Service
public class SampleServiceImpl implements SampleService{

	@Override
	public Integer doAdd(String str1, String str2) throws Exception{
		return Integer.parseInt(str1) + Integer.parseInt(str2);
		
	}

	@Override
	public void test() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer doMul(Integer n1, Integer n2) {
		return n1*n2;
	}
}
