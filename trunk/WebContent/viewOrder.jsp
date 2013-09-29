<!-- this page allows customer to check order by order number and their surname -->
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
<!-- import page header -->
<%@ include file="header.jsp" %>


    <div class="container">
    
    	<!-- shows the navigation title -->
        <p><a href="index">Home</a> >> View Order</p>
        <hr/>
        
        
    	<!-- displays the searching form for checking the value -->
    	<div class="panel panel-info">
          <div class="panel-heading">
            <h1 class="panel-title"><span class="glyphicon glyphicon-screenshot"></span> Searching My Orders</h1>
          </div>
          <div class="panel-body">
    		<!-- displays the searching form for checking the value -->
		    <h4>Please fill in the searching details </h4> <br>
    		<form method="post" action="viewOrder?action=check">
    			<div class="${error }">
	    			<label class="control-label"> Order Number :</label>
	    			<input type="text" name="orderNumber" value="${number }" class="form-control" placeholder="${emptyNum }" id="orderNumber">
	    		</div>
    			<div class="${error }">
		   			<label class="control-label"> Surname :</label>
		   			<input type="text" name="surname" value="${name }" class="form-control" placeholder="${emptyName }" id="surname">
    			</div>
    			<br>
    			<input type="submit" class="btn btn-default">
    		</form>
          </div>
        </div> <!-- end of info panel -->
        
        <!-- displays the searching result if it has found -->
	    <h2><span class="glyphicon glyphicon-search"></span> Searching Result :</h2>
        <c:choose>
		  <c:when test="${not empty order && not empty orderedProducts}">
			  <p>
			  	<strong> Dear  ${sname }: The ordering details for the "${snumber}" is shown as below... </strong>
			  </p>
			  
		  	<table class="table table-bordered">
			  	<tr class="active">
		            <th>Category</th>
		            <th>Code</th>
		            <th>Description</th>
		            <th>Quantity</th>
		            <th>Line Total</th>
	            </tr>
	            
			  	<c:forEach var="op" items="${orderedProducts}">
				  	<tr>
			            <td>${op.product.category}</td>
			            <td>${op.product.code}</td>
			            <td>${op.product.description}</td>
			            <td>${op.quantity}</td>
			            <td>$ ${op.lineTotal}</td>
		            </tr>
			  	</c:forEach>
			  	
			  	<tr class="warning">
		            <td></td>
		            <td></td>
		            <td></td>
		            <td>Order Status: ${order.status }</td>
		            <td>Grand Total: $ ${grandTotal }</td>
	            </tr>
			</table>
		  </c:when>
		  
		  <c:otherwise>
		    <p> Not relevant order is provided ....</p>
		  </c:otherwise>
		</c:choose>
        
        
	</div> <!-- end of body container -->
	
	
<!-- import page footer -->
<%@ include file="footer.jsp" %>
</body>
</html>