package br.com.bexs.service;


import br.com.bexs.model.RouteModel;
import br.com.bexs.process.CalcProcess;
import br.com.bexs.process.CsvProcess;
import br.com.bexs.util.Graph;
import br.com.bexs.util.Node;
import br.com.bexs.vo.RouteResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
public class RouteService {

    @Autowired
    private CsvProcess csvProcess;

    @Autowired
    private CalcProcess calcProcess;

    private Graph graph;

    private static final String ERRO = "Verifique se sua entrada está no padrão NNN-NNN";
    private static final String TRY = "try again:";
    private static final String BEST = "best route: ";

    public RouteResponse getRoute(String route) {
        var result = process(null, route);
        var response = new RouteResponse();
        response.setBestRoute(result);
        return response;

    }

    public boolean persistRoute(String origem, String destino, int cost) {
        csvProcess.putRoute(origem, destino, cost);
        reload();
        return true;
    }

    public void reload() {
        List<RouteModel> models = csvProcess.getCsvToNode(null);
        graph = calcProcess.convertNode(models);
    }


    public String process(String pathFile, String route) {
        StringBuilder builder = new StringBuilder();
        var origDest = parseRoute(route);
        if (origDest != null) {

            List<RouteModel> models = csvProcess.getCsvToNode(pathFile);

            graph = calcProcess.convertNode(models);

            Node nd = calcProcess.getNode(origDest[0], graph);
            if (nd != null) {
                calcProcess.calculate(nd);

                AtomicReference<Integer> price = new AtomicReference<>();
                AtomicReference<String> name = new AtomicReference<>();
                builder.append(BEST);
                for (Node node : graph.getNodes()) {

                    node.getCost().stream().filter(node1 -> node.getName().equals(origDest[1])).forEach(node1 -> {
                        builder.append(node1.getName() + " - ");
                        price.getAndSet(node.getPrice());
                        name.getAndSet(node.getName());
                    });

                }

                builder.append(name + " > $" + price.get());
                log.info(builder.toString());

            }

        }

        return builder.toString();

    }


    private String[] parseRoute(String route) {
        try {

            var origDest = route.toUpperCase().split("-");
            if (origDest.length != 2) {
                log.error(ERRO);
                return null;
            }

            return origDest;
        } catch (Exception e) {
            log.error(ERRO);
            return null;
        }

    }

    public void readFile(String arg) {
        csvProcess.getCsvToNode(arg);
    }
}
