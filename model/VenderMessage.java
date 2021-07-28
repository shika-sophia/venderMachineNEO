package model;

import java.util.Arrays;

public class VenderMessage {
    String msg;

    public String buildMsg(String order, VenderCalc calc) {
        if(order.startsWith("input")) {
            msg = String.format("%d円が入りました。 \n",
                calc.getInput());
        }

        if(order.startsWith("req")) {
            msg = String.format("%sを購入しました。 \n",
                calc.getBuyDrink());
        }

        return msg;
    }//buildMsg()

    public String getMsg() {
        if(msg == null) {
            msg = "コインを入れてください。\n";
        }

        return msg;
    }//getMsg()

    //====== Test main() ====
    public static void main(String[] args) {
        var data = new DrinkData();
        var calc = new VenderCalc(data);
        var parse = new VenderParse();
        var here = new VenderMessage();

        String[] orderAry = {
            "input10", "input50", "input100", "input500", "input1000",
            "req0", "req1", "req2", "req3", "req4"
        };

        System.out.println(here.getMsg());
        Arrays.stream(orderAry)
            .forEach(order -> {
                parse.parseOrder(order, calc);
                here.buildMsg(order, calc);
                System.out.println(here.getMsg());
            });

    }//main()
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