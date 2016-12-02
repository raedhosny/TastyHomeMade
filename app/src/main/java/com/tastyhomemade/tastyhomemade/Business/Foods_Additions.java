package com.tastyhomemade.tastyhomemade.Business;

/**
 * Created by Raed on 12/3/2016.
 */

public class Foods_Additions {
    int Id;
    int FoodId;

    public Foods_Additions() {
    }

    int AdditionId;

    public Foods_Additions(int id, int foodId, int additionId) {
        Id = id;
        FoodId = foodId;
        AdditionId = additionId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getFoodId() {
        return FoodId;
    }

    public void setFoodId(int foodId) {
        FoodId = foodId;
    }

    public int getAdditionId() {
        return AdditionId;
    }

    public void setAdditionId(int additionId) {
        AdditionId = additionId;
    }
}
