<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
    <link rel="shortcut icon" href="img/favicon.png">
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <c:choose>
			<c:when test="${category == 1}">콘서트 공연 목록</c:when>
			<c:when test="${category == 2}">연극 공연 목록</c:when>
			<c:when test="${category == 3}">전시회 공연 목록</c:when>
		</c:choose>
        <link href="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/style.min.css" rel="stylesheet" />
        <link href="css/a_performance.css" rel="stylesheet" />
        <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
    </head>
    <body class="sb-nav-fixed">
        <%@ include file="../a_header.jsp" %>
        <div id="layoutSidenav_content">
                    <div class="container-fluid px-4">
                        <h1 class="mt-4">
						<c:choose>
							<c:when test="${category == 1}">콘서트 공연 목록</c:when>
							<c:when test="${category == 2}">연극 공연 목록</c:when>
							<c:when test="${category == 3}">전시회 공연 목록</c:when>
						</c:choose>                        
                        </h1>
                        <ol class="breadcrumb mb-4">
                            <li class="breadcrumb-item active">
                            <c:choose>
								<c:when test="${category == 1}">Concert List</c:when>
								<c:when test="${category == 2}">Theater List</c:when>
								<c:when test="${category == 3}">Museum List</c:when>
							</c:choose>    
                            </li>
                        </ol>
                        
                        <div align="right">
							<a class="btn btn-primary" href="a_performance_ent_insert" id="perInsert" style="margin-bottom:10px;">공연 등록</a>
						</div>
                        
                        <div class="card mb-4">
                            <div class="card-header">
                                <i class="fas fa-table me-1"></i>
									<c:choose>
										<c:when test="${category == 1}">콘서트 공연 목록</c:when>
										<c:when test="${category == 2}">연극 공연 목록</c:when>
										<c:when test="${category == 3}">전시회 공연 목록</c:when>
									</c:choose> 
                            	</div>
                            <div class="card-body">
                                <table id="datatablesSimple">
                                    <thead>
                                        <tr>
                                            <th>공연 번호</th>
                                            <th>공연명</th>
                                            <th>좌석</th>
                                            <th>날짜</th>
                                            <th>시간</th>
                                            <th>가격</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="total" items="${tlist}" varStatus="loop">
                                        <tr>
                                            <td><a id="detail" href="a_performance_ent_detail?tseq=${total.tseq}">${total.tseq}</a></td>
                                            <td><a id="detail" href="a_performance_ent_detail?tseq=${total.tseq}">${total.tname}</a></td>
                                            <td>${total.seat}</td>
                                            <td>
                                            <fmt:formatDate value="${total.sdate}" pattern="yyyy-MM-dd" var="sdate" />
											<fmt:formatDate value="${total.edate}" pattern="yyyy-MM-dd" var="edate" />
											${sdate} ~ ${edate}
                                            </td>
                                            <td>${total.time}</td>
                                            <td>${total.price}</td>
                                        </tr>
                                  </c:forEach>      
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    </div> 
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
        <script src="js/scripts.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/simple-datatables@7.1.2/dist/umd/simple-datatables.min.js"></script>
        <script src="js/datatables-simple-demo.js"></script>
    </body>
</html>