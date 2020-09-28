package dmde.transformers;

import dmde.rules.Rule;

import java.util.Arrays;

public class MoveFirstLatterToEndTransformer extends WordTransformer {

    public MoveFirstLatterToEndTransformer(Rule... rules) {
        super(rules);
    }

    @Override
    public char[] transform(char[] word) {
        char[] newWord = Arrays.copyOf(word, word.length + 2);
        newWord[newWord.length - 2] = newWord[0];
        System.arraycopy(newWord, 1, newWord, 0, newWord.length - 1);
        newWord[newWord.length - 2] = 'a';
        newWord[newWord.length - 1] = 'y';

        return newWord;
    }
}
