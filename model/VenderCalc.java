package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class VenderCalc {
    private List<String> drinkList;
    private List<Integer> priceList;
    private List<Boolean> canBuyList = new ArrayList<>();
    private volatile int current;   //現在の投入金額
    private int listSize;//drinkListの要素数
    private int priceMin;//priceListの最小値

    public VenderCalc(DrinkData data) {
        this.drinkList = data.getDrinkList();
        this.priceList = data.getPriceList();
        this.current = 0;
        this.listSize = drinkList.size();
        this.priceMin = priceList.stream()
                .min(Comparator.naturalOrder())
                .get();

        //initialize canBuyList
        Stream.generate(() -> false)
            .limit(listSize)
            .forEach(canBuyList::add);
    }//constructor

    private void addMoney(int addMoney) {
        this.current += addMoney;
    }//addMoney()

    public List<Boolean> judgeCanBuy(int addMoney){
        addMoney(addMoney);        //現在金額に加算

        if(priceMin <= current) {  //現在金額が price最小値よりも大きければ
            canBuyList.clear();    //canBuyListをクリア
            priceList.stream()     //price以上なら trueにして canBuyListに代入
                .map(price -> (price <= current))
                .forEach(canBuyList::add);
        }

        return canBuyList;
    }//judgeCanBuy()

    //====== Test main() ======
    public static void main(String[] args) {
        var data = new DrinkData();
        var here = new VenderCalc(data);

        //---- Test constructor ----
        System.out.println("drinkList: " + here.drinkList);
        System.out.println("priceList: " + here.priceList);
        System.out.println("canBuyList: " + here.canBuyList);
        System.out.println("current: " + here.current);
        System.out.println("listSize: " + here.listSize);
        System.out.println("priceMin: " + here.priceMin);
        System.out.println();

        //---- Test judgeCanBuy() ----
        int[] throwMoney = {50, 100, 110, 120, 200};
        Arrays.stream(throwMoney)
            .forEach(th -> {
                here.judgeCanBuy(th);//throwMoneyの各要素を代入
                here.current = 0;    //毎回初期化
                System.out.printf("current: %3d | canBuyList: %s \n",
                    th, here.canBuyList);
            });

    }//main()

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

*/