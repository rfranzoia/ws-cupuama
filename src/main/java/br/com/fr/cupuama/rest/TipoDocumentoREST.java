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

import br.com.fr.cupuama.entity.dto.TipoDocumentoDTO;
import br.com.fr.cupuama.entity.dto.ResponseDTO;
import br.com.fr.cupuama.exception.TipoDocumentoException;
import br.com.fr.cupuama.service.TipoDocumentoService;

@Component
@Path("v1/tipoDocumento")
public class TipoDocumentoREST extends BasicREST {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private TipoDocumentoService service;
	
	/**
	 * @api {post} /tipoDocumento
	 *    Save
	 *    
	 * @apiDescription
	 *    Inclui um registro em TipoDocumento
	 *    
	 * @apiName save
	 * 
	 * @apiGroup TipoDocumento
	 *
	 * @apiParam {TipoDocumentoDTO} dto DTO com os dados do registro de TipoDocumento
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia do TipoDocumentoDTO inserido (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	public Response save(TipoDocumentoDTO dto, @Context UriInfo uriInfo) throws TipoDocumentoException {
		try {
			dto = service.save(dto);
			
			URI location = uriInfo.getRequestUriBuilder().path(dto.getId().toString()).build();
			
			return Response.created(location).entity(new ResponseDTO(Status.CREATED.getStatusCode(), dto)).build();
			
		} catch (TipoDocumentoException e) {
			return badRequest(e);
		}        
	}
	
	/**
	 * @api {put} /tipoDocumento/{tipoDocumentoId
	 *    Update
	 *    
	 * @apiDescription
	 *    Altera os dados de um registro de TipoDocumento
	 *    
	 * @apiName update
	 * 
	 * @apiGroup TipoDocumento
	 *
	 * @apiParam {Long} tipoDocumentoId ID de TipoDocumento
	 * @apiParam {TipoDocumentoDTO} dto DTO com os dados para alteração do registro de TipoDocumento
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia do TipoDocumentoDTO inserido (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@PUT
	@Path("/{tipoDocumentoId}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response update(@PathParam("tipoDocumentoId") Integer tipoDocumentoId, TipoDocumentoDTO dto) throws TipoDocumentoException {
        try {
        	
        	dto = service.update(tipoDocumentoId, dto);
        	
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), dto)).build();
            
		} catch (NotFoundException nfe) {
			return notFound("Nenhuma tipoDocumento foi encontrada com o ID especificado: " + tipoDocumentoId);
			
		} catch (TipoDocumentoException e) {
			return badRequest(e);
		}
    }
	
	/**
	 * @api {delete} /tipoDocumento/{tipoDocumentoId
	 *    Delete
	 *    
	 * @apiDescription
	 *    Remove um registro de TipoDocumento
	 *    
	 * @apiName delete
	 * 
	 * @apiGroup TipoDocumento
	 *
	 * @apiParam {Long} tipoDocumentoId ID de TipoDocumento
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso da operação de remoção do registro
	 *
	 */
	@DELETE
	@Path("/{tipoDocumentoId}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response delete(@PathParam("tipoDocumentoId") Integer tipoDocumentoId) throws TipoDocumentoException {
        try {
        	
        	service.delete(tipoDocumentoId);

        	return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode())).build();
            
		} catch (NotFoundException nfe) {
			return notFound("Nenhuma tipoDocumento foi encontrada com o ID especificado: " + tipoDocumentoId);
			
		} catch (TipoDocumentoException e) {
			return badRequest(e);
		}
    }
	
	/**
	 * @api {get} /tipoDocumento/{tipoDocumentoId
	 *    Find One
	 *    
	 * @apiDescription
	 *    Recupera um registro de TipoDocumento
	 *    
	 * @apiName findOne
	 * 
	 * @apiGroup TipoDocumento
	 *
	 * @apiParam {Long} tipoDocumentoId ID de TipoDocumento
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia do TipoDocumentoDTO (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@GET
	@Path("/{tipoDocumentoId}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response findOne(@PathParam("tipoDocumentoId") Integer tipoDocumentoId) throws TipoDocumentoException {
        try {
        	TipoDocumentoDTO dto = service.get(tipoDocumentoId);
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), dto)).build();
        } catch (NotFoundException nfe) {
        	return notFound("Nenhuma tipoDocumento foi encontrada com o ID especificado: " + tipoDocumentoId);
        	
        } catch (TipoDocumentoException e) {
        	return badRequest(e);
		}
    }

	/**
	 * @api {get} /tipoDocumento
	 *    List All Order By Id
	 *    
	 * @apiDescription
	 *    Recupera uma lista de TipoDocumento ordenada por NOME
	 *    
	 * @apiName listAll
	 * 
	 * @apiGroup TipoDocumento
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e lista de TipoDocumentoDTO (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response listAll() throws Exception {
        try {
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), service.listAll())).build();
        } catch (TipoDocumentoException e) {
        	return badRequest(e);
		}
    }
	
	/**
	 * @api {get} /tipoDocumento/orderByNome
	 *    List All Order By Nome
	 *    
	 * @apiDescription
	 *    Recupera uma lista de TipoDocumento ordenada por NOME
	 *    
	 * @apiName listAllOrderByNome
	 * 
	 * @apiGroup TipoDocumento
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e lista de TipoDocumentoDTO (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@GET
	@Path("/orderByNome")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response listAllOrderByNome() throws Exception {
        try {
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), service.listAllOrderByNome())).build();
        } catch (TipoDocumentoException e) {
        	return badRequest(e);
		}
    }
	
    
}
	