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

import br.com.fr.cupuama.entity.dto.TipoMovimentacaoDTO;
import br.com.fr.cupuama.entity.dto.ResponseDTO;
import br.com.fr.cupuama.exception.TipoMovimentacaoException;
import br.com.fr.cupuama.service.TipoMovimentacaoService;

@Component
@Path("v1/tipoMovimentacao")
public class TipoMovimentacaoREST extends BasicREST {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private TipoMovimentacaoService service;
	
	/**
	 * @api {post} /tipoMovimentacao
	 *    Save
	 *    
	 * @apiDescription
	 *    Inclui um registro em TipoMovimentacao
	 *    
	 * @apiName save
	 * 
	 * @apiGroup TipoMovimentacao
	 *
	 * @apiParam {TipoMovimentacaoDTO} dto DTO com os dados do registro de TipoMovimentacao
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia do TipoMovimentacaoDTO inserido (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	public Response save(TipoMovimentacaoDTO dto, @Context UriInfo uriInfo) throws TipoMovimentacaoException {
		try {
			dto = service.save(dto);
			
			URI location = uriInfo.getRequestUriBuilder().path(dto.getId().toString()).build();
			
			return Response.created(location).entity(new ResponseDTO(Status.CREATED.getStatusCode(), dto)).build();
			
		} catch (TipoMovimentacaoException e) {
			return badRequest(e);
		}        
	}
	
	/**
	 * @api {put} /tipoMovimentacao/{tipoMovimentacaoId
	 *    Update
	 *    
	 * @apiDescription
	 *    Altera os dados de um registro de TipoMovimentacao
	 *    
	 * @apiName update
	 * 
	 * @apiGroup TipoMovimentacao
	 *
	 * @apiParam {Long} tipoMovimentacaoId ID de TipoMovimentacao
	 * @apiParam {TipoMovimentacaoDTO} dto DTO com os dados para alteração do registro de TipoMovimentacao
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia do TipoMovimentacaoDTO inserido (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@PUT
	@Path("/{tipoMovimentacaoId}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response update(@PathParam("tipoMovimentacaoId") Integer tipoMovimentacaoId, TipoMovimentacaoDTO dto) throws TipoMovimentacaoException {
        try {
        	
        	dto = service.update(tipoMovimentacaoId, dto);
        	
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), dto)).build();
            
		} catch (NotFoundException nfe) {
			return notFound("Nenhuma tipoMovimentacao foi encontrada com o ID especificado: " + tipoMovimentacaoId);
			
		} catch (TipoMovimentacaoException e) {
			return badRequest(e);
		}
    }
	
	/**
	 * @api {delete} /tipoMovimentacao/{tipoMovimentacaoId
	 *    Delete
	 *    
	 * @apiDescription
	 *    Remove um registro de TipoMovimentacao
	 *    
	 * @apiName delete
	 * 
	 * @apiGroup TipoMovimentacao
	 *
	 * @apiParam {Long} tipoMovimentacaoId ID de TipoMovimentacao
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso da operação de remoção do registro
	 *
	 */
	@DELETE
	@Path("/{tipoMovimentacaoId}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response delete(@PathParam("tipoMovimentacaoId") Integer tipoMovimentacaoId) throws TipoMovimentacaoException {
        try {
        	
        	service.delete(tipoMovimentacaoId);

        	return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode())).build();
            
		} catch (NotFoundException nfe) {
			return notFound("Nenhuma tipoMovimentacao foi encontrada com o ID especificado: " + tipoMovimentacaoId);
			
		} catch (TipoMovimentacaoException e) {
			return badRequest(e);
		}
    }
	
	/**
	 * @api {get} /tipoMovimentacao/{tipoMovimentacaoId
	 *    Find One
	 *    
	 * @apiDescription
	 *    Recupera um registro de TipoMovimentacao
	 *    
	 * @apiName findOne
	 * 
	 * @apiGroup TipoMovimentacao
	 *
	 * @apiParam {Long} tipoMovimentacaoId ID de TipoMovimentacao
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia do TipoMovimentacaoDTO (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@GET
	@Path("/{tipoMovimentacaoId}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response findOne(@PathParam("tipoMovimentacaoId") Integer tipoMovimentacaoId) throws TipoMovimentacaoException {
        try {
        	TipoMovimentacaoDTO dto = service.get(tipoMovimentacaoId);
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), dto)).build();
        } catch (NotFoundException nfe) {
        	return notFound("Nenhuma tipoMovimentacao foi encontrada com o ID especificado: " + tipoMovimentacaoId);
        	
        } catch (TipoMovimentacaoException e) {
        	return badRequest(e);
		}
    }

	/**
	 * @api {get} /tipoMovimentacao
	 *    List All Order By Id
	 *    
	 * @apiDescription
	 *    Recupera uma lista de TipoMovimentacao ordenada por NOME
	 *    
	 * @apiName listAll
	 * 
	 * @apiGroup TipoMovimentacao
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e lista de TipoMovimentacaoDTO (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response listAll() throws Exception {
        try {
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), service.listAll())).build();
        } catch (TipoMovimentacaoException e) {
        	return badRequest(e);
		}
    }
	
	/**
	 * @api {get} /tipoMovimentacao/orderByNome
	 *    List All Order By Nome
	 *    
	 * @apiDescription
	 *    Recupera uma lista de TipoMovimentacao ordenada por NOME
	 *    
	 * @apiName listAllOrderByNome
	 * 
	 * @apiGroup TipoMovimentacao
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e lista de TipoMovimentacaoDTO (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@GET
	@Path("/orderByNome")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response listAllOrderByNome() throws Exception {
        try {
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), service.listAllOrderByNome())).build();
        } catch (TipoMovimentacaoException e) {
        	return badRequest(e);
		}
    }
	
    
}
	