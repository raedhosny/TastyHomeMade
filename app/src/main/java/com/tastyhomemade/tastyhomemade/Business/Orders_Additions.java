package com.tastyhomemade.tastyhomemade.Business;

/**
 * Created by Raed on 12/9/2016.
 */

public class Orders_Additions {
    int Id;
    int OrderId;
    int AdditionId;
    int Quantity;

    public Orders_Additions() {
    }

    public Orders_Additions(int id, int orderId, int additionId,int Quantity) {
        Id = id;
        OrderId = orderId;
        AdditionId = additionId;
        Quantity = Quantity;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
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

    public int getAdditionId() {
        return AdditionId;
    }

    public void setAdditionId(int additionId) {
        AdditionId = additionId;
    }
}
