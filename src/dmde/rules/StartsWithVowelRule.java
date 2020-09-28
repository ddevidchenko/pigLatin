package dmde.rules;

public class StartsWithVowelRule implements Rule {

    @Override
    public boolean applies(char[] word) {
        return "aeiouy".indexOf(Character.toLowerCase(word[0])) != -1;
    }
}
