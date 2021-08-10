package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditData extends DrinkData {
    //List<List<String>> editList;
    List<String> indexEditList;
    List<String> drinkEditList;
    List<String> priceEditList;
    List<String> appendEditList;
    List<String> deleteEditList;

    public EditData(
            String[] indexEditAry,
            String[] drinkEditAry,
            String[] priceEditAry,
            String[] appendEditAry,
            String[] deleteEditAry) {
        this.indexEditList = aryToList(indexEditAry);
        this.drinkEditList = aryToList(drinkEditAry);
        this.priceEditList = aryToList(priceEditAry);
        this.appendEditList = aryToList(appendEditAry);
        this.deleteEditList = aryToList(deleteEditAry);

//        //---- Test print ----
//        editList = new ArrayList<>(Arrays.asList(
//            indexEditList, drinkEditList, priceEditList,
//            appendEditList, deleteEditList));
//        printNestList(editList);
    }

    private List<String> aryToList(String[] ary) {
        return new ArrayList<>(Arrays.asList(ary));
    }

//    private void printNestList(List<List<String>> nestList) {
//        nestList.stream()
//            .flatMap(list -> {
//                list.add(list.size() - 1, "\n");
//                return list.stream();
//            })
//            .forEach(System.out::print);
//    }//printNestList()
}//class

/*
// ---- Test printNestList() ----
Soda       //drink
50みるくMilk //append
1.20       //append
de2        //delete
*/