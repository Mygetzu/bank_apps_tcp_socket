package model;

import java.io.Serializable;

public class OrderMenu implements Serializable {
    private int id, qty;

    public OrderMenu(int id, int qty) {
        this.id = id;
        this.qty = qty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
