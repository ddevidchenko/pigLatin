package dmde.transformers;

import dmde.rules.Rule;

import javax.xml.stream.events.Characters;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class WordTransformer {
    private final Collection<Rule> rules;

    public WordTransformer(Rule... rules) {
        this.rules = List.of(rules);
    }

    public boolean applies(char[] word) {
        return rules.stream().allMatch(rule -> rule.applies(word));
    }

    public abstract char[] transform(char[] word);
}
