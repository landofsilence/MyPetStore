package per.jxnflzc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import per.jxnflzc.domain.Item;
import per.jxnflzc.domain.LineItem;
import per.jxnflzc.domain.Order;

import per.jxnflzc.persistence.ItemDAO;
import per.jxnflzc.persistence.LineItemDAO;
import per.jxnflzc.persistence.OrderDAO;
import per.jxnflzc.util.SessionFactoryUtil;


public class OrderService {
	private SqlSessionFactory sqlSessionFactory = null;
	private SqlSession sqlSession = null;
	private ItemDAO itemDAO;
	private OrderDAO orderDAO = null;
	private LineItemDAO lineItemDAO;

    public void insertOrder(Order order) {
        order.setStatus("ready");
        order.setCourier("shunfeng");
        order.setLocale("#");
		orderDAO.insertOrder(order);
		orderDAO.insertOrderStatus(order);

        for (int i = 0; i < order.getLineItems().size(); i++) {
            LineItem lineItem = (LineItem) order.getLineItems().get(i);
			lineItemDAO.insertLineItem(lineItem);
        }
    }

    public Order getOrder(int orderId) {
		sqlSessionFactory = SessionFactoryUtil.getSqlSessionFactory();
		sqlSession = sqlSessionFactory.openSession();
		orderDAO = sqlSession.getMapper(OrderDAO.class);
		lineItemDAO = sqlSession.getMapper(LineItemDAO.class);
        Order order = orderDAO.getOrder(orderId);
        order.setLineItems(lineItemDAO.getLineItemsByOrderId(orderId));

        for (int i = 0; i < order.getLineItems().size(); i++) {
          LineItem lineItem = (LineItem) order.getLineItems().get(i);

          Item item = itemDAO.getItem(lineItem.getItemId());
          item.setQuantity(itemDAO.getInventoryQuantity(lineItem.getItemId()));
          lineItem.setItem(item);
        }

        return order;
    }

    public int getNewId(){
        return orderDAO.getNewId();

    }

    public List<Order> getOrdersByUsername(String username) {
		sqlSessionFactory = SessionFactoryUtil.getSqlSessionFactory();
		sqlSession = sqlSessionFactory.openSession();
		orderDAO = sqlSession.getMapper(OrderDAO.class);

		List<Order> orderList = orderDAO.getOrdersByUsername(username);
		sqlSession.close();
		return orderList;
    }



}
