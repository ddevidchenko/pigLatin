package dmde.rules;

public class StartsWithConsonantRule implements Rule {

    @Override
    public boolean applies(char[] word) {
        return "bcdfghjklmnpqrstvwxz".indexOf(Character.toLowerCase(word[0])) != -1;
    }
}
