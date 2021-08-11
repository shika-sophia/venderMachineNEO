package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditLogic {
    private EditData editData;
    private List<Boolean> isOrderList;
    private List<String> editOrderList = new ArrayList<>();
    private final String[] editName =
        {"index","drink","price","append","delete"};

    public EditLogic(EditData editData) {
        this.editData = editData;
        parseEditOrder(); //-> isOrderList
        buildEditOrder(); //-> editOrderList
    }

    private void parseEditOrder() {
        //すべての要素が「""」blankなら false
        boolean indexOrder = isOrder(editData.indexEditList);
        boolean drinkOrder = isOrder(editData.drinkEditList);
        boolean priceOrder = isOrder(editData.priceEditList);
        boolean appendOrder = isOrder(editData.appendEditList);
        boolean deleteOrder = isOrder(editData.deleteEditList);

        isOrderList = new ArrayList<>(Arrays.asList(
                indexOrder, drinkOrder, priceOrder,
                appendOrder, deleteOrder));
    }//parseEditOrder()

    //すべての要素が「""」blankなら false
    private boolean isOrder(List<String> list) {
        boolean isBlank = list.stream()
            .allMatch(e -> e.isBlank());

        return !(isBlank);
    }//isOrder()

    //isOrderListが trueの editNameを editOrderListに格納
    private List<String> buildEditOrder() {
        for(int i = 0; i < editName.length; i++) {
            if(isOrderList.get(i)) {
                editOrderList.add(editName[i]);
            }
        }//for

        return editOrderList;
    }//buildEditOrder
}//class
