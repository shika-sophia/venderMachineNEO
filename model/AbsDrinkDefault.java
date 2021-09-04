/*
 * @inheritance ---- 継承関係 ----
 * @class AbsDrinkDefault
 *          ↑
 * @class DrinkData extends AbsDrinkDefault
 *          ↑
 * @class EditData extends DrinkData
 */

package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbsDrinkDefault {
    //---- define drinkList as Locale ja ----
    protected final List<String> drinkListJp = new ArrayList<>(
        Arrays.asList(
            "コーヒー",
            "コーラ",
            "オレンジジュース",
            "紅茶",
            "ウォーター"
        ));

    //---- define drinkList as Locale en ----
    protected final List<String> drinkListEn = new ArrayList<>(
        Arrays.asList(
            "Coffee",
            "Coca-Cola",
            "Orange-Juce",
            "Red-Tea",
            "Water"
        ));

    //---- define priceList ----
    protected final List<Integer> priceList = new ArrayList<>(
        Arrays.asList(130, 110, 110, 130, 100));

    //---- define selectList ----
    protected final List<Integer> selectList = new ArrayList<>(
        Arrays.asList(10, 50, 100, 500, 1000));
}//class
