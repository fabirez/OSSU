import tester.*;

// Bank Account Examples and Tests
public class Examples {
    // Savings accounts: these must maintain a positive balance.
    // Checking accounts: these require a minimum balance.
    // Credit lines: 
    // these are to borrow a limited amount of money.
    // They record the currently owed balance and the maximum amount that can be borrowed by the customer.

    public Examples(){ reset(); }
    
    Account check1;
    Account check2;
    Account check3;
    Account check4;
    Account check5;
    Account check6;

    Account savings1;
    Account savings2;
    Account savings3;
    Account savings4;
    Account savings5;
    Account savings6;
    
    Account ca1;
    Account ca2;
    Account ca3;
    Account ca4;
    Account ca5;
    Account ca6;


    Bank b1;
    Bank b2;
    Bank b3;
    
    // Initializes accounts to use for testing with effects.
    // We place inside reset() so we can "reuse" the accounts
public void reset(){
    // Checking examples
    check1 = new Checking(1, 100, "First Checking Account", 10);
    check2 = new Checking(2, 200, "Secon Checking Account", 20);
    check3 = new Checking(3, 300, "Third Checking Account", 30);
    check4 = new Checking(4, 400, "Forth Checking Account", 40);
    check5 = new Checking(5, 500, "Fifth Checking Account", 50);
    check6 = new Checking(6, 600, "Sixty Checking Account", 60);

    // Savings examples
    savings1 = new Savings(7, 100, "First Savings Account", 1.5);
    savings2 = new Savings(8, 200, "Secon Savings Account", 2.5);
    savings3 = new Savings(9, 300, "Third Savings Account", 3.5);
    savings4 = new Savings(10, 400, "Forth Savings Account", 4.5);
    savings5 = new Savings(11, 500, "Fifth Savings Account", 5.5);
    savings6 = new Savings(12, 600, "Sixty Savings Account", 6.5);

    // Credit examples
    ca1 = new Credit(13, -100, "First Credit Account", 1000, 1.5);
    ca2 = new Credit(14, -200, "Secon Credit Account", 2000, 2.5);
    ca3 = new Credit(15, -300, "Third Credit Account", 3000, 3.5);
    ca4 = new Credit(16, -400, "Forth Credit Account", 4000, 4.5);
    ca5 = new Credit(17, -500, "Fifth Credit Account", 5000, 5.5);
    ca6 = new Credit(18, -600, "Sixty Credit Account", 6000, 6.5);

    // Bank examples
    b1 = new Bank("Bank1");
    b2 = new Bank("Bank2");
    b3 = new Bank("Bank3");
}

    // Tests the exceptions we expect to be thrown when
    // performing an "illegal" action.
    public void testExceptions(Tester t){
        reset();

        t.checkException("Test for invalid Checking withdraw",
                         new RuntimeException("1000 is not available"),
                         this.check1,
                         "withdraw",
                         1000);

        t.checkException("Test for invalid Credit withdraw",
                         new RuntimeException("Cannot withdraw more than 1000"),
                         this.ca1,
                         "withdraw",
                         1001);

        t.checkException("Test for invalid Credit deposit",
                         new RuntimeException("Please deposit only 100"),
                         this.ca1,
                         "deposit",
                         101);

        t.checkException("Test for invalid Credit deposit",
                         new RuntimeException("You already payed your debt!"),
                         new Credit(19, 0, "Seven Credit Account", 7000, 7.5),
                         "deposit",
                         1);

        t.checkException("Test for invalid deposit on non-existent account",
                         new RuntimeException("Dind't find any account with this account number: " + 139),
                         this.b1,
                         "deposit",
                         100, 139);

        t.checkException("Test for removing a non-existent account",
                         new RuntimeException("Dind't find any account with this account number: " + 139),
                         this.b1,
                         "removeAccount",
                         139);
    }


    // Test the withdraw method(s)
    public void testWhitdraw(Tester t){
        reset();

        // Checking
        t.checkExpect(check1.withdraw(25), 75);
        t.checkExpect(check1,
                     new Checking(1, 75, "First Checking Account", 10));

        t.checkExpect(check1.withdraw(50), 25);
        t.checkExpect(check1,
                     new Checking(1, 25, "First Checking Account", 10));

        t.checkExpect(check1.withdraw(25), 0);
        t.checkExpect(check1,
                     new Checking(1, 0, "First Checking Account", 10));

        // Saving
        t.checkExpect(savings1.withdraw(25), 75);
        t.checkExpect(savings1,
                     new Savings(7, 75, "First Savings Account", 1.5));

        t.checkExpect(savings1.withdraw(75), 0);
        t.checkExpect(savings1,
                     new Savings(7, 0, "First Savings Account", 1.5));

        // Credit
        t.checkExpect(ca1.withdraw(100), -200);
        t.checkExpect(ca1,
                     new Credit(13, -200, "First Credit Account", 1000, 1.5));

        t.checkExpect(ca1.withdraw(200), -400);
        t.checkExpect(ca1,
                     new Credit(13, -400, "First Credit Account", 1000, 1.5));

        t.checkExpect(ca1.withdraw(200), -600);
        t.checkExpect(ca1,
                     new Credit(13, -600, "First Credit Account", 1000, 1.5));

        t.checkExpect(ca1.withdraw(200), -800);
        t.checkExpect(ca1,
                     new Credit(13, -800, "First Credit Account", 1000, 1.5));

        t.checkExpect(ca1.withdraw(200), -1000);
        t.checkExpect(ca1,
                     new Credit(13, -1000, "First Credit Account", 1000, 1.5));

        reset();

        // Check the bank accounts
        t.checkExpect(this.b1.accounts, new MtLoA());

        // Add a bank account
        ILoA expected =
            new ConsLoA(this.check1, new MtLoA());

        this.b1.add(this.check1);
        t.checkExpect(this.b1.accounts, expected);

        // withdraw in a bank account using ID 1
        this.b1.withdraw(100, 1);

        Account check1_ =
            new Checking(1, 0, "First Checking Account", 10);

        t.checkExpect(this.check1, check1_);

        // Add savings account
        this.b1.add(this.savings1);

        ILoA expected1 =
            new ConsLoA(this.savings1,
            new ConsLoA(this.check1,
            new MtLoA()));

        t.checkExpect(this.b1.accounts, expected1);

        // withdraw in savings account using ID 7
        this.b1.withdraw(100, 7);

        Account savings1_ =
            new Savings(7, 0, "First Savings Account", 1.5);
        t.checkExpect(this.savings1, savings1_);


        // Add credit account
        this.b1.add(this.ca1);

        ILoA expected2 =
            new ConsLoA(this.ca1,
            new ConsLoA(this.savings1,
            new ConsLoA(this.check1,
            new MtLoA())));

        t.checkExpect(this.b1.accounts, expected2);

        // withdraw in credit account using ID 13
        this.b1.withdraw(100, 13);

        Account ca1_ = new Credit(13, -200, "First Credit Account", 1000, 1.5);
        t.checkExpect(this.ca1, ca1_);
    }


    // Test the deposit method
    public void testDeposit(Tester t){
        reset();

        // Checking
        t.checkExpect(check1.deposit(25), 125);
        t.checkExpect(check1,
                     new Checking(1, 125, "First Checking Account", 10));

        t.checkExpect(check1.deposit(50), 175);
        t.checkExpect(check1,
                     new Checking(1, 175, "First Checking Account", 10));

        t.checkExpect(check1.deposit(25), 200);
        t.checkExpect(check1,
                     new Checking(1, 200, "First Checking Account", 10));

        // Saving
        t.checkExpect(savings1.deposit(25), 125);
        t.checkExpect(savings1,
                     new Savings(7, 125, "First Savings Account", 1.5));

        t.checkExpect(savings1.deposit(75), 200);
        t.checkExpect(savings1,
                     new Savings(7, 200, "First Savings Account", 1.5));

        // Credit
        t.checkExpect(ca1.deposit(100), 0);
        t.checkExpect(ca1,
                     new Credit(13, 0, "First Credit Account", 1000, 1.5));

        reset();

        // Check the bank accounts
        t.checkExpect(this.b1.accounts, new MtLoA());

        // Add a bank account
        ILoA expected =
            new ConsLoA(this.check1, new MtLoA());

        this.b1.add(this.check1);
        t.checkExpect(this.b1.accounts, expected);

        // Deposit in a bank account using ID 1
        this.b1.deposit(100, 1);

        Account check1_ =
            new Checking(1, 200, "First Checking Account", 10);

        t.checkExpect(this.check1, check1_);

        // Add savings account
        this.b1.add(this.savings1);

        ILoA expected1 =
            new ConsLoA(this.savings1,
            new ConsLoA(this.check1,
            new MtLoA()));

        t.checkExpect(this.b1.accounts, expected1);

        // Deposit in savings account using ID 7
        this.b1.deposit(100, 7);

        Account savings1_ =
            new Savings(7, 200, "First Savings Account", 1.5);
        t.checkExpect(this.savings1, savings1_);


        // Add credit account
        this.b1.add(this.ca1);

        ILoA expected2 =
            new ConsLoA(this.ca1,
            new ConsLoA(this.savings1,
            new ConsLoA(this.check1,
            new MtLoA())));

        t.checkExpect(this.b1.accounts, expected2);

        // Deposit in savings account using ID 7
        this.b1.deposit(100, 13);

        Account ca1_ = new Credit(13, 0, "First Credit Account", 1000, 1.5);
        t.checkExpect(this.ca1, ca1_);

        reset();
    }


    // Test the add method(s)
    public void testAdd(Tester t){
        reset();

        t.checkExpect(this.b1.accounts, new MtLoA());

        ILoA expected =
            new ConsLoA(this.check1, new MtLoA());

        this.b1.add(this.check1);
        t.checkExpect(this.b1.accounts, expected);

        ILoA expected1 =
            new ConsLoA(this.savings1,
            new ConsLoA(this.check1,
            new MtLoA()));

        this.b1.add(this.savings1);
        t.checkExpect(this.b1.accounts, expected1);

        ILoA expected2 =
            new ConsLoA(this.ca1,
            new ConsLoA(this.savings1,
            new ConsLoA(this.check1,
            new MtLoA())));

        this.b1.add(this.ca1);
        t.checkExpect(this.b1.accounts, expected2);

        reset();
    }

    // Test the add removeAccount(s)
    public void testRemoveAccount(Tester t){
        reset();

        t.checkExpect(this.b1.accounts, new MtLoA());

        ILoA expected =
            new ConsLoA(this.ca1,
            new ConsLoA(this.savings1,
            new ConsLoA(this.check1,
            new MtLoA())));

        this.b1.add(this.check1);
        this.b1.add(this.savings1);
        this.b1.add(this.ca1);
        t.checkExpect(this.b1.accounts, expected);



        // Remove in a bank account using ID 1
        this.b1.removeAccount(1);

        ILoA expected1 =
            new ConsLoA(this.ca1,
            new ConsLoA(this.savings1,
            new MtLoA()));

        t.checkExpect(this.b1.accounts, expected1);

        // Remove in a bank account using ID 7
        this.b1.removeAccount(7);

        ILoA expected2 =
            new ConsLoA(this.ca1,
            new MtLoA());

        t.checkExpect(this.b1.accounts, expected2);


        // Remove in a bank account using ID 13
        this.b1.removeAccount(13);

        ILoA expected3 = new MtLoA();

        t.checkExpect(this.b1.accounts, expected3);

        reset();
    }
}
