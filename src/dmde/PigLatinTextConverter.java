package dmde;

import dmde.rules.NotEndsWithWayRule;
import dmde.rules.StartsWithConsonantRule;
import dmde.rules.StartsWithVowelRule;
import dmde.transformers.AddWayTransformer;
import dmde.transformers.MoveFirstLatterToEndTransformer;
import dmde.transformers.WordTransformer;

import java.util.*;

public class PigLatinTextConverter {

    private final List<WordTransformer> wordTransformers = new LinkedList<>();

    public PigLatinTextConverter() {
        initWordTransformers();
    }

    private void initWordTransformers() {
        wordTransformers.add(new AddWayTransformer(new StartsWithVowelRule(), new NotEndsWithWayRule()));
        wordTransformers.add(new MoveFirstLatterToEndTransformer(new StartsWithConsonantRule(), new NotEndsWithWayRule()));
    }

    public String convert(String text) {
        if (text.isBlank()) {
            return text;
        }

        StringBuilder result = new StringBuilder();

        int wordBeginIndex = 0;
        boolean currentCharIsPartOfWord = false;
        for (int i = 0; i < text.length(); i++) {
            if (!currentCharIsPartOfWord && isPartOfWord(text.charAt(i))) {
                wordBeginIndex = i;
                currentCharIsPartOfWord = true;
            } else if (currentCharIsPartOfWord && !isPartOfWord(text.charAt(i))) {
                result.append(convertWord(text.substring(wordBeginIndex, i)));
                currentCharIsPartOfWord = false;
            } else if (currentCharIsPartOfWord && i == text.length() - 1) {
                result.append(convertWord(text.substring(wordBeginIndex)));
            }
            if (!currentCharIsPartOfWord) {
                result.append(text.charAt(i));
            }
        }

        return result.toString();
    }

    private boolean isPartOfWord(char c) {
        return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || c == '\'' || c == '’';
    }

    private char[] convertWord(String word) {
        char[] wordChars = word.toCharArray();
        for (WordTransformer wordTransformer : wordTransformers) {
            if (wordTransformer.applies(wordChars)) {
                Set<Integer> upperCaseIndexes = getUpperCaseIndexes(wordChars);
                Set<Integer> commaTypeOneBackwardIndexes = getCommaBackwardIndexes(wordChars, '\'');
                Set<Integer> commaTypeTwoBackwardIndexes = getCommaBackwardIndexes(wordChars, '’');

                wordChars = wordTransformer.transform(wordChars);

                fixUpperCases(wordChars, upperCaseIndexes);
                fixCommaPositions(wordChars, commaTypeOneBackwardIndexes, '\'');
                fixCommaPositions(wordChars, commaTypeTwoBackwardIndexes, '’');
                break;
            }
        }

        return wordChars;
    }

    private Set<Integer> getCommaBackwardIndexes(char[] word, char commaType) {
        Set<Integer> commaBackwardIndexes = new HashSet<>();
        int backwardIndex = 1;
        for (int i = word.length - 1; i >= 0; i--) {
            if (word[i] == commaType) {
                commaBackwardIndexes.add(backwardIndex);
            }
            backwardIndex++;
        }

        return commaBackwardIndexes;
    }

    private void fixCommaPositions(char[] word, Set<Integer> commaBackwardIndexes, char commaType) {
        if (commaBackwardIndexes.isEmpty()) {
            return;
        }
        Iterator<Integer> commaBackwardIndexIterator = commaBackwardIndexes.iterator();
        int backwardIndex = 1;
        for (int i = word.length - 1; i >= 0; i--) {
            if (word[i] == commaType) {
                int correctBackwardIndex = commaBackwardIndexIterator.next();
                if (correctBackwardIndex != backwardIndex) {
                    System.arraycopy(word, i + 1, word, i, (word.length - i - correctBackwardIndex));
                    word[word.length - correctBackwardIndex] = commaType;
                }
            }
            backwardIndex++;
        }
    }

    private Set<Integer> getUpperCaseIndexes(char[] word) {
        Set<Integer> upperCaseIndexes = new HashSet<>();
        for (int i = 0; i < word.length; i++) {
            if (Character.isUpperCase(word[i])) {
                upperCaseIndexes.add(i);
            }
        }

        return upperCaseIndexes;
    }

    private void fixUpperCases(char[] word, Set<Integer> upperCaseIndexes) {
        if (upperCaseIndexes.isEmpty()) {
            return;
        }
        for (int i = 0; i < word.length; i++) {
            if (Character.isUpperCase(word[i]) && !upperCaseIndexes.contains(i)) {
                word[i] = Character.toLowerCase(word[i]);
            } else if (Character.isLowerCase(word[i]) && upperCaseIndexes.contains(i)) {
                word[i] = Character.toUpperCase(word[i]);
            }
        }
    }
}
