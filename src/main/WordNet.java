package main;

import edu.princeton.cs.algs4.In;
import java.util.*;

public class WordNet {
    private Map<Integer, Set<String>> synsets;
    private Map<String, Set<Integer>> wordsToIds;
    private Graph graph;

    public WordNet(String synsetFilePath, String hyponymFilePath) {
        this.synsets = new HashMap<>();
        this.wordsToIds = new HashMap<>();
        this.graph = new Graph();

        parseSynsets(synsetFilePath);
        parseHyponyms(hyponymFilePath);
    }

    private void parseSynsets(String filename) {
        In in = new In(filename);
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] parts = line.split(",");
            int id = Integer.parseInt(parts[0]);
            String[] words = parts[1].split(" ");

            synsets.put(id, new HashSet<>(Arrays.asList(words)));
            for (String word : words) {
                wordsToIds.putIfAbsent(word, new HashSet<>());
                wordsToIds.get(word).add(id);
            }
        }
    }

    private void parseHyponyms(String filename) {
        In in = new In(filename);
        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] parts = line.split(",");
            int synsetId = Integer.parseInt(parts[0]);
            graph.addNode(synsetId);

            for (int i = 1; i < parts.length; i++) {
                int hyponymId = Integer.parseInt(parts[i]);
                graph.addEdge(synsetId, hyponymId);
            }
        }
    }
    //one word case
    public Set<String> hyponyms(String word) {
        Set<String> result = new TreeSet<>(); //TreeSet for sorted order
        if (wordsToIds.containsKey(word)) {
            for (int id : wordsToIds.get(word)) {
                traverseGraph(id, result);
            }
        }
        return result;
    }

    //multiple words case
    public Set<String> hyponyms(String[] words) {
        Set<String> commonHyponyms = new TreeSet<>();
        boolean isFirstWord = true;

        for (String word : words) {
            Set<String> wordHyponyms = new TreeSet<>();
            if (wordsToIds.containsKey(word)) {
                for (int id : wordsToIds.get(word)) {
                    traverseGraph(id, wordHyponyms);
                }
            }

            if (isFirstWord) {
                commonHyponyms.addAll(wordHyponyms);
                isFirstWord = false;
            } else {
                commonHyponyms.retainAll(wordHyponyms);
            }
        }

        return commonHyponyms;
    }

    private void traverseGraph(int nodeId, Set<String> result) {
        if (synsets.containsKey(nodeId)) {
            result.addAll(synsets.get(nodeId));
            for (int neighborId : graph.neighbors(nodeId)) {
                traverseGraph(neighborId, result);
            }
        }
    }

}
