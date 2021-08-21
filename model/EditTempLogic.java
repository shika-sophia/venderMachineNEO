package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;

public class EditTempLogic {
    private EditData editData;
    private Locale locale;
    private final int APPEND_SIZE = 4; //appendに必要な追加項目
    private volatile int baseSize;     //append前の drink数
    private List<String> defaultIndexList;

    public EditTempLogic(EditData editData, Locale locale) {
        this.editData = editData;
        this.locale = locale;
    }

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

    public void sortByIndex() {

    }//sortByIndex()

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

        boolean canPrice = judgePrice(price);

        editData.indexEditList.add(index);
        //editData.drinkJpEditList.add(drinkNameJp);
        //editData.drinkEnEditList.add(drinkNameJp);
        editData.priceEditList.add(price);

        return true;
    }//appendOperation()


    //すべての要素が「""」blankなら false
    private boolean isOrder(List<String> list) {
        boolean isBlank = list.stream()
            .allMatch(e -> e.isBlank());

        return !(isBlank);
    }//isOrder()

    //入力チェック(priceの適正判定)
    private boolean judgePrice(String price) {
        if(locale.toString().contains("ja")) {

        } else {

        }

        return false;
    }

}//class
