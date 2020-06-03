package br.com.bexs.process;

import br.com.bexs.model.RouteModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class CsvProcess {

    private static File FILE = null;

    private static String FILEPATH;

    public List<RouteModel> getCsvToNode(String path) {

        List<RouteModel> nodesList = new ArrayList<>();
        verify(path);
        try(var br = new BufferedReader(new FileReader(FILE));) {
          String st;
            while ((st = br.readLine()) != null && !st.isEmpty()) {

                var rotas = st.split(",");
                var routeMode = new RouteModel();
                routeMode.setOrigem(rotas[0]);
                routeMode.setDestino(rotas[1]);
                routeMode.setCost(Integer.parseInt(rotas[2]));
                nodesList.add(routeMode);

            }
        } catch (Exception e) {
            log.error("Erro leitura do arquivo ");
        }


        return nodesList;

    }

    public void putRoute(String routeOrig, String routeDest, int cost) {

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(FILE, true))) {
            writer.append(routeOrig.toUpperCase() + "," + routeDest.toUpperCase() + "," + cost);
            writer.append('\n');
            reloadFile(FILEPATH);

        } catch (Exception e) {
            log.error("Verifique seu arquivo de input " + FILEPATH);
        }
    }

    public void reloadFile(String path) {
        FILE = new File(path);
    }

    private void verify(String path){
        if (path != null && !path.isEmpty())
            FILEPATH = path;

        if (FILE == null)
            FILE = new File(path);
    }


}
