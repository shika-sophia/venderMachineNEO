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
            .map(price -> Integer.valueOf(price))
            .forEach(data.priceList::add);
    }//reBuildData()

}//class
