package model;

import java.util.ArrayList;
import java.util.List;

public class EditSortIndex extends EditTempLogic {
    private List<String> indexEditList;
    private List<String> sortedIndexList;

    public void buildIndex() {
        indexEditList = editData.indexEditList;

        if(sortedIndexList == null) {
            sortedIndexList = new ArrayList<>();
        } else {
            sortedIndexList.clear();
        }

        indexEditList.stream()
            .sorted()
            .forEach(sortedIndexList::add);
    }//buildIndex()

    public void sortByIndex() {

    }//sortByIndex()


}//class
