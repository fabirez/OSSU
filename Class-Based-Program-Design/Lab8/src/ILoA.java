// Represents a List of Accounts
public interface ILoA{
    ILoA append(Account that);
    ILoA appendList(ILoA that);

    int depositAccount(int funds, int accNo);
    int withdrawAccount(int funds, int accNo);
    // EFFECT: Remove the given account from this Bank
    ILoA removeAccount(int acctNo);
    ILoA removeAccountHelper(ILoA prev, int acctNo);

}

