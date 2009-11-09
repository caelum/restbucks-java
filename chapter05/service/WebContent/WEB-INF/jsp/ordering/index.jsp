<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<c:forEach var="order" items="${orderList }">
	<div>
	Order #${order.id }<br/>
	${order.location }<br/>
	<ul>
	<c:forEach var="item" items="${order.items }">
		<li>${item.quantity} x ${item.name } (${item.size }, ${item.milk })</li>
	</c:forEach>
	</ul>
	</div>
</c:forEach>

</html>