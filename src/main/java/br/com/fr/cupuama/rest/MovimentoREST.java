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

import br.com.fr.cupuama.entity.dto.MovimentoDTO;
import br.com.fr.cupuama.entity.dto.ResponseDTO;
import br.com.fr.cupuama.exception.MovimentoException;
import br.com.fr.cupuama.service.MovimentoService;

@Component
@Path("v1/movimento")
public class MovimentoREST extends BasicREST {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private MovimentoService service;
	
	/**
	 * @api {post} /movimento
	 *    Save
	 *    
	 * @apiDescription
	 *    Inclui um registro em Movimento
	 *    
	 * @apiName save
	 * 
	 * @apiGroup Movimento
	 *
	 * @apiParam {MovimentoDTO} dto DTO com os dados do registro de Movimento
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia do MovimentoDTO inserido (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	public Response save(MovimentoDTO dto, @Context UriInfo uriInfo) throws MovimentoException {
		try {
			dto = service.addMovimentoAndItensAndUpdateEstoque(dto);
			
			URI location = uriInfo.getRequestUriBuilder().path(dto.getId().toString()).build();
			
			return Response.created(location).entity(new ResponseDTO(Status.CREATED.getStatusCode(), service.getWithItensMovimento(dto.getId()))).build();
			
		} catch (NotFoundException nfe) {
			return notFound(nfe.getMessage());
			
		} catch (MovimentoException e) {
			return badRequest(e);
		}        
	}
	
	/**
	 * @api {put} /movimento/{movimentoId}
	 *    Update
	 *    
	 * @apiDescription
	 *    Altera os dados de um registro de Movimento
	 *    
	 * @apiName update
	 * 
	 * @apiGroup Movimento
	 *
	 * @apiParam {Long} movimentoId ID de Movimento
	 * @apiParam {MovimentoDTO} dto DTO com os dados para alteração do registro de Movimento
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia do MovimentoDTO inserido (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@PUT
	@Path("/{movimentoId}")
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response update(@PathParam("movimentoId") Integer movimentoId, MovimentoDTO dto) throws MovimentoException {
        try {
        	
        	dto = service.updateMovimentoItensAndEstoque(movimentoId, dto);
        	
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), service.getWithItensMovimento(dto.getId()))).build();
            
		} catch (NotFoundException nfe) {
			return notFound(nfe.getMessage());
			
		} catch (MovimentoException e) {
			return badRequest(e);
		}
    }
	
	/**
	 * @api {delete} /movimento/{movimentoId}
	 *    Delete
	 *    
	 * @apiDescription
	 *    Remove um registro de Movimento
	 *    
	 * @apiName delete
	 * 
	 * @apiGroup Movimento
	 *
	 * @apiParam {Long} movimentoId ID de Movimento
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso da operação de remoção do registro
	 *
	 */
	@DELETE
	@Path("/{movimentoId}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response delete(@PathParam("movimentoId") Integer movimentoId) throws MovimentoException {
        try {
        	
        	service.deleteMovimentoAndUpdateEstoque(movimentoId);

        	return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode())).build();
            
		} catch (NotFoundException nfe) {
			return notFound(nfe.getMessage());
			
		} catch (MovimentoException e) {
			return badRequest(e);
		}
    }
	
	/**
	 * @api {get} /movimento/{movimentoId}
	 *    Find One
	 *    
	 * @apiDescription
	 *    Recupera um registro de Movimento
	 *    
	 * @apiName findOne
	 * 
	 * @apiGroup Movimento
	 *
	 * @apiParam {Long} movimentoId ID de Movimento
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia do MovimentoDTO (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@GET
	@Path("/{movimentoId}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response findOne(@PathParam("movimentoId") Integer movimentoId) throws MovimentoException {
        try {
        	MovimentoDTO dto = service.getWithItensMovimento(movimentoId);
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), dto)).build();
        } catch (NotFoundException nfe) {
        	return notFound(nfe.getMessage());
        	
        } catch (MovimentoException e) {
        	return badRequest(e);
		}
    }

	/**
	 * @api {get} /movimento
	 *    List All Order By DtMovimento
	 *    
	 * @apiDescription
	 *    Recupera uma lista de Movimento ordenada por Data
	 *    
	 * @apiName listAllOrderByDtMovimento
	 * 
	 * @apiGroup Movimento
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e lista de MovimentoDTO (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response listAllOrderByDtMovimento() throws Exception {
        try {
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), service.listAllOrderByDtMovimento())).build();
        } catch (MovimentoException e) {
        	return badRequest(e);
		}
    }
	
	/**
	 * @api {get} /movimento/includeItems
	 *    List All Order By Data Movimento With Items
	 *    
	 * @apiDescription
	 *    Recupera uma lista de Movimento ordenada por Data incluindo todos os itens deste
	 *    
	 * @apiName listAllWithItensMovimento
	 * 
	 * @apiGroup Movimento
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e lista de MovimentoDTO (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@GET
	@Path("/includeItens")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response listAllWithItensMovimento() throws Exception {
        try {
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), service.listAllWithItensMovimento())).build();
        } catch (MovimentoException e) {
        	return badRequest(e);
		}
    }
	
}
	