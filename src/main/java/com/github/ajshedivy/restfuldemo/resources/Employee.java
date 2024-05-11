package com.github.ajshedivy.restfuldemo.resources;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.as400.access.AS400SecurityException;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/employee")
public class Employee {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeByID(@QueryParam("id") String empNo) {
        try {
            Connection conn = ConnectionUtils.getConnection();
            System.out.println("Connected to database: " + conn.getMetaData().getDatabaseProductName());

            String sql = "SELECT * FROM sample.EMPLOYEE where empno = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, empNo);
                ResultSet rs = stmt.executeQuery();

                if (!rs.next()) {
                    return Response.status(Response.Status.NOT_FOUND).entity("Employee not found").build();
                }

                System.out.println("result set: " + rs.toString());
                HashMap<String, Object> data = new HashMap<>();
                ResultSetMetaData metaData = rs.getMetaData();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    data.put(metaData.getColumnLabel(i), rs.getObject(i));
                }

                // Convert the HashMap to a JSON string
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(data);

                return Response.ok(json, MediaType.APPLICATION_JSON).build();
            }
        } catch (SQLException | IOException | AS400SecurityException e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to connect to the database")
                    .build();
        }
    }
}
