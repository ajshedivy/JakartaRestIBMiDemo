package com.github.ajshedivy.restfuldemo.resources;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class EmployeeTest {

    public static void parseRequestOutput(Response response) {
        if (response.getStatus() == 200) {
            String body = response.readEntity(String.class);
            System.out.println(body);
        } else {
            System.out.println("Error: " + response.getStatus());
        }
    }

    public static void main(String[] args) {
        Client client = ClientBuilder.newClient();
        Response response = client.target("http://idevphp.idevcloud.com:8080/restfuldemo/api/books")
                .request(MediaType.APPLICATION_JSON)
                .get();

        parseRequestOutput(response);

        WebTarget employeeTarget = client.target("http://idevphp.idevcloud.com:8080/restfuldemo/api/employee")
                .queryParam("id", "000010");
        Response response2 = employeeTarget.request(MediaType.APPLICATION_JSON).get();

        parseRequestOutput(response2);

    }
}