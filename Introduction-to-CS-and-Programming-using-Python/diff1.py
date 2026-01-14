#Teacher
def counts(L):
    """ L is a list of Employee and Person objects 
    Returns a tuple of a count of:
      * how many Person objects are in L
      * how many Employee objects are in L 
      * the number of unique names among Employee and Person objects """
    counte, countp, countn = 0,0,0
    names = []
    for i in L:
        if type(i) == Person:
            countp += 1
        elif type(i) == Employee:
            counte += 1
        if i.get_name() not in names:
            names.append(i.get_name())
            countn += 1
    return (countp, counte, countn)

