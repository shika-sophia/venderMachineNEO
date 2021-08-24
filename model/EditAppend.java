package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class EditAppend extends EditTempLogic {
    private final int APPEND_SIZE = 4; //appendに必要な追加項目
    private volatile int baseSize;     //append前の drink数

    public void setValue() {
        this.baseSize = editData.drinkJpEditList.size();
        this.defaultIndexList = buildDefaultIndex();
    }//setValue()

    private List<String> buildDefaultIndex() {
        if (defaultIndexList == null) {
            defaultIndexList = new ArrayList<String>();
        }

        this.baseSize = editData.drinkJpEditList.size();
        defaultIndexList.clear();
        IntStream.rangeClosed(0, baseSize)
            .mapToObj(i -> String.valueOf(i * 10))
            .forEach(defaultIndexList::add);

        return defaultIndexList;
    }//buildDefaultIndex()

    public boolean appendOperation(EditMessage editMess) {
        List<String> appList = editData.appendEditList;

        //要素がすべて空なら trueを返し append処理なし
        if(!isOrder(appList)) {
            return true;
        }

        //要素が足りない場合 falseを返し 再入力
        if(appList.size() < APPEND_SIZE) {
            editMess.IncorrectAppend();
            return false;
        }

        String index = appList.get(0);
        String drinkNameJp = appList.get(1);
        String drinkNameEn = appList.get(2);
        String price = appList.get(3);

        boolean canIndex = judgeDigit(index);
        boolean canPrice = judgePrice(price);

        if(!canIndex || !canPrice) {
            editMess.IncorrectDigit();
            return false;
        }

        super.indexTempList.add(index);
        super.drinkJpTempList.add(drinkNameJp);
        super.drinkEnTempList.add(drinkNameEn);
        super.priceTempList.add(price);

        return true;
    }//appendOperation()

    //すべての要素が「""」blankなら false
    protected boolean isOrder(List<String> list) {
        boolean isBlank = list.stream()
            .allMatch(e -> e.isBlank());

        return !(isBlank);
    }//isOrder()

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
    private boolean judgePrice(String price) {
        //数字かどうか
        boolean isDigit = judgeDigit(price);
        if(!isDigit) {
            return false;
        }

        //Locale別の処理
        if(locale.toString().contains("ja")) {
            ;
        } else {
            price = price.replace(".", "").trim();
        }

        //整数化して、価格範囲かどうか
        int priceInt = Integer.valueOf(price);
        if(priceInt <= 0 || 10000 < priceInt) {
            return false;
        }

        return true;
    }//judgePrice()
}//class
