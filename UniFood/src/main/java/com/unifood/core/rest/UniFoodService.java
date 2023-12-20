package com.unifood.core.rest;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

//	@Produces("text/plain;charset=UTF-8")

@Path("/api")
public class UniFoodService
{
	@GET
	@Path("/text")
	@Produces("text/plain")
	public String testaServicoText()
	{
		return "UniFood back-end versão 0.0.1-SNAPSHOT";
	}

	@GET
	@Path("/html")
	@Produces({MediaType.TEXT_HTML})
	public String testaServicoHtml()
	{
		return "<html><meta charset=\"utf-8\"><p><b>UniFood</b> back-end versão 0.0.1-SNAPSHOT</p></html>";
	}

	@GET
	@Path("/json")
	@Produces("application/json")
	public JsonObject testaServicoJson()
	{
		JsonObject value = Json.createObjectBuilder()
				.add("sistema", "UniFood back-end")
				.add("versao", "0.0.1-SNAPSHOT").build();
		return value;
	}

}
