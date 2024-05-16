package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class HyponymsHandler extends NgordnetQueryHandler {

    private WordNet wordNet;
    public HyponymsHandler(WordNet wordNet) {
        this.wordNet = wordNet;
    }
    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        if (words == null || words.isEmpty()) {
            return "[]";
        }

        Set<String> commonHyponyms = null;
        boolean isFirstWord = true;

        for (String word : words) {
            Set<String> wordHyponyms = wordNet.hyponyms(word);

            if (isFirstWord) {
                commonHyponyms = new TreeSet<>(wordHyponyms);
                isFirstWord = false;
            } else {
                commonHyponyms.retainAll(wordHyponyms);
            }
        }

        if (commonHyponyms != null) {
            return commonHyponyms.toString();
        } else {
            return "[]";
        }
    }
}
