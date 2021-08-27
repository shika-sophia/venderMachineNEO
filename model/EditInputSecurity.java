/**
 * @content form input内容のチェック
 *          入力内容に <script>などの HTML, JavaScriptタグがないかをチェック
 * @security XSS: Cross-Site Scripting 〔DS p496 / HK10〕
 *             エスケープ処理(=サニタイズ)の不備で
 *             ユーザー入力がそのまま表示されてしまう脆弱性。
 */
package model;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.List;
import java.util.regex.Pattern;

public class EditInputSecurity {
    EditData editData;
    List<List<String>> editList;
    Pattern regex = Pattern.compile("<.*>");

    public EditInputSecurity(EditData editData){
        this.editData = editData;
    }

    public void setEditList() {
        this.editList = editData.editList;
    }

    public boolean checkListElement() {
        boolean isMatch = editList.parallelStream()
            .flatMap(list -> list.parallelStream())
            .anyMatch(this::normalizedMatch);

        return !(isMatch); //anyMatchしていたら false -> canAccept
    }//checkListElement()

    private boolean normalizedMatch(String element) {
        //Unicode表現を標準化 normalize()
        String normalized = Normalizer.normalize(element, Form.NFKC);

        //タグ「<」「>」とマッチしたら true
        boolean isMatch = regex.matcher(normalized).find();

        return isMatch;
    }//normalizedMatch()

//    //====== Test main() ======
//    public static void main(String[] args) {
//        var editData = new EditData();
//        var security = new EditInputSecurity(editData);
//
//        //---- common DemoData ----
//        String[] indexDemoAry = {"0", "25", "20", "30", "40"};
//        String[] drinkEnDemoAry = {"A","B","C","D","E"};
//        String[] priceDemoAry = {"100","110","120","130","140"};
//        String[] appendDemoAry = {"50","か","F","150"};
//        String[] deleteDemoAry = {"0"};
//
//        //---- nomal DemoData ----
//        String[] drinkJpDemoAry = {"あ","い","う","え","お"};
//        //----script DemoData ----
//        //String[] drinkJpDemoAry = {"<script>あ</script>","い","う","え","お"};
//
//        editData.setListValue(indexDemoAry, drinkJpDemoAry, drinkEnDemoAry,
//                    priceDemoAry, appendDemoAry, deleteDemoAry);
//        security.setEditList();
//
//        boolean test = security.checkListElement();
//        System.out.println("test: " + test);
//    }//main()
}//class

/*
//---- nomal DemoData ----
test: true

//----script DemoData ----
test: false

*/