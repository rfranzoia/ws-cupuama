package br.com.fr.cupuama.rest;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import br.com.fr.cupuama.entity.dto.ProdutoDTO;
import br.com.fr.cupuama.entity.dto.ResponseDTO;
import br.com.fr.cupuama.exception.ProdutoException;
import br.com.fr.cupuama.service.ProdutoService;

@Component
@Path("v1/produto")
public class ProdutoREST extends BasicREST {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ProdutoService service;
	
	/**
	 * @api {post} /produto
	 *    Save
	 *    
	 * @apiDescription
	 *    Inclui um registro em Produto
	 *    
	 * @apiName save
	 * 
	 * @apiGroup Produto
	 *
	 * @apiParam {ProdutoDTO} dto DTO com os dados do registro de Produto
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia do ProdutoDTO inserido (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	public Response save(ProdutoDTO dto, @Context UriInfo uriInfo) throws ProdutoException {
		try {
			dto = service.save(dto);
			
			URI location = uriInfo.getRequestUriBuilder().path(dto.getId().toString()).build();
			
			return Response.created(location).entity(new ResponseDTO(Status.CREATED.getStatusCode(), dto)).build();
			
		} catch (ProdutoException e) {
			return badRequest(e);
		}        
	}
	
	/**
	 * @api {put} /produto/{produtoId
	 *    Update
	 *    
	 * @apiDescription
	 *    Altera os dados de um registro de Produto
	 *    
	 * @apiName update
	 * 
	 * @apiGroup Produto
	 *
	 * @apiParam {Long} produtoId ID de Produto
	 * @apiParam {ProdutoDTO} dto DTO com os dados para alteração do registro de Produto
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia do ProdutoDTO inserido (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@PUT
	@Path("/{produtoId}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response update(@PathParam("produtoId") Integer produtoId, ProdutoDTO dto) throws ProdutoException {
        try {
        	
        	dto = service.update(produtoId, dto);
        	
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), dto)).build();
            
		} catch (NotFoundException nfe) {
			return notFound("Nenhuma produto foi encontrada com o ID especificado: " + produtoId);
			
		} catch (ProdutoException e) {
			return badRequest(e);
		}
    }
	
	/**
	 * @api {delete} /produto/{produtoId
	 *    Delete
	 *    
	 * @apiDescription
	 *    Remove um registro de Produto
	 *    
	 * @apiName delete
	 * 
	 * @apiGroup Produto
	 *
	 * @apiParam {Long} produtoId ID de Produto
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso da operação de remoção do registro
	 *
	 */
	@DELETE
	@Path("/{produtoId}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response delete(@PathParam("produtoId") Integer produtoId) throws ProdutoException {
        try {
        	
        	service.delete(produtoId);

        	return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode())).build();
            
		} catch (NotFoundException nfe) {
			return notFound("Nenhuma produto foi encontrada com o ID especificado: " + produtoId);
			
		} catch (ProdutoException e) {
			return badRequest(e);
		}
    }
	
	/**
	 * @api {get} /produto/{produtoId
	 *    Find One
	 *    
	 * @apiDescription
	 *    Recupera um registro de Produto
	 *    
	 * @apiName findOne
	 * 
	 * @apiGroup Produto
	 *
	 * @apiParam {Long} produtoId ID de Produto
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia do ProdutoDTO (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@GET
	@Path("/{produtoId}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response findOne(@PathParam("produtoId") Integer produtoId) throws ProdutoException {
        try {
        	ProdutoDTO dto = service.get(produtoId);
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), dto)).build();
        } catch (NotFoundException nfe) {
        	return notFound("Nenhuma produto foi encontrada com o ID especificado: " + produtoId);
        	
        } catch (ProdutoException e) {
        	return badRequest(e);
		}
    }

	/**
	 * @api {get} /produto
	 *    List All Order By Id
	 *    
	 * @apiDescription
	 *    Recupera uma lista de Produto ordenada por NOME
	 *    
	 * @apiName listAll
	 * 
	 * @apiGroup Produto
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e lista de ProdutoDTO (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response listAll() throws Exception {
        try {
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), service.listAll())).build();
        } catch (ProdutoException e) {
        	return badRequest(e);
		}
    }
	
	/**
	 * @api {get} /produto/orderByNome
	 *    List All Order By Nome
	 *    
	 * @apiDescription
	 *    Recupera uma lista de Produto ordenada por NOME
	 *    
	 * @apiName listAllOrderByNome
	 * 
	 * @apiGroup Produto
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e lista de ProdutoDTO (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@GET
	@Path("/orderByNome")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response listAllOrderByNome() throws Exception {
        try {
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), service.listAllOrderByNome())).build();
        } catch (ProdutoException e) {
        	return badRequest(e);
		}
    }
	
    
}
	