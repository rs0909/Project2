package org.techtown.gps;

public class UserInfo
{
    private int weight;
    private int amountOfwater;

    public UserInfo()
    {
        this.weight = 50;
        amountOfwater = weight * 30;
    }

    public UserInfo(int weight)
    {
        this.weight = weight;
        amountOfwater = weight * 30;
    }


    public void setWeight(int weight)
    {
        this.weight = weight;
        amountOfwater = weight * 30;
    }

    public int getWeight()
    {
        return weight;
    }

    public int getAmountOfwater()
    {
        return amountOfwater;
    }
}
