package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class EditTempLogic {
    private EditData editData;
    private List<String> defaultIndexList;
    private int baseSize;

    public EditTempLogic(EditData editData) {
        this.editData = editData;
        this.defaultIndexList = buildDefaultIndex();
        this.baseSize = editData.drinkEditList.size();
    }

    private List<String> buildDefaultIndex() {
        if (defaultIndexList == null) {
            defaultIndexList = new ArrayList<String>();
            IntStream.rangeClosed(0, baseSize)
                .mapToObj(i -> String.valueOf(i * 10))
                .forEach(defaultIndexList::add);
        }

        return defaultIndexList;
    }//buildDefaultIndex()

    public void sortByIndex() {

    }//sortByIndex()

    public boolean appendOperation(EditMessage editMess) {
        List<String> appList = editData.appendEditList;

        //要素がすべて空なら false
        if(!isOrder(appList)) {
            return false;
        }

        //要素が足りない場合
        if(appList.size() < baseSize + 1) {
            editMess.IncorrectAppend();
            return false;
        }

        String index = appList.get(0);

        return true;
    }//appendOperation()

    //すべての要素が「""」blankなら false
    private boolean isOrder(List<String> list) {
        boolean isBlank = list.stream()
            .allMatch(e -> e.isBlank());

        return !(isBlank);
    }//isOrder()
}//class
