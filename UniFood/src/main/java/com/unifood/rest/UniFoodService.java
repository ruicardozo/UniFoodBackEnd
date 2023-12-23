package com.unifood.rest;

import java.util.List;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.unifood.ed.TabelaED;
import com.unifood.rn.TabelaRN;

@Path("/api")
public class UniFoodService
{
	@Inject
	private TabelaRN tabelaRN;

	@GET
	@Path("/text")
	@Produces("text/plain")
	public Response testaServicoText()
	{
		return addCorsHeaders(Response.ok("UniFood back-end versão 0.0.1-SNAPSHOT")).build();
	}

	@GET
	@Path("/html")
	@Produces(
	{ MediaType.TEXT_HTML })
	public Response testaServicoHtml()
	{
		return addCorsHeaders(Response.ok("<html><meta charset=\"utf-8\"><p><b>UniFood</b> back-end versão 0.0.1-SNAPSHOT</p></html>")).build();
	}

	@GET
	@Path("/json")
	@Produces("application/json")
	public Response testaServicoJson()
	{
		JsonObject value = Json.createObjectBuilder()
				.add("sistema", "UniFood back-end")
				.add("versao", "0.0.1-SNAPSHOT")
				.build();
		return addCorsHeaders(Response.ok(value)).build();
	}

	@GET
	@Path("/listatabelas")
	@Produces("application/json")
	public Response listaTabelas()
	{
		List<TabelaED> lista = tabelaRN.lista();
		return addCorsHeaders(Response.ok(lista)).build();
	}

	// Helper method to add CORS headers
	private Response.ResponseBuilder addCorsHeaders(Response.ResponseBuilder responseBuilder)
	{
		return responseBuilder
				.header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
				.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
				.header("Access-Control-Allow-Credentials", "true");
	}
}