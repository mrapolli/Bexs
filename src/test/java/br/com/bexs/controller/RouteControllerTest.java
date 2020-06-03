package br.com.bexs.controller;

import br.com.bexs.service.RouteService;
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

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RouteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RouteService routeService;

    @Autowired
    private WebApplicationContext context;


    @Before
    public void setup() {
         mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();

    }

    @Test
    public void getRoute() throws Exception {
        var route = new RouteResponse();
        route.setBestRoute("best route: GRU - BRC - SCL - ORL - CDG > $40");
        when(routeService.getRoute(eq("GRU-CDG"))).thenReturn(route);
        mockMvc.perform(MockMvcRequestBuilders
                .get("/calc/route?origDest=GRU-CDG")
                .content(asJsonString(route))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void putRoute() throws Exception {
        var request = new RouteRequest();
        request.setRotaOrigem("GRU");
        request.setRotaDestino("MIA");
        request.setCost(0);
        when(routeService.persistRoute(eq("GRU"),eq("MIA"),eq(0))).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/calc/route")
                .content(asJsonString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }



    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
