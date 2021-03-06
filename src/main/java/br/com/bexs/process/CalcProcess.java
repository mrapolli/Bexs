package br.com.bexs.process;

import br.com.bexs.model.RouteModel;
import br.com.bexs.util.Graph;
import br.com.bexs.util.Node;
import br.com.bexs.util.NodeCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

@Component
public class CalcProcess {

    @Autowired
    NodeCreator nodeCreator;

    public void calculate(Node source) {

        var settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();
        source.setPrice(0);
        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestPriceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Entry<Node, Integer> adjacencyPair : currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                var edgeWeigh = adjacencyPair.getValue();

                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinCost(adjacentNode, edgeWeigh, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
    }

    public Graph convertNode(List<RouteModel> routeModelList) {

        for (RouteModel rota : routeModelList) {

            nodeCreator.createNode(rota.getOrigem(), rota.getDestino(), rota.getCost());
        }

        Graph graph = new Graph();
        graph.setNodes(nodeCreator.getNodes());

        return graph;
    }


    public Node getNode(String nodeName, Graph graph) {
        final Node[] node = {null};
        graph.getNodes().forEach(node1 -> {
            if (node1.getName().equals(nodeName)) {
                node[0] = node1;
            }

        });
        return node[0];

    }


    private static Node getLowestPriceNode(Set<Node> unsettledNodes) {
        Node lowestPriceNode = null;
        var lowestPrice = Integer.MAX_VALUE;
        for (Node node : unsettledNodes) {
            int nodePrice = node.getPrice();
            if (nodePrice < lowestPrice) {
                lowestPrice = nodePrice;
                lowestPriceNode = node;
            }
        }
        return lowestPriceNode;
    }

    private static void calculateMinCost(Node evaluationNode, Integer val, Node sourceNode) {
        var price = sourceNode.getPrice();
        if (price + val < evaluationNode.getPrice()) {
            evaluationNode.setPrice(price + val);
            var lowestPrice = new LinkedList<>(sourceNode.getCost());
            lowestPrice.add(sourceNode);
            evaluationNode.setCost(lowestPrice);
        }
    }
}
