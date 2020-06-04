package br.com.bexs.service;

import br.com.bexs.model.RouteModel;
import br.com.bexs.process.CsvProcess;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RouteServiceTest {

    @Autowired
    private RouteService routeService;

    @MockBean
    private CsvProcess csvProcess;


    @Test
    public void processTest() throws IOException {
        File file = File.createTempFile("rotas.csv", null);
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.append("GRU" + "," + "BRC" + "," + 10);
        }
        var route = new RouteModel();
        route.setOrigem("GRU");
        route.setDestino("BRC");
        route.setCost(10);
        when(csvProcess.getCsvToNode(file.getAbsolutePath())).thenReturn(Arrays.asList(route));
        var result = routeService.process(file.getAbsolutePath(),"GRU-BRC");
        assertEquals(result,"best route: GRU - BRC > $10");

    }

    @Test
    public void getRouteTest() {
        var route = new RouteModel();
        route.setOrigem("GRU");
        route.setDestino("MIA");
        route.setCost(10);
        when(csvProcess.getCsvToNode(null)).thenReturn(Arrays.asList(route));
        var result = routeService.getRoute("GRU-MIA");
        assertEquals(result.getBestRoute(),"best route: GRU - MIA > $10");

    }

    @Test
    public void persistRouteTest() {
        var route = new RouteModel();
        route.setOrigem("GRU");
        route.setDestino("MIA");
        route.setCost(10);
        when(csvProcess.getCsvToNode(null)).thenReturn(Arrays.asList(route));
        var result = routeService.persistRoute(route.getDestino(),route.getOrigem(),route.getCost());
        assertEquals(result,true);

    }
}
