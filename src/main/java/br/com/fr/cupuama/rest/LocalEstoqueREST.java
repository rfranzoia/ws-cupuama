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

import br.com.fr.cupuama.entity.dto.LocalEstoqueDTO;
import br.com.fr.cupuama.entity.dto.ResponseDTO;
import br.com.fr.cupuama.exception.LocalEstoqueException;
import br.com.fr.cupuama.service.LocalEstoqueService;

@Component
@Path("v1/localEstoque")
public class LocalEstoqueREST extends BasicREST {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private LocalEstoqueService service;
	
	/**
	 * @api {post} /localEstoque
	 *    Save
	 *    
	 * @apiDescription
	 *    Inclui um registro em LocalEstoque
	 *    
	 * @apiName save
	 * 
	 * @apiGroup LocalEstoque
	 *
	 * @apiParam {LocalEstoqueDTO} dto DTO com os dados do registro de LocalEstoque
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia do LocalEstoqueDTO inserido (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	public Response save(LocalEstoqueDTO dto, @Context UriInfo uriInfo) throws LocalEstoqueException {
		try {
			dto = service.save(dto);
			
			URI location = uriInfo.getRequestUriBuilder().path(dto.getId().toString()).build();
			
			return Response.created(location).entity(new ResponseDTO(Status.CREATED.getStatusCode(), dto)).build();
			
		} catch (LocalEstoqueException e) {
			return badRequest(e);
		}        
	}
	
	/**
	 * @api {put} /localEstoque/{localEstoqueId
	 *    Update
	 *    
	 * @apiDescription
	 *    Altera os dados de um registro de LocalEstoque
	 *    
	 * @apiName update
	 * 
	 * @apiGroup LocalEstoque
	 *
	 * @apiParam {Long} localEstoqueId ID de LocalEstoque
	 * @apiParam {LocalEstoqueDTO} dto DTO com os dados para alteração do registro de LocalEstoque
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia do LocalEstoqueDTO inserido (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@PUT
	@Path("/{localEstoqueId}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response update(@PathParam("localEstoqueId") Integer localEstoqueId, LocalEstoqueDTO dto) throws LocalEstoqueException {
        try {
        	
        	service.update(localEstoqueId, dto);
        	
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), dto)).build();
            
		} catch (NotFoundException nfe) {
			return notFound("Nenhuma localEstoque foi encontrada com o ID especificado: " + localEstoqueId);
			
		} catch (LocalEstoqueException e) {
			return badRequest(e);
		}
    }
	
	/**
	 * @api {delete} /localEstoque/{localEstoqueId
	 *    Delete
	 *    
	 * @apiDescription
	 *    Remove um registro de LocalEstoque
	 *    
	 * @apiName delete
	 * 
	 * @apiGroup LocalEstoque
	 *
	 * @apiParam {Long} localEstoqueId ID de LocalEstoque
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso da operação de remoção do registro
	 *
	 */
	@DELETE
	@Path("/{localEstoqueId}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response delete(@PathParam("localEstoqueId") Integer localEstoqueId) throws LocalEstoqueException {
        try {
        	
        	service.delete(localEstoqueId);

        	return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode())).build();
            
		} catch (NotFoundException nfe) {
			return notFound("Nenhuma localEstoque foi encontrada com o ID especificado: " + localEstoqueId);
			
		} catch (LocalEstoqueException e) {
			return badRequest(e);
		}
    }
	
	/**
	 * @api {get} /localEstoque/{localEstoqueId
	 *    Find One
	 *    
	 * @apiDescription
	 *    Recupera um registro de LocalEstoque
	 *    
	 * @apiName findOne
	 * 
	 * @apiGroup LocalEstoque
	 *
	 * @apiParam {Long} localEstoqueId ID de LocalEstoque
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia do LocalEstoqueDTO (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@GET
	@Path("/{localEstoqueId}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response findOne(@PathParam("localEstoqueId") Integer localEstoqueId) throws LocalEstoqueException {
        try {
        	LocalEstoqueDTO dto = service.get(localEstoqueId);
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), dto)).build();
        } catch (NotFoundException nfe) {
        	return notFound("Nenhuma localEstoque foi encontrada com o ID especificado: " + localEstoqueId);
        	
        } catch (LocalEstoqueException e) {
        	return badRequest(e);
		}
    }

	/**
	 * @api {get} /localEstoque
	 *    List All Order By Id
	 *    
	 * @apiDescription
	 *    Recupera uma lista de LocalEstoque ordenada por NOME
	 *    
	 * @apiName listAll
	 * 
	 * @apiGroup LocalEstoque
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e lista de LocalEstoqueDTO (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response listAll() throws Exception {
        try {
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), service.listAll())).build();
        } catch (LocalEstoqueException e) {
        	return badRequest(e);
		}
    }
	
	/**
	 * @api {get} /localEstoque/orderByNome
	 *    List All Order By Nome
	 *    
	 * @apiDescription
	 *    Recupera uma lista de LocalEstoque ordenada por NOME
	 *    
	 * @apiName listAllOrderByNome
	 * 
	 * @apiGroup LocalEstoque
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e lista de LocalEstoqueDTO (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@GET
	@Path("/orderByNome")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response listAllOrderByNome() throws Exception {
        try {
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), service.listAllOrderByNome())).build();
        } catch (LocalEstoqueException e) {
        	return badRequest(e);
		}
    }
	
    
}
	