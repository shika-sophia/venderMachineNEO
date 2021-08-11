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
    }//constructor

    private List<String> aryToList(String[] ary) {
        return new ArrayList<>(Arrays.asList(ary));
    }

//    private void printNestList(List<List<String>> nestList) {
//        nestList.stream()
//            .flatMap(list -> {
//                System.out.println(list.size());
//                list.add(list.size(), "\n");
//                return list.stream();
//            })
//            .forEach(System.out::print);
//    }//printNestList()
}//class

/*
// ---- Test printNestList() ----
5 //index

5 //drink
Soda
5 //price

4 //append
50みるくMilk1.20
1 //delete
de2

*/