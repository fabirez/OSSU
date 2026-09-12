// Represents a credit line account
public class Credit extends Account{

    int creditLine;  // Maximum amount accessible
    double interest; // The interest rate charged
    
    public Credit(int accountNum, int balance, String name, int creditLine, double interest){
        super(accountNum, balance, name);
        this.creditLine = creditLine;
        this.interest = interest;
    }

    // EFFECT: Withdraw the given amount
    // Return the new balance
    int withdraw(int amount){
        int newBalance = -(Math.abs(this.balance) + Math.abs(amount));
        if(Math.abs(newBalance) > 1000){
            throw new RuntimeException("Cannot withdraw more than " + creditLine);
        }else{
            this.balance = newBalance;
            return newBalance;
        }
    };

    // EFFECT: Deposit the given funds into this account
    // Return the new balance
    int deposit(int funds){
        if(this.balance >= 0){
            throw new RuntimeException("You already payed your debt!");
        }
        int newBalance = this.balance+funds;

        if(newBalance > 0){
            throw new RuntimeException("Please deposit only " + String.valueOf(Math.abs(this.balance)));
        }

        this.balance = newBalance;
        return newBalance;
    };
}
