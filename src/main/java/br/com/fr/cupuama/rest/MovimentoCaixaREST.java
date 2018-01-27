package br.com.fr.cupuama.rest;

import java.net.URI;
import java.util.Date;

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

import br.com.fr.cupuama.entity.dto.MovimentoCaixaDTO;
import br.com.fr.cupuama.entity.dto.ResponseDTO;
import br.com.fr.cupuama.exception.CaixaException;
import br.com.fr.cupuama.exception.MovimentoCaixaException;
import br.com.fr.cupuama.service.MovimentoCaixaService;
import br.com.fr.cupuama.util.Util;

@Component
@Path("v1/movimentoCaixa")
public class MovimentoCaixaREST extends BasicREST {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private MovimentoCaixaService service;
	
	/**
	 * @api {post} /movimentoCaixa
	 *    Save
	 *    
	 * @apiDescription
	 *    Inclui um registro em MovimentoCaixa
	 *    
	 * @apiName save
	 * 
	 * @apiGroup MovimentoCaixa
	 *
	 * @apiParam {MovimentoCaixaDTO} dto DTO com os dados do registro de MovimentoCaixa
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia do MovimentoCaixaDTO inserido (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	public Response save(MovimentoCaixaDTO dto, @Context UriInfo uriInfo) throws MovimentoCaixaException {
		try {
			dto = service.addMovimentoCaixaAndUpdateCaixa(dto);
			
			URI location = uriInfo.getRequestUriBuilder().path(dto.getId().toString()).build();
			
			return Response.created(location).entity(new ResponseDTO(Status.CREATED.getStatusCode(), service.get(dto.getId()))).build();
			
		} catch (NotFoundException nfe) {
			return notFound(nfe.getMessage());
			
		} catch (CaixaException | MovimentoCaixaException e) {
			return badRequest(e);
		}        
	}
	
	/**
	 * @api {put} /movimentoCaixa/{movimentoCaixaId}
	 *    Update
	 *    
	 * @apiDescription
	 *    Altera os dados de um registro de MovimentoCaixa
	 *    
	 * @apiName update
	 * 
	 * @apiGroup MovimentoCaixa
	 *
	 * @apiParam {Long} movimentoCaixaId ID de MovimentoCaixa
	 * @apiParam {MovimentoCaixaDTO} dto DTO com os dados para alteração do registro de MovimentoCaixa
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia do MovimentoCaixaDTO inserido (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@PUT
	@Path("/{movimentoCaixaId}")
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response update(@PathParam("movimentoCaixaId") Integer movimentoCaixaId, MovimentoCaixaDTO dto) throws MovimentoCaixaException {
        try {
        	
        	dto = service.updateMovimentoCaixaAndUpdateCaixa(movimentoCaixaId, dto);
        	
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), service.get(dto.getId()))).build();
            
		} catch (NotFoundException nfe) {
			return notFound(nfe.getMessage());
			
		} catch (CaixaException | MovimentoCaixaException e) {
			return badRequest(e);
		}
    }
	
	/**
	 * @api {delete} /movimentoCaixa/{movimentoCaixaId}
	 *    Delete
	 *    
	 * @apiDescription
	 *    Remove um registro de MovimentoCaixa
	 *    
	 * @apiName delete
	 * 
	 * @apiGroup MovimentoCaixa
	 *
	 * @apiParam {Long} movimentoCaixaId ID de MovimentoCaixa
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso da operação de remoção do registro
	 *
	 */
	@DELETE
	@Path("/{movimentoCaixaId}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response delete(@PathParam("movimentoCaixaId") Integer movimentoCaixaId) throws MovimentoCaixaException {
        try {
        	
        	service.removeMovimentoCaixaAndUpdateCaixa(movimentoCaixaId);

        	return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode())).build();
            
		} catch (NotFoundException nfe) {
			return notFound(nfe.getMessage());
			
		} catch (CaixaException | MovimentoCaixaException e) {
			return badRequest(e);
		}
    }
	
	/**
	 * @api {get} /movimentoCaixa/{movimentoCaixaId}
	 *    Find One
	 *    
	 * @apiDescription
	 *    Recupera um registro de MovimentoCaixa
	 *    
	 * @apiName findOne
	 * 
	 * @apiGroup MovimentoCaixa
	 *
	 * @apiParam {Long} movimentoCaixaId ID de MovimentoCaixa
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia do MovimentoCaixaDTO (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@GET
	@Path("/{movimentoCaixaId}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response findOne(@PathParam("movimentoCaixaId") Integer movimentoCaixaId) throws MovimentoCaixaException {
        try {
        	MovimentoCaixaDTO dto = service.get(movimentoCaixaId);
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), dto)).build();
        } catch (NotFoundException nfe) {
        	return notFound(nfe.getMessage());
        	
        } catch (MovimentoCaixaException e) {
        	return badRequest(e);
		}
    }

	
	/**
	 * @api {get} /movimentoCaixa
	 *    List All Order By DtMovimentoCaixa
	 *    
	 * @apiDescription
	 *    Recupera uma lista de MovimentoCaixa ordenada por Data
	 *    
	 * @apiName listAllOrderByDtMovimentoCaixa
	 * 
	 * @apiGroup MovimentoCaixa
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e lista de MovimentoCaixaDTO (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response listAll() throws Exception {
        try {
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), service.listAll())).build();
        } catch (Exception e) {
        	return badRequest(e);
		}
    }
	
	/**
	 * @api {get} /movimentoCaixa/periodo/{inicio}/{fim}
	 *    List All Order By DtMovimentoCaixa
	 *    
	 * @apiDescription
	 *    Recupera uma lista de MovimentoCaixa ordenada por Data
	 *    
	 * @apiName listAllOrderByDtMovimentoCaixa
	 * 
	 * @apiGroup MovimentoCaixa
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e lista de MovimentoCaixaDTO (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@GET
	@Path("/periodo/{inicio}/{fim}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response listAllOrderByDtMovimentoCaixa(@PathParam("inicio") String inicio, @PathParam("fim") String fim) throws Exception {
        try {
        	if (inicio == null) {
        		inicio = Util.DATE_FORMAT_ANOMES.format(new Date());
        	}
        	
        	if (fim == null) {
        		fim = Util.DATE_FORMAT_ANOMES.format(new Date());
        	}
        	
        	Date dtInicio = Util.DATE_FORMAT_TIMESTAMP_COMPACT.parse(inicio + "01000000");
        	
        	if (fim.endsWith("02")) {
        		fim += "28235959";
        		
        		
        	} else if (fim.endsWith("01") || fim.endsWith("03") || fim.endsWith("05") || 
        				fim.endsWith("07") || fim.endsWith("08") || fim.endsWith("10") || fim.endsWith("12")) {
        		fim += "31235959";
        		
        	} else {
        		fim += "30235959";
        	}
        	
        	Date dtFim = Util.DATE_FORMAT_TIMESTAMP_COMPACT.parse(fim);

        	if (dtInicio.after(dtFim)) {
        		return badRequest("Data inicial não pode ser posterior à final!");
        	}
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), service.listByPeriodo(dtInicio, dtFim))).build();
        } catch (Exception e) {
        	return badRequest(e);
		}
    }
	
	
}
	