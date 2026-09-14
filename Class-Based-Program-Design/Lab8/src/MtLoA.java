
// Represents the empty List of Accounts
public class MtLoA implements ILoA{
    MtLoA(){}

    public ILoA append(Account that) { return new ConsLoA(that, new MtLoA()); }
    public ILoA appendList(ILoA that) { return that; }

    public int depositAccount(int funds, int accNo) { 
        throw new RuntimeException("Dind't find any account with this account number: " + accNo);
    }

    public int withdrawAccount(int funds, int accNo) 
    { 
        throw new RuntimeException("Dind't find any account with this account number: " + accNo);
    }

    public ILoA removeAccount(int acctNo)
    {
        throw new RuntimeException("Dind't find any account with this account number: " + acctNo);
    }

    public ILoA removeAccountHelper(ILoA prev, int acctNo)
    {
        throw new RuntimeException("Dind't find any account with this account number: " + acctNo);
    }

}

