package model;

import java.util.ArrayList;
import java.util.List;

public class EditSortIndex {
    private EditData editData;
    private List<String> indexEditList;
    private List<String> sortedIndexList;

    public EditSortIndex(EditData editData){
        this.editData = editData;
    }

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
