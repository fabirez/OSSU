# Problem Set 2, hangman.py
# Name:
# Collaborators:
# Time spent:

import random
import string

# -----------------------------------
# HELPER CODE
# -----------------------------------

WORDLIST_FILENAME = "words.txt"

def load_words():
    """
    returns: list, a list of valid words. Words are strings of lowercase letters.

    Depending on the size of the word list, this function may
    take a while to finish.
    """
    print("Loading word list from file...")
    # inFile: file
    inFile = open(WORDLIST_FILENAME, 'r')
    # line: string
    line = inFile.readline()
    # wordlist: list of strings
    wordlist = line.split()
    print(" ", len(wordlist), "words loaded.")
    return wordlist

def choose_word(wordlist):
    """
    wordlist (list): list of words (strings)

    returns: a word from wordlist at random
    """
    return random.choice(wordlist)

# -----------------------------------
# END OF HELPER CODE
# -----------------------------------


# Load the list of words to be accessed from anywhere in the program
wordlist = load_words()

def has_player_won(secret_word, letters_guessed):
    """
    secret_word: string, the lowercase word the user is guessing
    letters_guessed: list (of lowercase letters), the letters that have been
        guessed so far

    returns: boolean, True if all the letters of secret_word are in letters_guessed,
        False otherwise
    """
    # FILL IN YOUR CODE HERE AND DELETE "pass"
    res = list(secret_word);

    # For loop, that remove all the duplicate characters from the secret_word 
    # e.g apple -> aple
    for i in range(len(secret_word)):
        flag = False;
        j = i + 1
        while(j < len(secret_word)):
            if(secret_word[i] == secret_word[j]):
                res.remove(secret_word[j]) 
            j += 1;


    for el in letters_guessed:
        if(el in res):
            res.remove(el);

    return True if len(res) == 0 else False; 


def get_word_progress(secret_word, letters_guessed):
    """
    secret_word: string, the lowercase word the user is guessing
    letters_guessed: list (of lowercase letters), the letters that have been
        guessed so far

    returns: string, comprised of letters and asterisks (*) that represents
        which letters in secret_word have not been guessed so far
    """
    # FILL IN YOUR CODE HERE AND DELETE "pass"
    if(len(letters_guessed) == 0):
        return "*" * len(secret_word)
    if(len(secret_word) == 0):
        return ""

    res = [];
    for el in secret_word:
        if(el in letters_guessed):
            res.append(el);
        else:
            res.append("*")
    return ''.join(res)


def get_available_letters(letters_guessed):
    """
    letters_guessed: list (of lowercase letters), the letters that have been
        guessed so far

    returns: string, comprised of letters that represents which
      letters have not yet been guessed. The letters should be returned in
      alphabetical order
    """
    # FILL IN YOUR CODE HERE AND DELETE "pass"
    alphabet = "abcdefghijklmnopqrstuvwxyz"
    if(len(letters_guessed) == 0): 
        return alphabet;
    alphabet_list = list(alphabet)
    
    for el in letters_guessed:
        try:
            alphabet_list.remove(el)
        except ValueError:
            continue

    return "".join(alphabet_list)


def get_letter_to_reveal(secret_word,letters_guessed):
    """
    secret_word: string, the lowercase word the user is guessing
    letters_guessed: list (of lowercase letters), the letters that have been
        guessed so far

    returns: string, a random character that has not been guessed yet
    """
    # FILL IN YOUR CODE HERE AND DELETE "pass"

    res = list(secret_word);
    # For loop, that remove all the duplicate characters from the secret_word 
    # e.g apple -> aple
    for i in range(len(secret_word)):
        flag = False;
        j = i + 1
        while(j < len(secret_word)):
            if(secret_word[i] == secret_word[j]):
                res.remove(secret_word[j]) 
            j += 1;

    for el in letters_guessed:
        if(el in res):
            res.remove(el);

    choose_from = res
    new = random.randint(0, len(choose_from)-1)
    revealed_letter = choose_from[new]
    return revealed_letter;



def hangman(secret_word, with_help):
    """
    secret_word: string, the secret word to guess.
    with_help: boolean, this enables help functionality if true.

    Starts up an interactive game of Hangman.

    * At the start of the game, let the user know how many
      letters the secret_word contains and how many guesses they start with.

    * The user should start with 10 guesses.

    * Before each round, you should display to the user how many guesses
      they have left and the letters that the user has not yet guessed.

    * Ask the user to supply one guess per round. Remember to make
      sure that the user puts in a single letter (or help character '!'
      for with_help functionality)

    * If the user inputs an incorrect consonant, then the user loses ONE guess,
      while if the user inputs an incorrect vowel (a, e, i, o, u),
      then the user loses TWO guesses.

    * The user should receive feedback immediately after each guess
      about whether their guess appears in the computer's word.

    * After each guess, you should display to the user the
      partially guessed word so far.

    -----------------------------------
    with_help functionality
    -----------------------------------
    * If the guess is the symbol !, you should reveal to the user one of the
      letters missing from the word at the cost of 3 guesses. If the user does
      not have 3 guesses remaining, print a warning message. Otherwise, add
      this letter to their guessed word and continue playing normally.

    Follows the other limitations detailed in the problem write-up.
    """
    # FILL IN YOUR CODE HERE AND DELETE "pass"
    guesses = 10;
    letter_used = []
    letters_guessed = []
    current_state_of_secret = "*" * len(secret_word)
    current_state_of_letters = get_available_letters(letter_used)
    print("Welcome to Hangman!")
    print(f"I'm thinking of a word that is {len(secret_word)} letters long")
    print("--------------------------------------------")

    while(guesses > 0 ):
        print(f"You have {guesses} guesses left")
        print(f"Available letters:{current_state_of_letters}")
        user_char = input("Please guess a letter:");
        if(user_char in secret_word):
            letter_used.append(user_char);
            letters_guessed.append(user_char)
            current_state_of_secret = get_word_progress(secret_word, letter_used)
            current_state_of_letters = get_available_letters(letter_used)
            print(f"Good guess: {current_state_of_secret}")
            if(has_player_won(secret_word, letters_guessed) == True):
                print("--------------------------------------------")
                print("You won!!!")
                total_score =  (guesses + 4 * len(letters_guessed)) + (3 * len(secret_word))
                print(f"Your total score for this game is {total_score}")
                print("Thank you for playing")
                break;
        elif(user_char == '!'):
            if(guesses < 3):
                print("--------------------------------------------")
                print("Sorry you can't use this bonus")
                print("You need at least 3 guesses")
            else:
                guesses -= 3
                hint = get_letter_to_reveal(secret_word, letters_guessed)
                letters_guessed.append(hint)
                letter_used.append(hint);
                current_state_of_secret = get_word_progress(secret_word, letter_used)
                current_state_of_letters = get_available_letters(letter_used)
                print(f"Letter revealed: {hint}")
                print("Good luck!")
                print(f"With the hint: {current_state_of_secret}")
                if(has_player_won(secret_word, letters_guessed) == True):
                    print("--------------------------------------------")
                    print("You won!!!")
                    print("Thank you for playing")
                    total_score =  (guesses + 4 * len(letters_guessed)) + (3 * len(secret_word))
                    print(f"Your total score for this game is {total_score}")
                    break;
        else:
            if(user_char in "aeiou"):
                guesses -= 2;
            else:
                guesses -= 1;
            letter_used.append(user_char);
            current_state_of_letters = get_available_letters(letter_used)
            print(f"Oops! That letter is not in my word: {current_state_of_secret}")

        print("--------------------------------------------")

    if(guesses == 0):
        print(f"The word was: {secret_word}.")





# When you've completed your hangman function, scroll down to the bottom
# of the file and uncomment the lines to test

if __name__ == "__main__":
    # To test your game, uncomment the following three lines.
    secret_word = choose_word(wordlist)
    with_help = True 
    hangman(secret_word, with_help)
