package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditData extends DrinkData {
    List<List<String>> editList;
    List<String> indexEditList;
    List<String> drinkEditList;
    List<String> priceEditList;
    List<String> appendEditList;
    List<String> deleteEditList;

    public EditData() { }

    public void setListValue(
            String[] indexEditAry,
            String[] drinkEditAry,
            String[] priceEditAry,
            String[] appendEditAry,
            String[] deleteEditAry) {
        this.indexEditList = aryToList(indexEditAry, indexEditList);
        this.drinkEditList = aryToList(drinkEditAry, drinkEditList);
        this.priceEditList = aryToList(priceEditAry, priceEditList);
        this.appendEditList = aryToList(appendEditAry, appendEditList);
        this.deleteEditList = aryToList(deleteEditAry, deleteEditList);
        buildEditList();

        //---- Test print ----
        printNestList(editList);
    }//setListValue()

    private void buildEditList() {
        if(editList == null) {
            editList = new ArrayList<>(Arrays.asList(
                indexEditList, drinkEditList, priceEditList,
                appendEditList, deleteEditList));
        }
    }//buildEditList()

    private List<String> aryToList(String[] ary, List<String> list) {
        if(list == null) {
            list = new ArrayList<>(Arrays.asList(ary));
        } else {
            list.clear();
            Arrays.stream(ary)
                .forEach(list::add);
        }

        return list;
    }//aryToList()

    //====== Test print ======
    private void printNestList(List<List<String>> nestList) {
        nestList.stream()
            .flatMap(list -> {
                System.out.println(list.size());
                list.add(list.size(), "\n");
                return list.stream();
            })
            .forEach(System.out::print);
    }//printNestList()
}//class

/*
// ---- Test printNestList() / <input placeholder=""> ----
@requestQuery http://localhost:8080/venderMachineNEO/EditorServlet
 *   ?id=&id=&id=&id=&id=&ap=50
 *   &dr=&dr=&dr=&dr=&dr=Milk&ap=Soda&ap=SodaEn
 *   &pr=&pr=&pr=&pr=&pr=&ap=1.20&de=de3

5 //index

5 //drink
Soda
5 //price

4 //append
50みるくMilk1.20
1 //delete
de2

//---- <input value=""> ----
5
510203040 //index 0 -> 5に更新して送信
5

5

4

1
append

*/