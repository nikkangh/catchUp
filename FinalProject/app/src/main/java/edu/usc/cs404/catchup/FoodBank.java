package edu.usc.cs404.catchup;

/**
 * Created by eshitamathur on 5/4/17.
 */


    public class FoodBank {
        String foodBankName;
        String phone;
        String address;

        // default constructor is needed by Firebase!
        public FoodBank() {
        }

        public FoodBank (String foodBankName, String phone, String address) {
            this.foodBankName = foodBankName;
            this.phone = phone;
            this.address = address;
        }

        public String getFoodBankName() {
            return foodBankName;
        }

        public void setFoodBankName(String foodBankName) {
            this.foodBankName = foodBankName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String author) {
            this.address = address;
        }
    }



