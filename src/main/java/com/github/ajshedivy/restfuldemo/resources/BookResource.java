package com.github.ajshedivy.restfuldemo.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
    private static Map<String, Book> library = new HashMap<>();

    static {
        // Initialize the library with some books
        initializeLibrary();
    }

    private static void initializeLibrary() {
        // Create some books
        Book book1 = new Book("1", "The Great Gatsby", "F. Scott Fitzgerald", "Classic");
        book1.addReview(new Review("A masterpiece of literary art", "1", 10));

        Book book2 = new Book("2", "1984", "George Orwell", "Dystopian");
        book2.addReview(new Review("Profound and terrifying", "2", 9));

        Book book3 = new Book("3", "To Kill a Mockingbird", "Harper Lee", "Historical Fiction");
        book3.addReview(new Review("Impactful and engaging", "3", 9));

        // Add books to library
        library.put(book1.getId(), book1);
        library.put(book2.getId(), book2);
        library.put(book3.getId(), book3);
    }

    @GET
    @Path("/{id}")
    public Response getBookById(@PathParam("id") String id) {
        Book book = library.get(id);
        if (book == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(book).build();
    }

    @GET
    public Response getAllBooks() {
        Collection<Book> books = library.values();
        if (books.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        GenericEntity<Collection<Book>> entity = new GenericEntity<Collection<Book>>(books) {};
        return Response.ok(entity).build();
    }

    @POST
    public Response addBook(Book book) {
        library.put(book.getId(), book);
        return Response.status(Response.Status.CREATED).entity(book).build();
    }

    @POST
    @Path("/{id}/reviews")
    public Response addReview(@PathParam("id") String id, Review review) {
        Book book = library.get(id);
        if (book == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        review.setBookID(book.getId());
        book.addReview(review);
        return Response.ok().build();
    }

    @GET
    @Path("/bestreview/{id}")
    public Response getTopReviews(@PathParam("id") String id) {
        Book book = library.get(id);
        if (book == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return book.getReviews().stream()
            .max((r1, r2) -> Integer.compare(r1.getRating(), r2.getRating()))
            .map(review -> Response.ok(review).build())
            .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") String id) {
        if (library.remove(id) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok().build();
    }
}
