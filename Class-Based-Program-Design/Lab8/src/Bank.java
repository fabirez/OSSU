
// Represents a Bank with list of accounts
public class Bank {
    
    String name;
    ILoA accounts;
    
    public Bank(String name){
        this.name = name;

        // Each bank starts with no accounts
        this.accounts = new MtLoA();
    }


    // allow the customer to open a new account in the bank.
    // EFFECT: Add a new account to this Bank
    void add(Account acct)
    { 
        this.accounts = new ConsLoA(acct, this.accounts);
    }

    // deposit that deposits the given amount to the account with the given account number.
    int deposit(int funds, int acctNo) {
        return this.accounts.depositAccount(funds, acctNo);
    }

    // deposit that deposits the given amount to the account with the given account number.
    int withdraw(int funds, int acctNo) {
        return this.accounts.withdrawAccount(funds, acctNo);
    }

    // EFFECT: Remove the given account from this Bank
    void removeAccount(int acctNo){
        this.accounts = this.accounts.removeAccount(acctNo);
        return;
    }
}
