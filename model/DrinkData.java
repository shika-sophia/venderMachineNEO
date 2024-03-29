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
import java.util.List;
import java.util.Locale;

public class DrinkData extends AbsDrinkDefault {
    private double EX_RATE;
    protected List<String> drinkListJp = new ArrayList<>();
    protected List<String> drinkListEn = new ArrayList<>();
    protected List<Integer> priceList = new ArrayList<>();
    protected List<String> priceListStr = new ArrayList<>();
    private List<Integer> selectList = new ArrayList<>();
    private List<String> selectListStr = new ArrayList<>();

    public DrinkData() {
        //defaultのListをコピーして新規にフィールド化
        this.drinkListJp.addAll(super.drinkListJp);
        this.drinkListEn.addAll(super.drinkListEn);
        this.priceList.addAll(super.priceList);
        this.selectList.addAll(super.selectList);
    }//constructor

    //====== getter, setter ======
    public List<String> getDrinkList(Locale locale) {
        List<String> drinkList;

        if(locale.toString().contains("ja")) {
            drinkList = drinkListJp;
        } else {
            drinkList = drinkListEn;
        }

        return drinkList;
    }//getDrinkList(Locale)

    //====== for MainVenderServlet / VenderCalc ======
    public List<Integer> getPriceList() {
        return priceList;
    }

    //====== for MainVenderBundleServlet ======
    public List<String> getPriceListStr(Locale locale, boolean localeChanged, double EX_RATE) {
        return listIntToStr(priceList, priceListStr, locale, localeChanged, EX_RATE);
    }//getPriceListStr()

    public List<String> getSelectListStr(Locale locale, boolean localeChanged, double EX_RATE) {
        return listIntToStr(selectList, selectListStr, locale, localeChanged, EX_RATE);
    }

    //====== List<Integer>を Localeによって分岐した List<String>に変換 ======
    private List<String> listIntToStr(
            List<Integer> listInt, List<String> listStr,
            Locale locale, boolean localeChanged, double EX_RATE){

        //listStrが未作成か Locale,為替レートに変更があれば listStrを作成
        if(listStr.isEmpty()
                || localeChanged
                || this.EX_RATE != EX_RATE) {
            this.EX_RATE = EX_RATE;
            listStr.clear(); //空でなく変更によって作り直す場合にクリア

            //---- Localeによる分岐 ----
            if(locale.toString().contains("ja")) {
                listInt.stream()
                    .map(i -> String.valueOf((int) i))
                    .forEach(listStr::add);
            } else {
                listInt.stream()
                    .map(i -> String.format("%.2f", (double) i / EX_RATE))
                    .forEach(listStr::add);
            }//if locale
        }//if empty

        return listStr;
    }//listIntToStr()

//    //====== Test main() ======
//    public static void main(String[] args) {
//        var data = new DrinkData();
//        Locale locale = new Locale("ja");
//        //Locale locale = new Locale("ja_JP");
//        //Locale locale = new Locale("en");
//        double EX_RATE = 100d;
//        //double EX_RATE = 110d;
//
//        //---- Test constructor ----
//        System.out.println("drinkListJp: " + data.drinkListJp);
//        System.out.println("drinkListEn: " + data.drinkListEn);
//        System.out.println("priceList: " + data.priceList);
//        System.out.println("drinkList: " + data.getDrinkList(locale));
//
//        //---- Test getPriceListStr(), getSelectListStr() ----
//        List<String> list = data.getPriceListStr(locale, false, EX_RATE);
//        List<String> list2 = data.getSelectListStr(locale, false, EX_RATE);
//        System.out.println("locale: " + locale);
//        System.out.println("priceListStr: " + list);
//        System.out.println(
//            "priceListStr: " + data.priceListStr);
//        System.out.println("selectListStr: " + list2);
//        System.out.println(
//            "selectListStr: " + data.selectListStr);
//    }//main()
}//class

/*
//---- Test getPriceListStr(), setPriceListStr() ----
locale: ja
priceListStr: [130, 110, 110, 130, 100]
priceListStr: [130, 110, 110, 130, 100]

locale: ja_jp
priceListStr: [130, 110, 110, 130, 100]
priceListStr: [130, 110, 110, 130, 100]

double EX_RATE = 100d;
locale: en
priceListStr: [1.30, 1.10, 1.10, 1.30, 1.00]
priceListStr: [1.30, 1.10, 1.10, 1.30, 1.00]

double EX_RATE = 110d;
locale: en
priceListStr: [1.18, 1.00, 1.00, 1.18, 0.91]
priceListStr: [1.18, 1.00, 1.00, 1.18, 0.91]

locale: ja
priceListStr: [130, 110, 110, 130, 100]
priceListStr: [130, 110, 110, 130, 100]
selectListStr: [10, 50, 100, 500, 1000]
selectListStr: [10, 50, 100, 500, 1000]

locale: en
priceListStr: [1.30, 1.10, 1.10, 1.30, 1.00]
priceListStr: [1.30, 1.10, 1.10, 1.30, 1.00]
selectListStr: [0.10, 0.50, 1.00, 5.00, 10.00]
selectListStr: [0.10, 0.50, 1.00, 5.00, 10.00]

//---- Test constructor ----
drinkListJp: [コーヒー, コーラ, オレンジジュース, 紅茶, ウォーター]
drinkListEn: [Coffee, Coca-Cola, Orange-Juce, Red-Tea, Water]
priceList: [130, 110, 110, 130, 100]
drinkList: [Coffee, Coca-Cola, Orange-Juce, Red-Tea, Water]
locale: en
priceListStr: [1.30, 1.10, 1.10, 1.30, 1.00]
priceListStr: [1.30, 1.10, 1.10, 1.30, 1.00]
selectListStr: [0.10, 0.50, 1.00, 5.00, 10.00]
selectListStr: [0.10, 0.50, 1.00, 5.00, 10.00]

drinkListJp: [コーヒー, コーラ, オレンジジュース, 紅茶, ウォーター]
drinkListEn: [Coffee, Coca-Cola, Orange-Juce, Red-Tea, Water]
priceList: [130, 110, 110, 130, 100]
drinkList: [コーヒー, コーラ, オレンジジュース, 紅茶, ウォーター]
locale: ja
priceListStr: [130, 110, 110, 130, 100]
priceListStr: [130, 110, 110, 130, 100]
selectListStr: [10, 50, 100, 500, 1000]
selectListStr: [10, 50, 100, 500, 1000]

*/