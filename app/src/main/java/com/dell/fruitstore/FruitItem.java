package com.dell.fruitstore;

public class FruitItem {

    private String FruitQuant;
    private String FruitName;
    private int FruitPrice;

    public FruitItem(String FruitName, int Fruitprice, String fruitQuant) {
        this.FruitQuant = fruitQuant;
        this.FruitName = FruitName;
        this.FruitPrice = Fruitprice;
    }


    public int getFruitPrice() {
        return FruitPrice;
    }

    public void setFruitPrice(int fruitPrice) {
        this.FruitPrice = fruitPrice;
    }

    public String getFruitName() {
        return FruitName;
    }

    public void setFruitName(String fruitName) {
        this.FruitName = fruitName;
    }

//
//    public FruitItem(String className, String subjectName) {
//        this.FruitPrice = className;
//        this.FruitName = subjectName;
//    }

    public String getFruitQuant() {
        return FruitQuant;
    }

    public void setFruitQuant(String fruitQuant) {
        this.FruitQuant = fruitQuant;
    }
}
