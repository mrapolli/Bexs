package br.com.bexs.util;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class NodeCreator {

    private Set<Node> nodes = new HashSet<>();


    public void createNode(String nodeOrig, String nodeDest, Integer cost) {

        Node nodeO;
        Node nodeD;

        var nodOrigem = nodes.stream().filter(node ->
                node.getName().equals(nodeOrig)
        ).findFirst();

        if (nodOrigem.isPresent()) {
            nodeO = nodOrigem.get();
        } else {
            nodeO = new Node(nodeOrig);
            nodes.add(nodeO);
        }

        var nodDestino = nodes.stream().filter(node ->
                node.getName().equals(nodeDest)
        ).findFirst();

        if (nodDestino.isPresent()) {
            nodeD = nodDestino.get();
        } else {
            nodeD = new Node(nodeDest);
            nodes.add(nodeD);
        }

        nodeO.addDestination(nodeD, cost);

    }

    public Set<Node> getNodes() {
        return nodes;
    }
}
