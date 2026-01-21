# Problem Set 4A
# Name:
# Collaborators:

from tree import Node # Imports the Node object used to construct trees

# Part A0: Data representation
# Fill out the following variables correctly.
# If correct, the test named test_data_representation should pass.
tree1 = Node(8,
             Node(2,Node(1),Node(6)),
             Node(10)
            ) 

tree2 = Node(7,
             Node(2,Node(1),Node(5,Node(3),Node(6))),
             Node(9,Node(8),Node(10))
            )

tree3 = Node(5,
             Node(3,Node(2),Node(4)),
             Node(14,Node(12),Node(21,Node(20),Node(26)))
            )

#######################################
# 1.2 Determining The Height of a Tree
#######################################


def find_tree_height(tree):
    '''
    Find the height of the given tree
    Input:
        tree: An element of type Node constructing a tree
    Output:
        The integer depth of the tree
    '''
    a=0;
    b=0;
    if tree == None:
        return -1;
    else:
        a=1+find_tree_height(tree.get_left_child());
        b=1+find_tree_height(tree.get_right_child());
    return a if a > b else b;

# Check with only root node.
tr1 = Node(13)
assert find_tree_height(tr1) == 0;

#  10
# 5  9

#  10
# 11  12

def is_heap(tree, compare_func):
    '''
    Determines if the tree is a max or min heap depending on compare_func
    Inputs:
        tree: An element of type Node constructing a tree
        compare_func: a function that compares the child node value to the parent node value
            i.e. op(child_value,parent_value) for a max heap would return True if child_value < parent_value and False otherwise
                 op(child_value,parent_value) for a min meap would return True if child_value > parent_value and False otherwise
    Output:
        True if the entire tree satisfies the compare_func function; False otherwise
    '''
    if(tree == None or (tree.get_left_child() == None and tree.get_right_child() == None)):
       return True;
    else:
        child_left = tree.get_left_child()
        child_right = tree.get_right_child()
        parent = tree.get_value()

        valid_left = True;
        valid_right= True;

        if(child_left != None):
            valid_left = compare_func(child_left.get_value(), parent) and is_heap(child_left, compare_func);

        if(child_right != None):
            valid_right = compare_func(child_right.get_value(), parent) and is_heap(child_right, compare_func);

        return valid_left and valid_right;

if __name__ == '__main__':
    # You can use this part for your own testing and debugging purposes.
    # IMPORTANT: Do not erase the pass statement below if you do not add your own code
    pass
