/**
 * ◆ソートのアルゴリズム
 * 元の入力インデックスを保存しておき、0～50
 * それを数字昇順にソート。
 * 例) 0, 25, 20, 30, 40 //indexEditList
 * 例) 0, 20, 25, 30, 40 //sortedIndexList
 * ソート済の並び順は、元のListの何番目indexかを求め、
 * List.indexを取り出し 0～5
 * 例) 0, 2, 1, 3, 4     //indexOriginList
 * 各Listを List.index順に addしていく。
 *
 * ◆DBの利用
 * 本来はDBのテーブルを用意して、DAOクラスで検索・ソートを行うところであるが、
 * 今回はListだけでそのロジックを組むことにする。
 *
 */
package model;

import java.util.ArrayList;
import java.util.List;

public class EditSortIndex {
    private EditData editData;
    private EditTempLogic editTemp;
    private List<String> indexEditList;
    private List<String> sortedIndexList;
    private List<Integer> originIndexList;

    public EditSortIndex(EditData editData, EditTempLogic editTemp) {
        this.editData = editData;
        this.editTemp = editTemp;
    }

    private void buildIndex() {
        indexEditList = editData.indexEditList;

        if(sortedIndexList == null) {
            sortedIndexList = new ArrayList<String>();
            originIndexList = new ArrayList<Integer>();
        } else {
            sortedIndexList.clear();
            originIndexList.clear();
        }

        indexEditList.stream()
            .sorted()
            .forEach(sortedIndexList::add);

        sortedIndexList.stream()
            .map(value -> indexEditList.indexOf(value))
            .forEach(originIndexList::add);
    }//buildIndex()

    public void sortByIndex() {
        buildIndex();
        editTemp.initTempList();
        originIndexList.stream()
            .forEach(index -> {
                editTemp.indexTempList.add(indexEditList.get(index));
                editTemp.drinkJpTempList.add(editData.drinkJpEditList.get(index));
                editTemp.drinkEnTempList.add(editData.drinkEnEditList.get(index));
                editTemp.priceTempList.add(editData.priceEditList.get(index));
            });
    }//sortByIndex()

//    //====== Test main() ======
//    public static void main(String[] args) {
//        var editData = new EditData();
//        var editTemp = new EditTempLogic(editData);
//
//        String[] indexDemoAry = {"0", "25", "20", "30", "40"};
//        String[] drinkJpDemoAry = {"あ","い","う","え","お"};
//        String[] drinkEnDemoAry = {"A","B","C","D","E"};
//        String[] priceDemoAry = {"100","110","120","130","140"};
//        String[] appendDemoAry = {"50","か","F","150"};
//        String[] deleteDemoAry = {"0"};
//        editData.setListValue(indexDemoAry, drinkJpDemoAry, drinkEnDemoAry,
//              priceDemoAry, appendDemoAry, deleteDemoAry);
//        editTemp.setValue();
//
//        //---- Test buildIndex() ----
//        editTemp.sort.buildIndex();
//        System.out.println("indexEditList: " + editTemp.sort.indexEditList);
//        System.out.println("sortedIndexList: " + editTemp.sort.sortedIndexList);
//        System.out.println("originIndexList: " + editTemp.sort.originIndexList);
//
//        //---- Test sortByIndex() ----
//        System.out.println("---- before-sort ----");
//        printTemp(editTemp);
//
//        editTemp.sortByIndex();
//
//        System.out.println("---- after-sort ----");
//        printTemp(editTemp);
//    }//main()
//
//    private static void printTemp(EditTempLogic editTemp) {
//        System.out.println("indexTempList: " + editTemp.indexTempList);
//        System.out.println("drinkJpTempList: " + editTemp.drinkJpTempList);
//        System.out.println("drinkEnTempList: " + editTemp.drinkEnTempList);
//        System.out.println("priceTempList: " + editTemp.priceTempList);
//    }//printTemp()
}//class

/*
//====== Test buildIndex() ======
indexEditList: [0, 25, 20, 30, 40]
sortedIndexList: [0, 20, 25, 30, 40]
originIndexList: [0, 2, 1, 3, 4]

//====== Test sortByIndex() ======
indexEditList: [0, 25, 20, 30, 40]
sortedIndexList: [0, 20, 25, 30, 40]
originIndexList: [0, 2, 1, 3, 4]
---- before-sort ----
indexTempList: [0, 25, 20, 30, 40]
drinkJpTempList: [あ, い, う, え, お]
drinkEnTempList: [A, B, C, D, E]
priceTempList: [100, 110, 120, 130, 140]
---- after-sort ----
indexTempList: [0, 20, 25, 30, 40]
drinkJpTempList: [あ, う, い, え, お]
drinkEnTempList: [A, C, B, D, E]
priceTempList: [100, 120, 110, 130, 140]

 */
