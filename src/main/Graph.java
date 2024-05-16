package main;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Graph {
    //node of synset ID to all synset IDs
    private final Map<Integer, Set<Integer>> nodeNeighborsMap;

    public Graph() {
        nodeNeighborsMap = new HashMap<>();
    }


    public void addNode(int nodeId) {
        if (!nodeNeighborsMap.containsKey(nodeId)) {
            nodeNeighborsMap.put(nodeId, new HashSet<>());
        }
    }

    public void addEdge(int nodeId1, int nodeId2) {
        addNode(nodeId1);
        addNode(nodeId2);
        nodeNeighborsMap.get(nodeId1).add(nodeId2);
    }

    public Set<Integer> neighbors(int nodeId) {
        Set<Integer> neighbors = nodeNeighborsMap.get(nodeId);
        if (neighbors == null) {
            return new HashSet<>(); //if node has no neighbors return empty
        }
        return neighbors;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Integer, Set<Integer>> entry : nodeNeighborsMap.entrySet()) {
            sb.append(entry.getKey()).append(": ");
            for (Integer neighborId : entry.getValue()) {
                sb.append(neighborId).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Test
    public void testNoNeighbors() {
        Graph graph = new Graph();
        graph.addNode(1);
        assertTrue(graph.neighbors(1).isEmpty());
    }
}
