package per.jxnflzc.web.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import per.jxnflzc.domain.Account;
import per.jxnflzc.domain.Order;
import per.jxnflzc.service.OrderService;

import java.util.List;
import java.util.Map;

/**
 * @author 河木
 * @version v1.0.0
 */
public class OrderAction implements Action, ModelDriven<Order> {
	private Order order = new Order();
	private Integer orderId;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	@Override
	public String execute() throws Exception {
		ActionContext context = ActionContext.getContext();
		Map session = context.getSession();
		OrderService orderService = new OrderService();
		Account account = (Account)session.get("account");

		if(((Integer)order.getOrderId()) == null) {
			List<Order> orderList = orderService.getOrdersByUsername(account.getUsername());
			session.put("orderList", orderList);
			return "list";
		} else {
			List<Order> orderList = (List) session.get("orderList");
			Order findOrder = orderService.getOrder(order.getOrderId());

			session.put("order",findOrder);
			return "view";
		}
	}

	@Override
	public Order getModel() {
		return order;
	}
}
