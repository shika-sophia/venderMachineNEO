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
        boolean canAccept = editList.parallelStream()
            .flatMap(list -> list.parallelStream())
            .anyMatch(this::normalizedMatch);

        return !(canAccept); //anyMatchしていたら false
    }//checkListElement()

    private boolean normalizedMatch(String element) {
        //Unicode表現を標準化 normalize()
        String normalized = Normalizer.normalize(element, Form.NFKC);

        //タグ「<」「>」とマッチしたら true
        boolean isMatch = regex.matcher(normalized).find();

        return isMatch;
    }//normalizedMatch()
}//class
