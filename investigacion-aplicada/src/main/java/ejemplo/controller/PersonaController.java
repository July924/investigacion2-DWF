package ejemplo.controller;


import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/personas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonaResource {

    // GET - Todas las personas
    @GET
    public List<Persona> listar() {
        return Persona.listAll();
    }

    // GET - Persona por ID
    @GET
    @Path("/{id}")
    public Persona obtener(@PathParam("id") Long id) {
        return Persona.findById(id);
    }

    // POST - Crear persona
    @POST
    @Transactional
    public Response crear(Persona persona) {
        persona.persist();
        return Response.status(Response.Status.CREATED).entity(persona).build();
    }

    // PUT - Actualizar persona
    @PUT
    @Path("/{id}")
    @Transactional
    public Persona actualizar(@PathParam("id") Long id, Persona nuevaPersona) {
        Persona persona = Persona.findById(id);
        if (persona == null) {
            throw new NotFoundException();
        }
        persona.nombre = nuevaPersona.nombre;
        persona.apellido = nuevaPersona.apellido;
        return persona;
    }

    // DELETE - Eliminar persona
    @DELETE
    @Path("/{id}")
    @Transactional
    public Response eliminar(@PathParam("id") Long id) {
        boolean eliminado = Persona.deleteById(id);
        if (!eliminado) {
            throw new NotFoundException();
        }
        return Response.noContent().build();
    }
}
