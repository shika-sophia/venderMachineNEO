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
import java.util.Locale;

public class EditTempLogic {
    protected EditData editData;
    private EditAppend append;
    private EditSortIndex sort;
    protected Locale locale;
    protected List<String> defaultIndexList; //編集前のindex
    protected List<String> indexTempList;
    protected List<String> drinkJpTempList;
    protected List<String> drinkEnTempList;
    protected List<String> priceTempList;

    protected EditTempLogic() { }
    public EditTempLogic(EditData editData, Locale locale) {
        this.editData = editData;
        this.append = new EditAppend();
        this.sort = new EditSortIndex();
        this.locale = locale;
    }

    public void setValue() {
        append.setValue();
        this.indexTempList = initList(indexTempList);
        this.drinkJpTempList = initList(drinkJpTempList);
        this.drinkEnTempList = initList(drinkEnTempList);
        this.priceTempList = initList(priceTempList);
    }

    public List<String> initList(List<String> list){
        if(list == null) {
            list = new ArrayList<String>();
        } else {
            list.clear();
        }

        return list;
    }//buildList()


    //すべての要素が「""」blankなら false
    protected boolean isOrder(List<String> list) {
        boolean isBlank = list.stream()
            .allMatch(e -> e.isBlank());

        return !(isBlank);
    }//isOrder()

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

}//class
