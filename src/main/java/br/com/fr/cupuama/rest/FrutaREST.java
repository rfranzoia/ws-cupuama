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

import br.com.fr.cupuama.entity.dto.FrutaDTO;
import br.com.fr.cupuama.entity.dto.ResponseDTO;
import br.com.fr.cupuama.exception.FrutaException;
import br.com.fr.cupuama.service.FrutaService;

@Component
@Path("v1/fruta")
public class FrutaREST extends BasicREST {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private FrutaService service;
	
	/**
	 * @api {post} /fruta
	 *    Save
	 *    
	 * @apiDescription
	 *    Inclui um registro em Fruta
	 *    
	 * @apiName save
	 * 
	 * @apiGroup Fruta
	 *
	 * @apiParam {FrutaDTO} dto DTO com os dados do registro de Fruta
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia do FrutaDTO inserido (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	public Response save(FrutaDTO dto, @Context UriInfo uriInfo) throws FrutaException {
		try {
			dto = service.save(dto);
			
			URI location = uriInfo.getRequestUriBuilder().path(dto.getId().toString()).build();
			
			return Response.created(location).entity(new ResponseDTO(Status.CREATED.getStatusCode(), dto)).build();
			
		} catch (FrutaException e) {
			return Response.status(Status.BAD_REQUEST).entity(new ResponseDTO(Status.BAD_REQUEST.getStatusCode(), e.getMessage(), e)).build();
		}        
	}
	
	/**
	 * @api {put} /fruta/{frutaId
	 *    Update
	 *    
	 * @apiDescription
	 *    Altera os dados de um registro de Fruta
	 *    
	 * @apiName update
	 * 
	 * @apiGroup Fruta
	 *
	 * @apiParam {Long} frutaId ID de Fruta
	 * @apiParam {FrutaDTO} dto DTO com os dados para alteração do registro de Fruta
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia do FrutaDTO inserido (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@PUT
	@Path("/{frutaId}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response update(@PathParam("frutaId") Integer frutaId, FrutaDTO dto) throws FrutaException {
        try {
        	
        	dto = service.update(frutaId, dto);
        	
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), dto)).build();
            
		} catch (NotFoundException nfe) {
			return notFound("Nenhuma fruta foi encontrada com o ID especificado: " + frutaId);
			
		} catch (FrutaException e) {
			return Response.status(Status.BAD_REQUEST).entity(new ResponseDTO(Status.BAD_REQUEST.getStatusCode(), e.getMessage(), e)).build();
		}
    }
	
	/**
	 * @api {delete} /fruta/{frutaId
	 *    Delete
	 *    
	 * @apiDescription
	 *    Remove um registro de Fruta
	 *    
	 * @apiName delete
	 * 
	 * @apiGroup Fruta
	 *
	 * @apiParam {Long} frutaId ID de Fruta
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso da operação de remoção do registro
	 *
	 */
	@DELETE
	@Path("/{frutaId}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response delete(@PathParam("frutaId") Integer frutaId) throws FrutaException {
        try {
        	
        	service.delete(frutaId);

        	return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode())).build();
            
		} catch (NotFoundException nfe) {
			return notFound("Nenhuma fruta foi encontrada com o ID especificado: " + frutaId);
			
		} catch (FrutaException e) {
			return Response.status(Status.BAD_REQUEST).entity(new ResponseDTO(Status.BAD_REQUEST.getStatusCode(), e.getMessage(), e)).build();
		}
    }
	
	/**
	 * @api {get} /fruta/{frutaId
	 *    Find One
	 *    
	 * @apiDescription
	 *    Recupera um registro de Fruta
	 *    
	 * @apiName findOne
	 * 
	 * @apiGroup Fruta
	 *
	 * @apiParam {Long} frutaId ID de Fruta
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia do FrutaDTO (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@GET
	@Path("/{frutaId}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response findOne(@PathParam("frutaId") Integer frutaId) throws FrutaException {
        try {
        	FrutaDTO dto = service.get(frutaId);
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), dto)).build();
        } catch (NotFoundException nfe) {
        	return notFound("Nenhuma fruta foi encontrada com o ID especificado: " + frutaId);
        	
        } catch (FrutaException e) {
			return Response.status(Status.BAD_REQUEST).entity(new ResponseDTO(Status.BAD_REQUEST.getStatusCode(), "Ocorreu um problema com a busca!", e)).build();
		}
    }

	/**
	 * @api {get} /fruta
	 *    List All Order By Id
	 *    
	 * @apiDescription
	 *    Recupera uma lista de Fruta ordenada por NOME
	 *    
	 * @apiName listAll
	 * 
	 * @apiGroup Fruta
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e lista de FrutaDTO (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response listAll() throws Exception {
        try {
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), service.listAll())).build();
        } catch (FrutaException e) {
			return Response.status(Status.BAD_REQUEST).entity(new ResponseDTO(Status.BAD_REQUEST.getStatusCode(), "Ocorreu um problema com a busca!", e)).build();
		}
    }
	
	/**
	 * @api {get} /fruta/orderByNome
	 *    List All Order By Nome
	 *    
	 * @apiDescription
	 *    Recupera uma lista de Fruta ordenada por NOME
	 *    
	 * @apiName listAllOrderByNome
	 * 
	 * @apiGroup Fruta
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e lista de FrutaDTO (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@GET
	@Path("/orderByNome")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response listAllOrderByNome() throws Exception {
        try {
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), service.listAllOrderByNome())).build();
        } catch (FrutaException e) {
			return Response.status(Status.BAD_REQUEST).entity(new ResponseDTO(Status.BAD_REQUEST.getStatusCode(), "Ocorreu um problema com a busca!", e)).build();
		}
    }
	
	/**
	 * @api {get} /fruta/safra/{safra}
	 *    List By Safra
	 *    
	 * @apiDescription
	 *    Recupera uma lista de Fruta de uma SAFRA especificada ordenada por NOME
	 *    
	 * @apiName listBySafra
	 * 
	 * @apiGroup Fruta
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e lista de FrutaDTO (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@GET
	@Path("/safra/{safra}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response listBySafra(@PathParam("safra") String safra) throws Exception {
        try {
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), service.listBySafraOrderByNome(safra))).build();
        } catch (FrutaException e) {
			return Response.status(Status.BAD_REQUEST).entity(new ResponseDTO(Status.BAD_REQUEST.getStatusCode(), "Ocorreu um problema com a busca!", e)).build();
		}
    }
	
	/**
	 * @api {get} /fruta/safras
	 *    List All Safras
	 *    
	 * @apiDescription
	 *    Recupera uma lista de Safras a partir das frutas cadastradas
	 *    
	 * @apiName listAllSafras
	 * 
	 * @apiGroup Fruta
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e lista de FrutaDTO (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@GET
	@Path("/safras")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response listAllSafras() throws Exception {
        try {
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), service.listSafras())).build();
            
        } catch (NotFoundException nfe) {
        	return notFound("Nenhuma SAFRA foi cadastrada ainda!");
        	
        } catch (FrutaException e) {
			return Response.status(Status.BAD_REQUEST).entity(new ResponseDTO(Status.BAD_REQUEST.getStatusCode(), "Ocorreu um problema com a busca!", e)).build();
		}
    }
    
}
	