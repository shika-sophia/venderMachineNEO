package model;

import java.util.Locale;

public class EditMessage {
    private Locale locale;
    private String editMsg;

    public EditMessage(Locale locale) {
        this.locale = locale;
    }

    public void acceptMsg(boolean canAccept) {
        if(canAccept) {
            if(locale.toString().contains("ja")) {
                editMsg = "この編集内容で、よろしいですか？";
            } else {
                editMsg = "Do you admit this edit？";
            }
        } else {
            if(locale.toString().contains("ja")) {
                editMsg = "入力値が不正です。「<」「>」は使えません。";
            } else {
                editMsg = "Your inputs are incorrect. '< >' cannot be used.";
            }
        }
    }//acceptMsg()


    public void IncorrectAppend() {
        if(locale.toString().contains("ja")) {
            editMsg = "追加項目をすべて入力してください。";
        } else {
            editMsg = "You should input 'Append' all columns.";
        }
    }//IncorrectAppend()

    public String getEditMsg() {
        return editMsg;
    }

    public void IncorrectDigit() {
        if(locale.toString().contains("ja")) {
            editMsg = "数値が無効です。";
        } else {
            editMsg = "Incorrect as digit.";
        }
    }//IncorrectDigit()
}//class
