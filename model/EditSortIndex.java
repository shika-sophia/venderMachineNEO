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
 */
package model;

import java.util.ArrayList;
import java.util.List;

public class EditSortIndex extends EditTempLogic {
    private List<String> indexEditList;
    private List<String> sortedIndexList;
    private List<Integer> originIndexList;

    public void buildIndex() {
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
        originIndexList.stream()
            .forEach(index -> {
                indexTempList.add(indexEditList.get(index));
                drinkJpTempList.add(editData.drinkJpEditList.get(index));
                drinkEnTempList.add(editData.drinkEnEditList.get(index));
                priceTempList.add(editData.priceEditList.get(index));
            });
    }//sortByIndex()

}//class
