package model;

import java.util.ArrayList;
import java.util.List;

public class EditSortIndex extends EditTempLogic {
    private List<String> indexEditList;
    private List<String> sortedIndexList;

    public void buildIndex() {
        indexEditList = editData.indexEditList;
        sortedIndexList = new ArrayList<>();
        indexEditList.stream()
            .sorted()
            .forEach(sortedIndexList::add);
    }

    public void sortEditList() {

    }

    private List<String> buildList(List<String>list, int index){

        return list;
    }
}//class
