package com.github.ajshedivy.restfuldemo;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class EmployeeTest {
    public static void main(String[] args) {
        Client client = ClientBuilder.newClient();
        Response response = client.target("http://localhost:8080/restfuldemo/api/employee")
                .queryParam("id", "000020")
                .request(MediaType.APPLICATION_JSON)
                .get();

        if (response.getStatus() == 200) {
            String body = response.readEntity(String.class);
            System.out.println(body);
        } else {
            System.out.println("Error: " + response.getStatus());
        }
    }
}