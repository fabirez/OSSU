
// Represents a bank account
public abstract class Account {

    int accountNum;  // Must be unique
    int balance;     // Must remain above zero (others Accts have more restrictions)
    String name;     // Name on the account

    public Account(int accountNum, int balance, String name){
        this.accountNum = accountNum;
        this.balance = balance;
        this.name = name;
    }

    // EFFECT: Withdraw the given amount
    // Return the new balance
    int withdraw(int amount){
        int newBalance = this.balance-amount;
        if(newBalance < 0){
            throw new RuntimeException(amount + " is not available");
        }else{
            this.balance = newBalance;
            return newBalance;
        }
    };


    // EFFECT: Deposit the given funds into this account
    // Return the new balance
    int deposit(int funds){
        int newBalance = this.balance+funds;
        this.balance = newBalance;
        return newBalance;
    };
    
    // return the account number 
    int getAccNo(){ return this.accountNum; }

}
