/**
 * @param String order
 * @content jspから渡されるボタンクリックの内容
 * @value input10, input50, input100, input500, input1000
 *        moneyのinput, 数字は投入金額
 * @value req0, req1, req2, req3, req4
 *        コーヒー, コーラ, オレンジジュース, 紅茶, ウォーターの注文
 * @value finish
 *        返金ボタン
 */
package model;

import java.util.Locale;

public class VenderParse {

    public void parseOrder(String order, VenderCalc calc) {
        if(order.startsWith("input")) {
            order = order.replaceAll("input", "").trim();

            if(order.contains(".")) {
                order = order.replace(".", "");
            }

            int input = Integer.parseInt(order);
            calc.judgeCanBuy(input);
            //System.out.println("input: " + input);

        } else if(order.startsWith("req")){
            order = order.replaceAll("req", "").trim();
            int index = Integer.parseInt(order);
            calc.doBuy(index);
            //System.out.println("index: " + index);
            //System.out.println(calc.getDrinkList().get(index));

        } else if(order.equals("finish")) {
            calc.returnMoney();
        }
    }//parseOrder()

    //unUsed ChangeLanguageServlet用
    public Locale buildLocale(String order) {
        String parsedLanguage = "";
        if(order.equals("日本語")) {
            parsedLanguage = "ja";
        } else if(order.equals("English")){
            parsedLanguage = "en";
        }

        return new Locale(parsedLanguage);
    }//buildLocale()
//    //====== Test main() ======
//    public static void main(String[] args) {
//        var data = new DrinkData();
//        var calc = new VenderCalc(data);
//        var here = new VenderParse();
//
//        String[] orderAry = {
//            "input10", "input50", "input100", "input500", "input1000",
//            "req0", "req1", "req2", "req3", "req4"
//        };
//
//        Arrays.stream(orderAry)
//            .forEach(order -> here.parseOrder(order, calc));
//    }//main()
}//class

/*
input: 10
input: 50
input: 100
input: 500
input: 1000
index: 0
コーヒー
index: 1
コーラ
index: 2
オレンジジュース
index: 3
紅茶
index: 4
ウォーター
*/