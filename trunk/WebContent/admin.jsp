<!--  this page is default admin home page -->
<%@ page language="java" contentType="text/html" import="" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>FC Sportsware</title>
    <!-- inner class css -->
    <link rel="stylesheet" type="text/css" href="assets/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="assets/style.css">
</head>
<body>

	
	
  <c:choose>
	<c:when test="${not empty admin}">
	
  	<!-- access with login as admin -->
	<%@ include file="header.jsp" %>
	<!-- admin page content -->
    <div class="container">
    	<h2>FC Sportsware Admin</h2>
	    <a href="admin?action=listOrders" class="btn btn-primary">
	    	<span class="glyphicon glyphicon-list-alt"></span> View Outstanding Orders
    	</a>
    	
    	<a href="admin?action=searchOrder" class="btn btn-primary">
	    	<span class="glyphicon glyphicon-star"></span> Update Order
    	</a>
    	<hr>
    	
    	<!-- displays outstanding orders -->
    	<c:if test="${not empty orders }">
	    	<table class="table table-bordered table-hover">
	    		<tr class="warning">
	    		<th>Order Number</th>
	    		<th>Surname</th>
	    		<th>Country</th>
	    		<th>Postcode</th>
	    		<th>Grand Total of Order</th>
	    		<th>Status Code</th>
	    		</tr>
		    	<c:forEach var="order" items="${orders}">
		    	<tr>
		    		<td><a href="admin?action=searchOrder&nu=${order.orderNumber }">${order.orderNumber }</a></td>
		    		<td>${order.surname }</td>
		    		<td>${order.country }</td>
		    		<td>${order.postCode }</td>
		    		<td>${order.grandTotal }</td>
		    		<td>${order.status }</td>
		    	</tr>
		    	</c:forEach>
	    	</table>
    	</c:if>
	
    	<!-- Shows successful updating order info -->
    		<c:choose>
    			<c:when test="${not empty updateSuccess }">
			    	<div class="alert alert-success">
    				<p>${updateSuccess }</p>
			    	</div>
    			</c:when>
    			<c:otherwise>
    			</c:otherwise>
    			</c:choose>
    	
	</div>
	<!-- end of container -->
	<%@ include file="footer.jsp" %>
	</c:when>
	<c:otherwise>
	<!-- invalid access to the admin page, shows warnning messag -->
	  <div class="panel panel-danger">
        <div class="panel-heading">
          <h3 class="panel-title">Warning </h3>
        </div>
        <div class="panel-body">
        <h3>Please don't trying to access this link without permission !  2013, All rights reserved by © Guangbo Chen.</h3>
        </div>
      </div>
	</c:otherwise>
  </c:choose>
	
</body>
</html>