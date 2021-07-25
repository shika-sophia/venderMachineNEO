package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class VenderCalc {
    private List<String> drinkList;
    private List<Integer> priceList;
    private List<Boolean> canBuyList = new ArrayList<>();
    private int current;   //現在の投入金額
    private int listSize;//drinkListの要素数

    public VenderCalc(DrinkData data) {
        this.drinkList = data.getDrinkList();
        this.priceList = data.getPriceList();
        this.current = 0;
        this.listSize = drinkList.size();

        //initialize canBuyList
        Stream.generate(() -> false)
            .limit(listSize)
            .forEach(canBuyList::add);
    }//constructor

    //====== Test main() ======
    public static void main(String[] args) {
        var data = new DrinkData();
        var here = new VenderCalc(data);

        System.out.println("drinkList: " + here.drinkList);
        System.out.println("priceList: " + here.priceList);
        System.out.println("canBuyList: " + here.canBuyList);
        System.out.println("current: " + here.current);
        System.out.println("listSize: " + here.listSize);
    }//main()

}//class

/*
drinkList: [コーヒー, コーラ, オレンジジュース, 紅茶, ウォーター]
priceList: [130, 110, 110, 130, 100]
canBuyList: [false, false, false, false, false]
current: 0
listSize: 5

*/