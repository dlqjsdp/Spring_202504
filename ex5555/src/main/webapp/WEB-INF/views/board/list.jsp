<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../includes/header.jsp" %>  
  

<!-- 검색창 -->
<div class="search-container">
  <div class="search-top-box">
    <div class="sch-form-wrap flex-inline">
      <div class="input-group">
        <select class="krds-form-select" title="카테고리 선택">
          <option value="">전체</option>
          <option value="공연">공연</option>
          <option value="전시">전시</option>
        </select>
        <div class="sch-input">
          <input type="text" class="krds-input" placeholder="검색어를 입력해주세요" title="검색어 입력">
          <button type="button" class="krds-btn icon">
            <span class="sr-only">검색</span>
            <i class="svg-icon ico-sch"></i>
          </button>
        </div>
      </div>
    </div>
  </div>
</div>
<!-- //검색창 -->

<!-- 목록 -->
<div class="krds-table-wrap">
  <table class="tbl col data">
    <colgroup>
      <col style="width: 5%;">
      <col style="width: 5%;">
      <col style="width: 60%;">
      <col style="width: 5%;">
      <col style="width: 10%;">
      <col style="width: 5%;">
    </colgroup>
    <thead>
      <tr>
        <th scope="col">번호</th>
        <th scope="col">분류</th>
        <th scope="col">공연/행사명</th>
        <th scope="col">작성자</th>
        <th scope="col">작성일</th>
        <th scope="col">조회수</th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <td>1</td>
        <td>공연</td>
        <td><a href="#">뮤지컬 '레미제라블' 정말 감동이었어요!</a></td>
        <td>홍길동</td>
        <td>2025-05-18</td>
        <td>123</td>
      </tr>
    </tbody>
  </table>
</div>
<!-- //목록 -->

<!-- 페이징 영역 -->
<div class="krds-pagination">
  <a class="page-navi prev" href="?pageNum=3">이전</a>
  <div class="page-links">
    <a class="page-link" href="?pageNum=1">1</a>
    <a class="page-link" href="?pageNum=2">2</a>
    <a class="page-link active" href="?pageNum=3"><span class="sr-only">현재페이지 </span>3</a>
    <a class="page-link" href="?pageNum=4">4</a>
    <a class="page-link" href="?pageNum=5">5</a>
    <a class="page-link" href="?pageNum=6">6</a>
    <a class="page-link" href="?pageNum=7">7</a>
    <a class="page-link" href="?pageNum=8">8</a>
    <a class="page-link" href="?pageNum=9">9</a>
    <a class="page-link" href="?pageNum=10">10</a>
  </div>
  <a class="page-navi next" href="?pageNum=4">다음</a>
</div>
<!-- //페이징 영역 -->
