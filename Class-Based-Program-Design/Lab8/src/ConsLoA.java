
// Represents a non-empty List of Accounts...
public class ConsLoA implements ILoA{

    Account first;
    ILoA rest;

    public ConsLoA(Account first, ILoA rest){
        this.first = first;
        this.rest = rest;
    }
    
    /* Template
     *  Fields:
     *    ... this.first ...         --- Account
     *    ... this.rest ...          --- ILoA
     *
     *  Methods:
     *
     *  Methods for Fields:
     *
     */

    // EFFECT: deposit in the current account
    public int depositAccount(int funds, int accNo)
    {
        if(accNo == this.first.getAccNo()){
            return this.first.deposit(funds);
        }else{
            return this.rest.depositAccount(funds, accNo);
        }
    }

    public int withdrawAccount(int funds, int accNo)
    {
        if(accNo == this.first.getAccNo()){
            return this.first.withdraw(funds);
        }else{
            return this.rest.withdrawAccount(funds, accNo);
        }
    }


    public ILoA append(Account a) { return new ConsLoA(this.first, this.rest.append(a)); }
    public ILoA appendList(ILoA that) { return new ConsLoA(this.first, this.rest.appendList(that)); }

    // EFFECT: Remove the given account from this Bank
    public ILoA removeAccount(int acctNo)
    {
        // WARNING: Edge case, first element
        if(acctNo == this.first.getAccNo()){
            return this.rest;
        }else{
            return this.rest.removeAccountHelper(
                new ConsLoA(this.first, new MtLoA()),
                acctNo);
        }
    }

    public ILoA removeAccountHelper(ILoA prev, int acctNo)
    {
        if(acctNo == this.first.getAccNo()){
            return prev.appendList(this.rest);
        }else{
            return this.rest.removeAccountHelper(prev.append(this.first), acctNo);
        }
    }

}
