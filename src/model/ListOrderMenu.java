package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListOrderMenu implements Serializable {
    List<OrderMenu> orderMenuList = new ArrayList<>();

    public ListOrderMenu() {}

    public List<OrderMenu> getOrderMenuList() {
        return orderMenuList;
    }

    public void setOrderMenuList(List<OrderMenu> orderMenuList) {
        this.orderMenuList = orderMenuList;
    }

    public void addListOrderMenu(OrderMenu orderMenu) {
        orderMenuList.add(orderMenu);
    }
}
