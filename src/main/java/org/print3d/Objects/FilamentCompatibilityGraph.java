package org.print3d.Objects;

import java.util.*;

public class FilamentCompatibilityGraph {
    private Map<String, Set<String>> adjacencyList;

    public FilamentCompatibilityGraph() {
        adjacencyList = new HashMap<>();
    }

    public void addNode(String node) {
        adjacencyList.putIfAbsent(node, new HashSet<>());
    }

    public void addEdge(String source, String destination) {
        adjacencyList.get(source).add(destination);
        adjacencyList.get(destination).add(source);
    }

    public Set<String> getCompatibleNodes(String node) {
        return adjacencyList.getOrDefault(node, new HashSet<>());
    }

    public boolean areCompatible(String node1, String node2) {
        Set<String> compatibleWithNode1 = adjacencyList.getOrDefault(node1, new HashSet<>());
        return compatibleWithNode1.contains(node2);
    }

    public void removeNode(String node) {
        adjacencyList.values().forEach(e -> e.remove(node));
        adjacencyList.remove(node);
    }

    public void removeEdge(String source, String destination) {
        adjacencyList.get(source).remove(destination);
        adjacencyList.get(destination).remove(source);
    }
}