package br.com.bexs.process;

import br.com.bexs.model.RouteModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CsvProcessTest {

    @Autowired
    CsvProcess csvProcess;

    private File file;

    @Before
    public void setup() throws IOException {
        file = File.createTempFile("rotas.csv", null);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.append("GRU" + "," + "BRC" + "," + 10);
            writer.append('\n');
        }

    }

    @Test
    public void getCsvToNodeTest() {

        var route = new RouteModel();
        route.setOrigem("GRU");
        route.setDestino("BRC");
        route.setCost(10);
        var lstNodes = csvProcess.getCsvToNode(file.getAbsolutePath());
        assertEquals(lstNodes.stream().findFirst().get().getDestino(), route.getDestino());
        assertEquals(lstNodes.stream().findFirst().get().getOrigem(), route.getOrigem());
        assertEquals(lstNodes.stream().findFirst().get().getCost(), route.getCost());

    }

    @Test
    public void putRouteTest() throws IOException {

        csvProcess.getCsvToNode(file.getAbsolutePath());
        csvProcess.putRoute("MIA", "CDG", 10);

        try (var br = new BufferedReader(new FileReader(file))) {
            var compare = br.lines().filter(s -> s.equals("MIA,CDG,10")).findFirst();
            assertEquals("MIA,CDG,10", compare.get());
        }

    }


}
