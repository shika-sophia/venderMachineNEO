package model;

public class EditDecide {
    private DrinkData data;
    private EditTempLogic editTemp;

    public void setField(DrinkData data, EditTempLogic editTemp) {
        this.data = data;
        this.editTemp = editTemp;
    }

    public void reBuildData() {
        data.drinkListJp.clear();
        data.drinkListEn.clear();
        data.priceList.clear();

        //TempList -> DrinkDataを更新
        data.drinkListJp = editTemp.drinkJpTempList;
        data.drinkListEn = editTemp.drinkEnTempList;
        editTemp.priceTempList.stream()
            .map(price -> price.replace(".", ""))
            .map(price -> Integer.valueOf(price))
            .forEach(data.priceList::add);
    }//reBuildData()

//    //====== Test main() ======
//    public static void main(String[] args) {
//        Locale locale = new Locale("ja");
//        //Locale locale = new Locale("en");
//        var data = new DrinkData();
//        var editData = new EditData();
//        var editTemp = new EditTempLogic(editData);
//        var editMess = new EditMessage(locale);
//        var decide = new EditDecide();
//
//        String[] indexDemoAry = {"0", "25", "20", "30", "40"};
//        String[] drinkJpDemoAry = {"あ","い","う","え","お"};
//        String[] drinkEnDemoAry = {"A","B","C","D","E"};
//        //String[] priceDemoAry = {"100","110","120","130","140"};
//        String[] priceDemoAry = {"1.00","1.10","1.20","1.30","1.40"};
//        String[] appendDemoAry = {"50","か","F","150"};
//        String[] deleteDemoAry = {"0"};
//        editData.setListValue(indexDemoAry, drinkJpDemoAry, drinkEnDemoAry,
//            priceDemoAry, appendDemoAry, deleteDemoAry);
//        editTemp.setValue();
//
//        //---- Test DrinkData ----
//        printDrinkData(locale, data);
//
//        //---- appendOperation(), reBuildData() ----
//        editTemp.appendOperation(editMess);
//        decide.setField(data, editTemp);
//        decide.reBuildData();
//
//        //---- Test DrinkData ----
//        printDrinkData(locale, data);
//    }//main()
//
//    public static void printDrinkData(Locale locale, DrinkData data) {
//        System.out.println("drinkListJp: " + data.drinkListJp);
//        System.out.println("drinkListEn: " + data.drinkListEn);
//        System.out.println("priceList: " + data.priceList);
//        System.out.println("drinkList: " + data.getDrinkList(locale));
//    }//printDrinkData()
}//class

/*
//====== Test main() ======
drinkListJp: [コーヒー, コーラ, オレンジジュース, 紅茶, ウォーター]
drinkListEn: [Coffee, Coca-Cola, Orange-Juce, Red-Tea, Water]
priceList: [130, 110, 110, 130, 100]
drinkList: [Coffee, Coca-Cola, Orange-Juce, Red-Tea, Water]

drinkListJp: [あ, い, う, え, お, か]
drinkListEn: [A, B, C, D, E, F]
priceList: [100, 110, 120, 130, 140, 150]
drinkList: [A, B, C, D, E, F]

*/