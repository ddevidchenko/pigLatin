package dmde.rules;

public class NotEndsWithWayRule implements Rule {

    @Override
    public boolean applies(char[] word) {
        return word.length < 3
                || (word[word.length - 3] != 'w' && word[word.length - 3] != 'W')
                || (word[word.length - 2] != 'a' && word[word.length - 2] != 'A')
                || (word[word.length - 1] != 'y' && word[word.length - 1] != 'Y');
    }
}
