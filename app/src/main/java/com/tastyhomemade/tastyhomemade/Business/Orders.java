package com.tastyhomemade.tastyhomemade.Business;

import java.sql.Date;

/**
 * Created by Raed on 12/6/2016.
 */

public class Orders {
    int Id;
    int Food_Id;
    int User_Id;
    Date RequestDate;
    boolean IsShippingToClient;
    double Shipping_Longitude;
    double Shipping_Latitude;
    int ShippingCountryId;
    String ShippingDistrict;
    String ShippingStreet;
    String ShippingBuilding;
    String ShippingApartment;
    String ShippingOtherDetails;
    Date ShippingDeliveryDate;
    int NumberOfOrders;
    String OrderAddress;
    boolean IsCompleteOrder;
    boolean IsReportDelayed;

    public int getNumberOfOrders() {
        return NumberOfOrders;
    }

    public void setNumberOfOrders(int numberOfOrders) {
        NumberOfOrders = numberOfOrders;
    }

    public Orders() {
    }

    public Orders(int id, int food_Id, int user_Id, Date requestDate, boolean isShippingToClient, double shipping_Longitude, double shipping_Latitude, int shippingCountryId, String shippingDistrict, String shippingStreet, String shippingBuilding, String shippingApartment, String shippingOtherDetails, Date shippingDeliveryDate, int NumberOfOrders, String OrderAddress, boolean IsCompleteOrder, boolean IsReportDelayed) {
        Id = id;
        Food_Id = food_Id;
        User_Id = user_Id;
        RequestDate = requestDate;
        IsShippingToClient = isShippingToClient;
        Shipping_Longitude = shipping_Longitude;
        Shipping_Latitude = shipping_Latitude;
        ShippingCountryId = shippingCountryId;
        ShippingDistrict = shippingDistrict;
        ShippingStreet = shippingStreet;
        ShippingBuilding = shippingBuilding;
        ShippingApartment = shippingApartment;
        ShippingOtherDetails = shippingOtherDetails;
        ShippingDeliveryDate = shippingDeliveryDate;
        NumberOfOrders = NumberOfOrders;
        OrderAddress = OrderAddress;
        IsCompleteOrder = IsCompleteOrder;
        IsReportDelayed = IsReportDelayed;
    }

    public boolean isCompleteOrder() {
        return IsCompleteOrder;
    }

    public void setCompleteOrder(boolean completeOrder) {
        IsCompleteOrder = completeOrder;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getFood_Id() {
        return Food_Id;
    }

    public void setFood_Id(int food_Id) {
        Food_Id = food_Id;
    }

    public int getUser_Id() {
        return User_Id;
    }

    public void setUser_Id(int user_Id) {
        User_Id = user_Id;
    }

    public Date getRequestDate() {
        return RequestDate;
    }

    public void setRequestDate(Date requestDate) {
        RequestDate = requestDate;
    }

    public boolean isShippingToClient() {
        return IsShippingToClient;
    }

    public void setShippingToClient(boolean shippingToClient) {
        IsShippingToClient = shippingToClient;
    }

    public double getShipping_Longitude() {
        return Shipping_Longitude;
    }

    public void setShipping_Longitude(double shipping_Longitude) {
        Shipping_Longitude = shipping_Longitude;
    }

    public double getShipping_Latitude() {
        return Shipping_Latitude;
    }

    public void setShipping_Latitude(double shipping_Latitude) {
        Shipping_Latitude = shipping_Latitude;
    }

    public int getShippingCountryId() {
        return ShippingCountryId;
    }

    public void setShippingCountryId(int shippingCountryId) {
        ShippingCountryId = shippingCountryId;
    }

    public String getShippingDistrict() {
        return ShippingDistrict;
    }

    public void setShippingDistrict(String shippingDistrict) {
        ShippingDistrict = shippingDistrict;
    }

    public String getShippingStreet() {
        return ShippingStreet;
    }

    public void setShippingStreet(String shippingStreet) {
        ShippingStreet = shippingStreet;
    }

    public String getShippingBuilding() {
        return ShippingBuilding;
    }

    public void setShippingBuilding(String shippingBuilding) {
        ShippingBuilding = shippingBuilding;
    }

    public String getShippingApartment() {
        return ShippingApartment;
    }

    public void setShippingApartment(String shippingApartment) {
        ShippingApartment = shippingApartment;
    }

    public String getShippingOtherDetails() {
        return ShippingOtherDetails;
    }

    public void setShippingOtherDetails(String shippingOtherDetails) {
        ShippingOtherDetails = shippingOtherDetails;
    }

    public Date getShippingDeliveryDate() {
        return ShippingDeliveryDate;
    }

    public void setShippingDeliveryDate(Date shippingDeliveryDate) {
        ShippingDeliveryDate = shippingDeliveryDate;
    }

    public String getOrderAddress() {
        return OrderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        OrderAddress = orderAddress;
    }

    public boolean isReportDelayed() {
        return IsReportDelayed;
    }

    public void setReportDelayed(boolean reportDelayed) {
        IsReportDelayed = reportDelayed;
    }
}
