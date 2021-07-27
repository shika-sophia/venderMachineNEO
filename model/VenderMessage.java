package model;

public class VenderMessage {
    String msg;

    public String buildMsg() {
        if(msg == null) {
            msg = "コインを入れてください。";
        }

        return msg;
    }//buildMsg()

}//class
