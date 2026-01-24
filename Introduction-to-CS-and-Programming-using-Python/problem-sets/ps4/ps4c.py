# Problem Set 4C
# Name:
# Collaborators:

import json
import ps4b # Importing your work from Part B

my_message = ps4b.Message("My message!")
### HELPER CODE ###
def load_words(file_name):
    '''
    file_name (string): the name of the file containing
    the list of words to load

    Returns: a list of valid words. Words are strings of lowercase letters.

    Depending on the size of the word list, this function may
    take a while to finish.
    '''
    # inFile: file
    with open(file_name, 'r') as inFile:
        # wordlist: list of strings
        wordlist = []
        for line in inFile:
            wordlist.extend([word.lower() for word in line.split(' ')])
        return wordlist


def is_word(word_list, word):
    '''
    Determines if word is a valid word, ignoring
    capitalization and punctuation

    word_list (list): list of words in the dictionary.
    word (string): a possible word.

    Returns: True if word is in word_list, False otherwise

    Example:
    >>> is_word(word_list, 'bat') returns
    True
    >>> is_word(word_list, 'asdf') returns
    False
    '''
    word = word.strip(" !@#$%^&*()-_+={}[]|\:;'<>?,./\"").lower()
    return word in word_list


def get_story_string():
    """
    Returns: a story in encrypted text.
    """
    f = open("story.txt", "r")
    story = str(f.read())
    f.close()
    return story[:-1]


def get_story_pads():
    with open('pads.txt') as json_file:
        return json.load(json_file)


WORDLIST_FILENAME = 'words.txt'

def print_help(p,a):
    print("\n***************************************")
    print(p, a);
    print("***************************************")

### END HELPER CODE ###


def decrypt_message_try_pads(ciphertext, pads):
    '''
    Given a string ciphertext and a list of possible pads
    used to create it find the pad used to create the ciphertext

    We will consider the pad used to create it the pad which
    when used to decrypt ciphertext results in a plaintext
    with the most valid English words. In the event of ties return
    the last pad that results in the maximum number of valid English words.

    ciphertext (EncryptedMessage): The ciphertext
    pads (list of lists of ints): A list of pads which might have been used
        to encrypt the ciphertext

    Returns: (PlaintextMessage) A message with the decrypted ciphertext and the best pad
    '''
    l = []
    for i in range(len(pads)):
        l.append(ciphertext.decrypt_message(pads[i]).get_text());

    result=None
    i=0;
    D={}
    for idx in range(len(l)):
        D[idx]=0;
        words = l[idx].split(" ")
        for word in words:
            if(is_word(load_words(WORDLIST_FILENAME), word)):
                D[idx]+=1;
                break;
            else:
                continue;


    result = None;
    many_words = max(D.values())
    if(many_words == 0):
           result = ps4b.PlaintextMessage(l[len(l) - 1], pads[len(pads) - 1]);
    else:
        for k,v in D.items():
            if(v == max(D.values())):
               print_help("Hello:",v)
               result = ps4b.PlaintextMessage(l[k], pads[k]);
               break;
            else:
                continue;

    return result


def decode_story():
    '''
    Write your code here to decode Bob's story using a list of possible pads
    Hint: use the helper functions get_story_string and get_story_pads and your EncryptedMessage class.

    Returns: (string) the decoded story

    '''
    # ciphertext of Bob's story 
    c = get_story_string()
    e = ps4b.EncryptedMessage(c)
    # list of Bob's one time pads.
    p = get_story_pads()
    return decrypt_message_try_pads(e, p)



if __name__ == '__main__':
    # Uncomment these lines to try running decode_story()
    story = decode_story()
    print("Decoded story: ", story)

