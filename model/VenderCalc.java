package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class VenderCalc {
    private List<String> drinkList;
    private List<Integer> priceList;
    private List<Boolean> canBuyList = new ArrayList<>();
    private List<String> didBuyList = new ArrayList<>();
    private volatile int current;   //現在の金額
    private volatile int input;     //各回の投入金額
    private volatile String buyDrink;//購入品の名前
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

    private void addMoney(int input) {
        this.input = input;
        this.current += input;
    }//addMoney()

    //====== 購入可能かを判定 ======
    public synchronized List<Boolean> judgeCanBuy(int input){
        addMoney(input);    //現在金額に加算
        canBuyList.clear(); //canBuyListをクリア
        priceList.stream()  //price以上なら trueにして canBuyListに代入
            .map(price -> (price <= current))
            .forEach(canBuyList::add);

        return canBuyList;
    }//judgeCanBuy()

    //====== 購入処理 ======
    public void doBuy(int index) {
        buyDrink = drinkList.get(index); //購入品
        int priceDrink = priceList.get(index);  //購入額

        didBuyList.add(buyDrink); //購入リストに追加
        judgeCanBuy(-priceDrink); //現在金額から購入額をマイナス

//        //---- Test print ----
//        System.out.println("current: " + current);
//        System.out.printf("buy (%d)%s: %d \n",
//            index, buyDrink, priceDrink);
//        System.out.println("didBuyList:" + didBuyList);
//        System.out.println("canBuyList:" + canBuyList);
    }//doBuy()

    //====== 返金処理 ======
    public void returnMoney() {
        int temp = current;
        this.current = 0;
        judgeCanBuy(0);
        this.input = temp;
    }//returnMoney

    //====== getter ======
    public int getCurrent() {
        return current;
    }

    public int getInput() {
        return input;
    }

    public String getBuyDrink() {
        return buyDrink;
    }

    public List<String> getDrinkList() {
        return drinkList;
    }

    public List<Boolean> getCanBuyList() {
        return canBuyList;
    }

    public List<String> getDidBuyList() {
        return didBuyList;
    }

//    //====== Test main() ======
//    public static void main(String[] args) {
//        var data = new DrinkData();
//        var here = new VenderCalc(data);
//
//        //---- Test constructor ----
//        System.out.println("drinkList: " + here.drinkList);
//        System.out.println("priceList: " + here.priceList);
//        System.out.println("canBuyList: " + here.canBuyList);
//        System.out.println("current: " + here.current);
//        System.out.println("listSize: " + here.listSize);
//        System.out.println("priceMin: " + here.priceMin);
//        System.out.println();
//
//        //---- Test judgeCanBuy() ----
//        int[] inputMoney = {50, 100, 110, 120, 200};
//        Arrays.stream(inputMoney)
//            .forEach(input -> {
//                here.judgeCanBuy(input);//inputMoneyの各要素を代入
//                here.current = 0;    //毎回初期化
//                System.out.printf("current: %3d | canBuyList: %s \n",
//                    input, here.canBuyList);
//            });
//
//        //---- Test doBuy() ----
//        here.current = 700;
//        int[] indexAry = { 1, 4, 0, 2, 3};
//        Arrays.stream(indexAry)
//            .forEach(here::doBuy);
//    }//main()

}//class

/*
//---- Test constructor ----
drinkList: [コーヒー, コーラ, オレンジジュース, 紅茶, ウォーター]
priceList: [130, 110, 110, 130, 100]
canBuyList: [false, false, false, false, false]
current: 0
listSize: 5
priceMin: 100

//---- Test judgeCanBuy() ----
current:  50 | canBuyList: [false, false, false, false, false]
current: 100 | canBuyList: [false, false, false, false, true]
current: 110 | canBuyList: [false, true, true, false, true]
current: 120 | canBuyList: [false, true, true, false, true]
current: 200 | canBuyList: [true, true, true, true, true]

//---- Test doBuy() ----
current: 590
buy (1)コーラ: 110
didBuyList:[コーラ]
canBuyList:[true, true, true, true, true]
current: 490
buy (4)ウォーター: 100
didBuyList:[コーラ, ウォーター]
canBuyList:[true, true, true, true, true]
current: 360
buy (0)コーヒー: 130
didBuyList:[コーラ, ウォーター, コーヒー]
canBuyList:[true, true, true, true, true]
current: 250
buy (2)オレンジジュース: 110
didBuyList:[コーラ, ウォーター, コーヒー, オレンジジュース]
canBuyList:[true, true, true, true, true]
current: 120
buy (3)紅茶: 130
didBuyList:[コーラ, ウォーター, コーヒー, オレンジジュース, 紅茶]
canBuyList:[false, true, true, false, true]

*/