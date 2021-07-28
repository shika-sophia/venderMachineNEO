/**
 * @see reference / venderMachine_RDD.txt
 * 商品の価格
 *    コーヒー：130円
 *    コーラ：110円
 *    オレンジジュース：110円
 *    紅茶：130円
 *    ウォーターは100円
 */

package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DrinkData {
    //---- define drinkList ----
    private List<String> drinkList = new ArrayList<>(
        Arrays.asList(
            "コーヒー",
            "コーラ",
            "オレンジジュース",
            "紅茶",
            "ウォーター"
        ));

    //---- define priceList ----
    private List<Integer> priceList = new ArrayList<>(
        Arrays.asList(130, 110, 110, 130, 100));

    //====== getter, add() ======
    public List<String> getDrinkList() {
        return drinkList;
    }

    public List<Integer> getPriceList() {
        return priceList;
    }

    public void addDrinkList(String drink) {
        this.drinkList.add(drink);
    }

    public void addPriceList(Integer price) {
        this.priceList.add(price);
    }

}//class

