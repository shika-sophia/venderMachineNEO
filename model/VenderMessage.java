package model;

import java.util.Locale;

public class VenderMessage {
    private String msg;  //表示メッセージ
    private final double EX_RATE;//為替レート

    public VenderMessage(double EX_RATE) {
        this.EX_RATE = EX_RATE;
    }

    public String buildMsg(String order, Locale locale, VenderCalc calc) {
        if(order.startsWith("input")) {
            if(locale.toString().contains("ja")) {
                msg = String.format("%d円が入りました。 \n",
                        calc.getInput());
            } else {
                msg = String.format("＄%.2f coins have been inserted. \n",
                        calc.getInput() / EX_RATE);
            }
        }//if input

        if(order.startsWith("req")) {
            if(locale.toString().contains("ja")) {
                msg = String.format("%sを購入しました。 \n",
                        calc.getBuyDrink());
            } else {
                msg = String.format("%s have been purchased. \n",
                        calc.getBuyDrink());
            }
        }//if req

        if(order.equals("finish")) {
            if(locale.toString().contains("ja")) {
                msg = String.format("%d円を返金しました。 ありがとうございました。\n",
                        calc.getInput());
            } else {
                msg = String.format("＄%.2f coins have been returned. Thank you. \n",
                        calc.getInput() / EX_RATE);
            }
        }//if finish

        return msg;
    }//buildMsg()

    public String getMsg(Locale locale) {
        if(msg == null) {
            if(locale.toString().equals("ja")) {
                msg = "コインを入れてください。\n";
            } else {
                msg = "Please insert coins. \n";
            }
        }//if null

        return msg;
    }//getMsg()

    public void restractLocale(Locale locale) {
        if(locale.toString().contains("ja")) {
            msg = "日本語に変更しました。";
        } else {
            msg = "Language has been changed to English.";
        }
    }//restractLocale()

//    //====== Test main() ====
//    public static void main(String[] args) {
//        var data = new DrinkData();
//        var calc = new VenderCalc(data);
//        var parse = new VenderParse();
//        var here = new VenderMessage();
//
//        String[] orderAry = {
//            "input10", "input50", "input100", "input500", "input1000",
//            "req0", "req1", "req2", "req3", "req4"
//        };
//
//        System.out.println(here.getMsg());
//        Arrays.stream(orderAry)
//            .forEach(order -> {
//                parse.parseOrder(order, calc);
//                here.buildMsg(order, calc);
//                System.out.println(here.getMsg());
//            });
//
//    }//main()
}//class

/*
コインを入れてください。
10円が入りました。
50円が入りました。
100円が入りました。
500円が入りました。
1000円が入りました。
コーヒーを購入しました。
コーラを購入しました。
オレンジジュースを購入しました。
紅茶を購入しました。
ウォーターを購入しました。

*/