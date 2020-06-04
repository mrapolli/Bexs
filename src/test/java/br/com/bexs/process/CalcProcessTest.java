package br.com.bexs.process;

import br.com.bexs.model.RouteModel;
import br.com.bexs.util.Node;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CalcProcessTest {

    @Autowired
    CalcProcess calcProcess;

    @Test
    public void calculateTest() {
        Node nodeO = new Node("GRU");
        Node nodeD = new Node("BRC");
        var map = new HashMap<Node, Integer>();
        map.put(nodeD, 10);
        nodeO.setAdjacentNodes(map);
        calcProcess.calculate(nodeO);

        var res = nodeO.getAdjacentNodes().keySet().stream().findFirst().get();
        Integer integer = 10;
        assertEquals(integer, res.getPrice());
    }

    @Test
    public void convertNodeTest(){
        var route = new RouteModel();
        route.setOrigem("GRU");
        route.setDestino("BRC");
        route.setCost(10);
        var graph = calcProcess.convertNode(Arrays.asList(route));
        var nodes = graph.getNodes().stream().findFirst().get();
        assertEquals(nodes.getName(),route.getDestino());

    }

    @Test
    public void getNodeTest() {
        var route = new RouteModel();
        route.setOrigem("GRU");
        route.setDestino("BRC");
        route.setCost(10);
        var graph = calcProcess.convertNode(Arrays.asList(route));
        var node = calcProcess.getNode(route.getOrigem(),graph);
        var res = node.getAdjacentNodes().keySet().stream().findFirst().get();
        Integer integer = 2147483647;
        assertEquals(integer, res.getPrice());
        assertEquals(res.getName(),route.getDestino());
    }
}
