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
import java.util.Locale;

public class DrinkData {
    //---- define drinkList ----
    private List<String> drinkListJp = new ArrayList<>(
        Arrays.asList(
            "コーヒー",
            "コーラ",
            "オレンジジュース",
            "紅茶",
            "ウォーター"
        ));

    //---- define drinkList as Locale en ----
    private List<String> drinkListEn = new ArrayList<>(
        Arrays.asList(
            "Coffee",
            "Coca-Cola",
            "Orange-Juce",
            "Red-Tea",
            "Water"
        ));

    //---- define priceList ----
    private List<Integer> priceList = new ArrayList<>(
        Arrays.asList(130, 110, 110, 130, 100));

    //====== getter, add() ======
    public List<String> getDrinkList(Locale locale) {
        List<String> drinkList;

        if(locale.toString().equals("ja")) {
            drinkList = drinkListJp;
        } else {
            drinkList = drinkListEn;
        }

        return drinkList;
    }//getDrinkList(Locale)

    public List<Integer> getPriceList() {
        return priceList;
    }

}//class

