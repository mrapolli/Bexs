package br.com.bexs.process;

import br.com.bexs.model.RouteModel;
import br.com.bexs.service.RouteService;
import br.com.bexs.util.Node;
import br.com.bexs.vo.RouteRequest;
import br.com.bexs.vo.RouteResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        assertEquals(java.util.Optional.of(10), res.getPrice());
    }

    @Test
    public void convertNodeTest(){
        var route = new RouteModel();
        route.setOrigem("GRU");
        route.setDestino("BRC");
        route.setCost(10);
        var graph = calcProcess.convertNode(Arrays.asList(route));
        var nodes = graph.getNodes().stream().findFirst().get();
        assertEquals(nodes.getName(),route.getOrigem());

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
        assertEquals(res.getName(),route.getOrigem());
    }
}
