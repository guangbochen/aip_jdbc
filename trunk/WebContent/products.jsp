<!-- this pages displays ordering products that is offered by the fc com. -->
<%@ page language="java" contentType="text/html" %>
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


<div class="myContainer">
<div class="container">
	<!--shows navigation bar -->
	<c:choose>
		<c:when test="${empty category}">
		    <p> <a href="index">Home</a> >> View Products </p>
		</c:when>
		<c:otherwise>
		    <p> <a href="index">Home</a> >> <a href="products">View Products</a> >> ${category} </p>
		</c:otherwise>
	</c:choose>
    <hr>
    
	<!--Shows the Shopping cart -->
    <div class="panel panel-success pull-right">
	    <div class="panel-heading">
	        <h1 class="panel-title">Your Shopping Cart</h1>
        </div>
        <div class="panel-body">
	    	<a href="orders"><img alt="shoppingCart" src="assets/img/shoppingcart.gif" />View my shopping cart</a>
        </div>
    </div>
    
	<!--Shows Category filter -->
    <div class="filter"> 
    <h4>Category Filter</h4>
    <form method="POST" action="products?action=category">
	    <select class="selection" name="category">
	    	<c:forEach var="item" items="${productCategory}">
				<option value="${item.category}" >${item.category}</option>
		  	</c:forEach>
		</select>
		<input class="btn btn-default" type="submit" value="Fetch">
	</form>
	</div> <br>
	
	<!--display products table -->
    <table class="table table-bordered table-hover">
        <tr class="warning">
            <th>Category</th>
            <th>Code</th>
            <th>Description</th>
            <th>Price</th>
            <th>Add to cart</th>
        </tr>
		
        <tr>
        <c:forEach var="product" items="${productList}">
            <td>${product.category}</td>
            <td>${product.code}</td>
            <td>${product.description}</td>
            <td>$ ${product.price}</td>
	        <form method="POST" action="products?action=order">
	            <td>
		            <input type="hidden" name="id" value="${product.id }" /> 
		            <input type="hidden" name="category" value="${product.category }" /> 
		            <input type="hidden" name="code" value="${product.code }" /> 
		            <input type="hidden" name="description" value="${product.description }" /> 
		            <input type="hidden" name="price" value="${product.price }" /> 
		            <input type="hidden" name="quantity" value="1" class="myQuantity"/> 
		            <center><input type="submit" value="Add" class="btn btn-success btn-lg addButton"/> </center>
	            </td>
	        </form>
        </tr>
        </c:forEach>
    </table>
    
   <%-- start of page pagination --%> 
    <%--For displaying Previous link except for the 1st page --%>
    <ul class="pagination">
    <c:choose>
	    <c:when test="${currentPage != 1}">
		    <c:choose>
				<c:when test="${empty category}">
					<li><a href="products?page=${currentPage - 1}">Previous</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="products?action=category&category=${category }&page=${currentPage - 1}">Previous</a></li>
				</c:otherwise>
			</c:choose>
	    </c:when>
	    <c:otherwise>
	        <li class="disabled"><a>Previous</a></li>
	    </c:otherwise>
    </c:choose>
    <%--For displaying Page numbers.
    The when condition does not display a link for the current page--%>
    <c:forEach begin="1" end="${noOfPages}" var="i">
        <c:choose>
            <c:when test="${currentPage eq i}">
                <li class="active"><a>${i}</a></li>
            </c:when>
            <c:otherwise>
			    <c:choose>
					<c:when test="${empty category}">
		                <li><a href="products?page=${i}">${i}</a></li>
					</c:when>
					<c:otherwise>
		                <li><a href="products?action=category&category=${category }&page=${i}">${i}</a></li>
					</c:otherwise>
				</c:choose>
            </c:otherwise>
        </c:choose>
    </c:forEach>
    <%--For displaying Next link --%>
    <c:choose>
	    <c:when test="${currentPage lt noOfPages}">
		    <c:choose>
				<c:when test="${empty category}">
			        <li><a href="products?page=${currentPage + 1}">Next</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="products?action=category&category=${category }&page=${currentPage + 1}">Next</a></li>
				</c:otherwise>
			</c:choose>
	    </c:when>
	    <c:otherwise>
	        <li class="disabled"><a>Next</a></li>
	    </c:otherwise>
    </c:choose>
    </ul> <%-- end of page pagination --%> 
    
</div>
</div> <!-- end of body container -->

	<!-- import page footer -->
	<%@ include file="footer.jsp" %>
</body>
</html>