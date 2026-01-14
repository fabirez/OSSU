def counts(L):
    """ L is a list of Employee and Person objects 
    Returns a tuple of a count of:
      * how many Person objects are in L
      * how many Employee objects are in L 
      * the number of unique names among Employee and Person objects """
    cPerson=0;
    cEmployee=0;
    D={}
    for el in L:
        if(type(el) == Person):
            cPerson+=1;
        else:
            cEmployee+=1;
        D[el.get_name()]=0;

    uNames = len(D.keys())
    return (cPerson, cEmployee, uNames)

