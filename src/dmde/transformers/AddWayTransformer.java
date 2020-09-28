package dmde.transformers;

import dmde.rules.Rule;

import java.util.Arrays;

public class AddWayTransformer extends WordTransformer {

    public AddWayTransformer(Rule... rules) {
        super(rules);
    }

    @Override
    public char[] transform(char[] word) {
        char[] newWord = Arrays.copyOf(word, word.length + 3);
        newWord[newWord.length - 3] = 'w';
        newWord[newWord.length - 2] = 'a';
        newWord[newWord.length - 1] = 'y';

        return newWord;
    }
}
