package com.tastyhomemade.tastyhomemade.Business;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by Raed on 12/12/2016.
 */

public class Orders_Actions {
    int Id;
    int OrderId;
    int ActionId;
    Timestamp ActionDate;

    public Orders_Actions() {
    }

    public Orders_Actions(int id, int orderId, int actionId, Timestamp actionDate) {
        Id = id;
        OrderId = orderId;
        ActionId = actionId;
        ActionDate = actionDate;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getOrderId() {
        return OrderId;
    }

    public void setOrderId(int orderId) {
        OrderId = orderId;
    }

    public int getActionId() {
        return ActionId;
    }

    public void setActionId(int actionId) {
        ActionId = actionId;
    }

    public Timestamp getActionDate() {
        return ActionDate;
    }

    public void setActionDate(Timestamp actionDate) {
        ActionDate = actionDate;
    }
}
