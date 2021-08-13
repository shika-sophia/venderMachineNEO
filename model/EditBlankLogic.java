/**
 * @status 未使用クラス
 * @descrition
 * //====== model/EditorLogic | 2021-08-13 ======
 * ◆<input placeholder="">で入力フォームを作成。
 * Servletで String[]の値を取得
 * String[] -> List<String>に変換。
 *
 * 値の変更があった部分だけを処理するアルゴリズム。
 * 値の変更がなかった部分は ""blankの要素が入っている。
 *
 * ◆<input value="">で入力フォームを作成。
 * アルゴリズムを変更し、すべての値を入れておき、
 * 変更部分だけ修正された値となる。
 * ""blank判定は不要になり、そのまま変更後のListを作成できる。
 * 確認ページ confirmに渡すなら、こちらのアルゴリズムのほうが処理がシンプル。
 *
 * EditLogicは 不要なので、システムから除外。保存だけしておく。
 * EditBlankLogicに改名。
 */
package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditBlankLogic {
    private EditData editData;
    private List<Boolean> isOrderList;
    private List<String> editOrderList = new ArrayList<>();
    private final String[] editName =
        {"index","drink","price","append","delete"};

    public EditBlankLogic(EditData editData) {
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

/*
//====== EditorServlet.doPost() | 2021-08-13 修正前 ======
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String[] indexEditAry = request.getParameterValues("id");
    String[] drinkEditAry = request.getParameterValues("dr");
    String[] priceEditAry = request.getParameterValues("pr");
    String[] appendEditAry = request.getParameterValues("ap");
    String[] deleteEditAry = request.getParameterValues("de");
    EditData editData = new EditData(
            indexEditAry, drinkEditAry, priceEditAry,
            appendEditAry, deleteEditAry);

    EditBlankLogic editLogic = new EditBlankLogic(editData);
}//doPost()
*/