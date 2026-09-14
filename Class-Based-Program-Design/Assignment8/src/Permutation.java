/*
- [x] Design the method decode that will consume the encoded message and use the ArrayList code to decipher the message,
      one character at a time.

      Look up methods you may use with the data of the type String in the Java documentation.
      You may assume all the characters in the string can be found in the alphabet field 
      (and therefore, the code field as well).

- [x] Design the method encode that will consume the message we wish to encode and use the ArrayList code to produce the encoded message,
      — again — one character at a time.

      You may assume all the characters in the string can be found in the alphabet field 
      (and therefore, the code field as well).

- [x] Design the method initEncoder that produces a random permutation of the 26 letters of the alphabet and returns it as an ArrayList of Characters.
		HINT: Make a copy of the alphabet list,
              then remove one character at random and add it to the encoder list,
              until all letters have been consumed.
*/

import java.util.*;
import tester.*;

/**
 * A class that defines a new permutation code, as well as methods for encoding
 * and decoding of the messages that use this code.
 */
class PermutationCode {
    // The original list of characters to be encoded
    ArrayList<Character> alphabet = 
        new ArrayList<Character>(Arrays.asList(
                    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 
                    'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 
                    't', 'u', 'v', 'w', 'x', 'y', 'z'));

    ArrayList<Character> code = new ArrayList<Character>(26);

    // A random number generator
    Random rand = new Random();

    // Create a new instance of the encoder/decoder with a new permutation code 
    PermutationCode() {
        this.code = this.initEncoder();
    }

    // Create a new instance of the encoder/decoder with the given code 
    PermutationCode(ArrayList<Character> code) {
        this.code = code;
    }

    // Initialize the encoding permutation of the characters
    // By defautl the code array will have the alphabeht shifted by one,
    // based on the asci code. (a->b, b->c ...)
    ArrayList<Character> initEncoder() {
        ArrayList<Character> newArr = new ArrayList<Character>();
        for (int i = 0;
            i < alphabet.size();
            i = i + 1) 
        {
            int asciiCode = alphabet.get(i);
            char newChar = (char) (asciiCode + 1);
            newArr.add(newChar);
        }
        return newArr;
    }

    // produce an encoded String from the given String
    String encode(String source) {
        source = source.toLowerCase();
        String res = "";
        for (int i = 0;
            i < source.length();
            i = i + 1) 
            {
                int getIdx = this.alphabet.indexOf(source.charAt(i));
                char charEncoded = this.code.get(getIdx);
                res = res + charEncoded;
            }
        return res;
    }

    // produce a decoded String from the given String
    String decode(String code) {
        code = code.toLowerCase();
        String res = "";
        for (int i = 0;
            i < code.length();
            i = i + 1) 
            {
                int getIdx = this.code.indexOf(code.charAt(i));
                char charDecoded = this.alphabet.get(getIdx);
                res = res + charDecoded;
            }
        return res;
    }

}

class ExamplesPermutation{
	void testGeneral(Tester t){
      ArrayList<Character> expectedCode = new ArrayList<Character>();
         expectedCode.add("b".charAt(0));
         expectedCode.add("c".charAt(0));
         expectedCode.add("d".charAt(0));
         expectedCode.add("e".charAt(0));
         expectedCode.add("f".charAt(0));
         expectedCode.add("g".charAt(0));
         expectedCode.add("h".charAt(0));
         expectedCode.add("i".charAt(0));
         expectedCode.add("j".charAt(0));
         expectedCode.add("k".charAt(0));
         expectedCode.add("l".charAt(0));
         expectedCode.add("m".charAt(0));
         expectedCode.add("n".charAt(0));
         expectedCode.add("o".charAt(0));
         expectedCode.add("p".charAt(0));
         expectedCode.add("q".charAt(0));
         expectedCode.add("r".charAt(0));
         expectedCode.add("s".charAt(0));
         expectedCode.add("t".charAt(0));
         expectedCode.add("u".charAt(0));
         expectedCode.add("v".charAt(0));
         expectedCode.add("w".charAt(0));
         expectedCode.add("x".charAt(0));
         expectedCode.add("y".charAt(0));
         expectedCode.add("z".charAt(0));
         expectedCode.add("{".charAt(0));

        t.checkExpect(new PermutationCode().encode("Hello"), "ifmmp");
        t.checkExpect(new PermutationCode().decode("Ifmmp"), "hello");

        t.checkExpect(new PermutationCode().encode("World"), "xpsme");
        t.checkExpect(new PermutationCode().decode("Xpsme"), "world");

        t.checkExpect(new PermutationCode().initEncoder(), expectedCode);

        // Test with given encoder
        ArrayList<Character> encoder = new ArrayList<Character>();
        encoder.add("z".charAt(0));
        encoder.add("y".charAt(0));
        encoder.add("x".charAt(0));
        encoder.add("w".charAt(0));
        encoder.add("v".charAt(0));
        encoder.add("u".charAt(0));
        encoder.add("t".charAt(0));
        encoder.add("s".charAt(0));
        encoder.add("r".charAt(0));
        encoder.add("q".charAt(0));
        encoder.add("p".charAt(0));
        encoder.add("o".charAt(0));
        encoder.add("n".charAt(0));
        encoder.add("m".charAt(0));
        encoder.add("l".charAt(0));
        encoder.add("k".charAt(0));
        encoder.add("j".charAt(0));
        encoder.add("i".charAt(0));
        encoder.add("h".charAt(0));
        encoder.add("g".charAt(0));
        encoder.add("f".charAt(0));
        encoder.add("e".charAt(0));
        encoder.add("d".charAt(0));
        encoder.add("c".charAt(0));
        encoder.add("b".charAt(0));
        encoder.add("a".charAt(0));

        PermutationCode pC = new PermutationCode(encoder);

        t.checkExpect(pC.encode("abc"), "zyx");
        t.checkExpect(pC.encode("zyx"), "abc");

        t.checkExpect(pC.encode("Hello"), "svool");
        t.checkExpect(pC.encode("World"), "dliow");
    }
}
