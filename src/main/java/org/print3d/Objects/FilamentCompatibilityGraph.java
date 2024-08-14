package org.print3d.Objects;

import org.print3d.DataStructures.Set;
import org.print3d.DataStructures.Tree;

public class FilamentCompatibilityGraph {
    private Tree<String, Set<String>> adjacencyList;

    public FilamentCompatibilityGraph() {
        adjacencyList = new Tree<>();
    }

    public void addNode(String node) {
        if (!adjacencyList.containsKey(node)) {
            adjacencyList.put(node, new Set<>());
        }
    }

    public void addEdge(String source, String destination) {
        adjacencyList.get(source).add(destination);
        adjacencyList.get(destination).add(source);
    }

    public Set<String> getCompatibleNodes(String node) {
        Set<String> result = adjacencyList.get(node);
        return result != null ? result : new Set<>();
    }

    public boolean areCompatible(String node1, String node2) {
        Set<String> compatibleWithNode1 = getCompatibleNodes(node1);
        return compatibleWithNode1.contains(node2);
    }

    public void removeNode(String node) {
        Set<String> keySet = adjacencyList.keySet();
        for (String key : keySet) {
            adjacencyList.get(key).remove(node);
        }
        adjacencyList.remove(node);
    }

    public void removeEdge(String source, String destination) {
        Set<String> sourceSet = adjacencyList.get(source);
        Set<String> destSet = adjacencyList.get(destination);
        if (sourceSet != null) sourceSet.remove(destination);
        if (destSet != null) destSet.remove(source);
    }
}