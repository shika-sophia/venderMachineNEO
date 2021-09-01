package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;

public class EditAppend {
    private EditData editData;
    private EditTempLogic editTemp;
    private volatile int baseSize;     //append前の drink数
    private List<String> defaultIndexList;

    public EditAppend(EditData editData, EditTempLogic editTemp) {
        this.editData = editData;
        this.editTemp = editTemp;
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
        IntStream.range(0, baseSize)
            .mapToObj(i -> String.valueOf(i * 10))
            .forEach(defaultIndexList::add);

        return defaultIndexList;
    }//buildDefaultIndex()

    public boolean appendOperation(EditMessage editMess) {
        List<String> appList = editData.appendEditList;

        //要素がすべて空なら falseを返し append処理なし
        if(!isOrder(appList)) {
            return false;
        }

        //要素が足りない場合 falseを返し 再入力
        if(appList.stream().anyMatch(e -> e.equals(""))) {
            editMess.IncorrectAppend();
            return false;
        }

        String index = appList.get(0);
        String drinkNameJp = appList.get(1);
        String drinkNameEn = appList.get(2);
        String price = appList.get(3);

        boolean canIndex = editTemp.judgeDigit(index);
        boolean isIndex = judgeStack(index);
        boolean canPrice = editTemp.judgePrice(price);

        if(!canIndex || !isIndex || !canPrice ) {
            editMess.IncorrectInput();
            return false;
        }

        editTemp.indexTempList.add(index);
        editTemp.drinkJpTempList.add(drinkNameJp);
        editTemp.drinkEnTempList.add(drinkNameEn);
        editTemp.priceTempList.add(price);

        return true;
    }//appendOperation()

    private boolean judgeStack(String index) {
        //defaultIndexListに含まれているなら false
        return !(defaultIndexList.contains(index));
    }

    //すべての要素が「""」blankなら false
    protected boolean isOrder(List<String> list) {
        boolean isBlank = list.stream()
            .allMatch(e -> e.isBlank());

        return !(isBlank);
    }//isOrder()

    //====== Test main() ======
    public static void main(String[] args) {
        Locale locale = new Locale("en");
        var editData = new EditData();
        var editTemp = new EditTempLogic(editData);
        var editMess = new EditMessage(locale);

        String[] indexDemoAry = {"0", "25", "20", "30", "40"};
        String[] drinkJpDemoAry = {"あ","い","う","え","お"};
        String[] drinkEnDemoAry = {"A","B","C","D","E"};
        String[] priceDemoAry = {"100","110","120","130","140"};
        String[] appendDemoAry = {"50","か","F","150"};
        //String[] appendDemoAry = {"10","か","F","150"};
        //String[] appendDemoAry = {"","か","F","150"};
        //String[] appendDemoAry = {"","","",""};
        String[] deleteDemoAry = {"0"};
        editData.setListValue(indexDemoAry, drinkJpDemoAry, drinkEnDemoAry,
              priceDemoAry, appendDemoAry, deleteDemoAry);
        editTemp.setValue();

        boolean canAppend = editTemp.appendOperation(editMess);
        System.out.println("canAppend: " + canAppend);
        System.out.println("baseSize: " + editTemp.append.baseSize);
        System.out.println("defaultIndexList: " + editTemp.append.defaultIndexList);
        System.out.println("appendEditList: " + editData.appendEditList);
        System.out.println("appendEditList.size(): " + editData.appendEditList.size());

        System.out.println("indexTempList: " + editTemp.indexTempList);
        System.out.println("drinkJpTempList: " + editTemp.drinkJpTempList);
        System.out.println("drinkEnTempList: " + editTemp.drinkEnTempList);
        System.out.println("priceTempList: " + editTemp.priceTempList);
        System.out.println("editMsg: " + editMess.getEditMsg());
    }//main()

}//class

/*
canAppend: true
appendEditList: [50, か, F, 150]
appendEditList.size(): 4

System.out.println("indexTempList: " + editTemp.indexTempList);
indexTempList: [0, 25, 20, 30, 40]
drinkJpTempList: [あ, い, う, え, お]
drinkEnTempList: [A, B, C, D, E]
priceTempList: [100, 110, 120, 130, 140]
editMsg: null

System.out.println("indexTempList: " + editTemp.append.indexTempList);
indexTempList: [0, 25, 20, 30, 40, 50]
drinkJpTempList: [あ, い, う, え, お, か]
drinkEnTempList: [A, B, C, D, E, F]
priceTempList: [100, 110, 120, 130, 140, 150]
editMsg: null

【考察】 TempListに追加されていない。
main()内の editTemp.setValue()を削除すると
各TempListは nullとなる。

appendOperation()内の super.setValue()をしないと
NullPointerExceptionとなる。

editTemp.append.indexTempListで呼び出すと追加されている。
これは元の EditTempLogicのインスタンスと
EditAppendの superとしてのEditTempLogicが別々に存在していることを示している。

=> 複数のTempListを持つことは混乱の原因となるため、継承関係を解消し、
EditTempLogicからの委譲先として独立した子クラスに変更。
EditTempLogic内のフィールドで保持し、インスタンスも同クラス内で行う。

editTemp.indexTempList.add(index);
追加も反映されている。

canAppend: true
baseSize: 5
defaultIndexList: [0, 10, 20, 30, 40]
appendEditList: [50, か, F, 150]
appendEditList.size(): 4
indexTempList: [0, 25, 20, 30, 40, 50]
drinkJpTempList: [あ, い, う, え, お, か]
drinkEnTempList: [A, B, C, D, E, F]
priceTempList: [100, 110, 120, 130, 140, 150]
editMsg: null


◆要素空白でも size 4問題
条件式: if(appList.size() < APPEND_SIZE)
    appendEditList: [, か, F, 150]
    appendEditList.size(): 4
    indexTempList: [0, 25, 20, 30, 40, ]

canAppend -> trueとなり追加されている

条件式: if(appList.stream().anyMatch(e -> e.equals("")))に変更
canAppend: false
appendEditList: [, か, F, 150]
appendEditList.size(): 4
indexTempList: null
drinkJpTempList: null
drinkEnTempList: null
priceTempList: null
editMsg: You should input 'Append' all columns.

◆全ての要素が空白
canAppend: true
appendEditList: [, , , ]
appendEditList.size(): 4
indexTempList: null

canAppend -> true は if(appList.size() < APPEND_SIZE)で returnされたもの
それでも TempListが nullになるのは おかしい。

◆要素が重複
String[] appendDemoAry = {"10","か","F","150"};
canAppend: false
indexTempList: null
editMsg: Incorrect as digit.
*/