package archive;

import java.util.ArrayList;
import java.util.List;

public class Drink {
  //====== drinkName() ======
  public List<String> drinkName(){
      List<String> drinkName = new ArrayList<>();
        drinkName.add("コーヒー ");
        drinkName.add("コーラ");
        drinkName.add("オレンジジュース");
        drinkName.add("紅茶");
        drinkName.add("ウォーター");
      return drinkName;

  }//drinkName()

  //====== price() ======
  public List<Integer> price(){
      List<Integer> price = new ArrayList<>();
        price.add(130);//"コーヒー"
        price.add(110);//"コーラ"
        price.add(110);//"オレンジジュース"
        price.add(130);//"紅茶"
        price.add(100);//"ウォーター"
      return price;

  }//price()
}//class
