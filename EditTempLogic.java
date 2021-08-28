/**
 * @title venderMachineNeo / model / EditTempLogic.java
 * @content 編集内容の処理をするクラス群
 *
 * @class EditData extends DrinkData   //編集入力をそのまま保存
 * @class EditTempLogic      //各機能のControl。編集結果を保存
 * @class EditAppend extends EditTempLogic   //リスト追加機能
 * @class EditSortIndex extends EditTempLogic//リスト並び替え
 *
 * @author shika
 * @date 2021-08-22
 */
package model;

import java.util.ArrayList;
import java.util.List;

public class EditTempLogic {
    protected EditData editData;
    private EditAppend append;
    private EditSortIndex sort;
    protected List<String> defaultIndexList; //編集前のindex
    protected List<String> indexTempList;
    protected List<String> drinkJpTempList;
    protected List<String> drinkEnTempList;
    protected List<String> priceTempList;

    protected EditTempLogic() { }
    public EditTempLogic(EditData editData) {
        this.editData = editData;
        this.append = new EditAppend();
        this.sort = new EditSortIndex();
    }

    public void setValue() {
        append.setValue();
        this.indexTempList = initList(indexTempList);
        this.drinkJpTempList = initList(drinkJpTempList);
        this.drinkEnTempList = initList(drinkEnTempList);
        this.priceTempList = initList(priceTempList);

        indexTempList.addAll(editData.getIndexEditList());
        drinkJpTempList.addAll(editData.getDrinkJpEditList());
        drinkEnTempList.addAll(editData.getDrinkEnEditList());
        priceTempList.addAll(editData.getPriceEditList());
    }

    public List<String> initList(List<String> list){
        if(list == null) {
            list = new ArrayList<String>();
        } else {
            list.clear();
        }

        return list;
    }//initList()

    //EditorServlet -> this -> EditorAppendクラスに処理を委譲
    public boolean appendOperation(EditMessage editMess) {
        return append.appendOperation(editMess);
    }

    //EditorServlet -> this -> EditorSortIndexクラスに処理を委譲
    public void sortByIndex() {
        sort.sortByIndex();
    }//sortByIndex()

    public boolean checkIndexList(List<String> list) {
        return list.stream()
            .map(this::judgeDigit)
            .allMatch(b -> b);
    }

    public boolean checkPriceList(List<String> list) {
        return list.stream()
            .map(this::judgePrice)
            .allMatch(b -> b);
    }

    //引数 strがすべて数字かどうか判定。「.」は除去。
    protected boolean judgeDigit(String str) {
        str = str.replace(".", "").trim();
        List<Boolean> isDigitList = new ArrayList<>();

        str.chars() //str.charAt()の IntStream
            .mapToObj(c -> Character.isDigit((char) c))
            .forEach(isDigitList::add);

        return isDigitList.stream().allMatch(b -> b);
    }//judgeDigit()

    //入力チェック(priceの適正判定)
    protected boolean judgePrice(String price) {
        //数字かどうか
        boolean isDigit = judgeDigit(price);
        if(!isDigit) {
            return false;
        }

        //整数化して、価格範囲かどうか
        price = price.replace(".", "").trim();
        int priceInt = Integer.valueOf(price);
        if(priceInt <= 0 || 10000 < priceInt) {
            return false;
        }

        return true;
    }//judgePrice()

    //====== getter ======
    public List<String> getIndexTempList() {
        return indexTempList;
    }

    public List<String> getDrinkJpTempList() {
        return drinkJpTempList;
    }

    public List<String> getDrinkEnTempList() {
        return drinkEnTempList;
    }

    public List<String> getPriceTempList() {
        return priceTempList;
    }

    //====== Test main() ======
    public static void main(String[] args) {
        var editData = new EditData();
        var editTemp = new EditTempLogic(editData);

        String[] indexDemoAry = {"0", "25", "20", "30", "40"};
        String[] drinkJpDemoAry = {"あ","い","う","え","お"};
        String[] drinkEnDemoAry = {"A","B","C","D","E"};
        //String[] priceDemoAry = {"100","110","120","130","140"};
        String[] priceDemoAry = {"1.00","1.10","1.20","1.30","1.40"};
        String[] appendDemoAry = {"50","か","F","150"};
        String[] deleteDemoAry = {"0"};
        editData.setListValue(indexDemoAry, drinkJpDemoAry, drinkEnDemoAry,
              priceDemoAry, appendDemoAry, deleteDemoAry);

        boolean canIndex = editTemp.checkIndexList(editData.getIndexEditList());
        boolean canPrice = editTemp.checkPriceList(editData.getPriceEditList());
        System.out.println("canIndex: " + canIndex);
        System.out.println("canPrice: " + canPrice);
    }//main()

}//class

/*
//---- normal data ----
canIndex: true
canPrice: true

//---- index fault ----
String[] indexDemoAry = {"0", "a", "20", "30", "40"};
canIndex: false
canPrice: true

//---- price fault ----
String[] priceDemoAry = {"100","a","120","130","140"};
canIndex: true
canPrice: false

//---- price over ----
String[] priceDemoAry = {"100","-1","120","130","140"};
String[] priceDemoAry = {"100","11000","120","130","140"};
canIndex: true
canPrice: false

//---- price double as en ----
String[] priceDemoAry = {"1.00","1.10","1.20","1.30","1.40"};
canIndex: true
canPrice: true

//---- price double as ja ----
String[] priceDemoAry = {"1.00","1.10","1.20","1.30","1.40"};
java.lang.NumberFormatException: For input string: "1.00"
=> locale別を消去し、すべて「.」を除去に変更し解決す。
*/