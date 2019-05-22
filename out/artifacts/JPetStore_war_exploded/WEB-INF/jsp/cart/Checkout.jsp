<%@ include file="../common/IncludeTop.jsp"%>

<div id="BackLink">
	<a href="viewCart">Return to Shopping Cart</a>
</div>

<div id="Catalog">

		<h2>Checkout Summary</h2>

		<table>

			<tr>
				<td><b>Item ID</b></td>
				<td><b>Product ID</b></td>
				<td><b>Description</b></td>
				<td><b>In Stock?</b></td>
				<td><b>Quantity</b></td>
				<td><b>List Price</b></td>
				<td><b>Total Cost</b></td>
			</tr>

			<c:forEach var="cartItem" items="${sessionScope.cart.cartItemList}">
				<tr>
					<td>
                        <a href="viewItem?itemId=${cartItem.item.itemId}">${cartItem.item.itemId}</a>
                    </td>
					<td>
                        <a href="viewProduct?productId=${cartItem.item.product.productId}">${cartItem.item.product.productId}</a>
					</td>
					<td>${cartItem.item.attribute1} ${cartItem.item.attribute2}
					${cartItem.item.attribute3} ${cartItem.item.attribute4}
					${cartItem.item.attribute5} ${cartItem.item.product.name}</td>
					<td>${cartItem.inStock}</td>
					<td>${cartItem.quantity}</td>
					<td><fmt:formatNumber value="${cartItem.item.listPrice}"
						pattern="$#,##0.00" /></td>
					<td><fmt:formatNumber value="${cartItem.total}"
						pattern="$#,##0.00" /></td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="7">Sub Total: <fmt:formatNumber
					value="${sessionScope.cart.subTotal}" pattern="$#,##0.00" /></td>
			</tr>
		</table>
    <c:if test="${sessionScope.cart.numberOfItems > 0}">

        <a href="NewOrder1" class="Button">Proceed to Checkout</a>
    </c:if>

</div>

<%@ include file="../common/IncludeBottom.jsp"%>