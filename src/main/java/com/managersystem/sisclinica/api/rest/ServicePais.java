package com.managersystem.sisclinica.api.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.managersystem.sisclinica.api.model.Pais;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Path("/pais")
@Api(value = "Pais")
public class ServicePais {

	static List<Pais> paises = new ArrayList<Pais>();
	
	public ServicePais() {
		if (paises.isEmpty()) {
			paises.add(new Pais(1L, "Brasil", "BR", "Brasileiro"));
			paises.add(new Pais(2L, "Argentina", "BR", "Brasileiro"));
			paises.add(new Pais(3L, "Brasil", "BR", "Brasileiro"));
		}
	}
	
	@GET
	@Path("/pais/listar")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@ApiOperation(value = "Pesquisar todos os países")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Pais.class),
			@ApiResponse(code = 204, message = "Nenhum conteúdo") })
	public Response pesquisar() {
		return paises.size() != 0 ? Response.status(200).entity(paises).build() : Response.status(204).build();
	}
	
	@GET
	@Path("/pais/pesquisar/{id}")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@ApiOperation(value = "Busca país por ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Pais.class),
			@ApiResponse(code = 204, message = "Nenhum conteúdo") })
	public Response pesquisarPorId(@PathParam("id") Long id) {
		Pais pais = paises.stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
		return pais != null ? Response.status(200).entity(pais).build() : Response.status(204).build();
	}
	
	@POST
	@Path("/pais/salvar")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@ApiOperation(value = "Inserir País")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Criado"), 
			@ApiResponse(code = 500, message = "Erro interno no servidor") })
	public Response salvar(Pais pais) {
		pais.setId(Long.valueOf (paises.size() + 1));
		paises.add(pais);
		return Response.status(201).entity(pais).build();
	}
	
	@PUT
	@Path("/pais/atualizar/{id}")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@ApiOperation(value = "Atualizar País ID")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK"), 
			@ApiResponse(code = 204, message = "Nenhum conteúdo") })
	public Response editar(@PathParam("id") Long id, Pais pais) {
		Response response = Response.status(204).entity("País não encontrado").build();
		Pais paisEditar = paises.stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);

		if (paisEditar != null) {
			paises.remove(paisEditar);
			paisEditar.setNome(pais.getNome());
			paises.add(paisEditar);
			response = Response.status(200).entity(pais).build();
		}

		return response;
	}
	
	@DELETE
	@Path("/paise/excluir/{id}")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@ApiOperation(value = "Excluir país por ID")
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Aceito"), 
			@ApiResponse(code = 204, message = "Nenhum conteúdo") })
	public Response excluir(@PathParam("id") Long id) {
		Response response = Response.status(204).entity("Nenhum conteúdo").build();
		Pais pais = paises.stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
		if (pais != null) {
			paises.remove(pais);
			response = Response.status(202).build();
		}
		return response;
	}
	
}
