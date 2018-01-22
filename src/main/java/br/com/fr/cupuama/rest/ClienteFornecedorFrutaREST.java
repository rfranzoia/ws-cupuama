package br.com.fr.cupuama.rest;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import br.com.fr.cupuama.entity.Fruta;
import br.com.fr.cupuama.entity.ClienteFornecedor;
import br.com.fr.cupuama.entity.dto.FrutaDTO;
import br.com.fr.cupuama.entity.dto.ClienteFornecedorDTO;
import br.com.fr.cupuama.entity.dto.ClienteFornecedorFrutaDTO;
import br.com.fr.cupuama.entity.dto.ResponseDTO;
import br.com.fr.cupuama.entity.key.ClienteFornecedorFrutaKey;
import br.com.fr.cupuama.exception.FrutaException;
import br.com.fr.cupuama.exception.ClienteFornecedorException;
import br.com.fr.cupuama.exception.ClienteFornecedorFrutaException;
import br.com.fr.cupuama.service.FrutaService;
import br.com.fr.cupuama.service.ClienteFornecedorFrutaService;
import br.com.fr.cupuama.service.ClienteFornecedorService;
import br.com.fr.cupuama.util.Util;

@Component
@Path("v1/clienteFornecedorFruta")
public class ClienteFornecedorFrutaREST extends BasicREST {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ClienteFornecedorFrutaService service;
	
	@Autowired
	private ClienteFornecedorService clienteFornecedorService;
	
	@Autowired
	private FrutaService frutaService;
	
	/**
	 * @api {post} /clienteFornecedorFruta
	 *    Save
	 *    
	 * @apiDescription
	 *    Inclui um conjunto de registros de ClienteFornecedorsFruta
	 *    
	 * @apiName save
	 * 
	 * @apiGroup ClienteFornecedorFruta
	 *
	 * @apiParam {ClienteFornecedorFrutaDTO} dto DTO com os dados do(s) registro(s)
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia do ClienteFornecedorFrutaDTO inserido (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	public Response save(ClienteFornecedorFrutaDTO dto, @Context UriInfo uriInfo) throws ClienteFornecedorFrutaException {
		try {
			service.save(dto);
			
			ClienteFornecedorDTO clienteFornecedor = clienteFornecedorService.get(dto.getKeyClienteFornecedorId());
			FrutaDTO fruta = frutaService.get(dto.getKeyFrutaId());
			
			ClienteFornecedorFrutaKey key = new ClienteFornecedorFrutaKey();
			key.setClienteFornecedor(Util.buildDTO(clienteFornecedor, ClienteFornecedor.class));
			key.setFruta(Util.buildDTO(fruta, Fruta.class));
			
			URI location = uriInfo.getRequestUriBuilder().path(key.toString()).build();
			
			return Response.created(location).entity(new ResponseDTO(Status.CREATED.getStatusCode(), dto)).build();
			
		} catch (ClienteFornecedorException | FrutaException pfex) {
			return Response.status(Status.NOT_FOUND).entity(new ResponseDTO(Status.NOT_FOUND.getStatusCode(), "ClienteFornecedor e/ou Fruta não encontrado(s)!", pfex)).build();
			
		} catch (ClienteFornecedorFrutaException fex) {
			return Response.status(Status.BAD_REQUEST).entity(new ResponseDTO(Status.BAD_REQUEST.getStatusCode(), fex.getMessage(), fex)).build();
		}        
	}
	
	
	/**
	 * @api {put} /clienteFornecedorFruta/sync/clienteFornecedor/{clienteFornecedor}
	 *    Sincronizar Por ClienteFornecedor
	 *    
	 * @apiDescription
	 *    Sincroniza as associações de um ClienteFornecedor com as Frutas informados
	 *    
	 * @apiName synchronizeByClienteFornecedor
	 * 
	 * @apiGroup ClienteFornecedorFruta
	 *
	 * @apiParam {Integer} clienteFornecedor ID da ClienteFornecedor
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia do ClienteFornecedorDTO atualizado (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@PUT
	@Path("/sync/clienteFornecedor/{clienteFornecedorId}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	public Response synchronizeByClienteFornecedor(@PathParam("clienteFornecedorId") Integer clienteFornecedorId, List<ClienteFornecedorFrutaDTO> clienteFornecedorFrutaList) throws ClienteFornecedorFrutaException {
		try {
			service.syncronizeClienteFornecedorFrutaByClienteFornecedor(clienteFornecedorId, clienteFornecedorFrutaList);
			return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), service.listByClienteFornecedor(clienteFornecedorId))).build();
			
		} catch (NotFoundException nfex) {
			return Response.status(Status.NOT_FOUND).entity(new ResponseDTO(Status.NOT_FOUND.getStatusCode(), "ClienteFornecedor e/ou Fruta não encontrado(s)!", nfex)).build();
			
		} catch (ClienteFornecedorFrutaException fex) {
			return Response.status(Status.BAD_REQUEST).entity(new ResponseDTO(Status.BAD_REQUEST.getStatusCode(), fex.getMessage(), fex)).build();
			
		} catch (ClienteFornecedorException | FrutaException ex) {
			return Response.status(Status.NOT_FOUND).entity(new ResponseDTO(Status.NOT_FOUND.getStatusCode(), "ClienteFornecedor e/ou Fruta não encontrado(s)!", ex)).build();
		}        
	}
	
	/**
	 * @api {put} /clienteFornecedorFruta/sync/fruta/{fruta}
	 *    Sincronizar Por Fruta
	 *    
	 * @apiDescription
	 *    Sincroniza as associações de uma Fruta com os ClienteFornecedor(es) informados
	 *    
	 * @apiName synchronizeByFruta
	 * 
	 * @apiGroup ClienteFornecedorFruta
	 *
	 * @apiParam {Integer} fruta ID do Fruta
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia do FrutaDTO atualizado (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@PUT
	@Path("/sync/fruta/{frutaId}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	public Response synchronizeByFruta(@PathParam("frutaId") Integer frutaId, List<ClienteFornecedorFrutaDTO> clienteFornecedorFrutaList) throws ClienteFornecedorFrutaException {
		try {
			service.syncronizeClienteFornecedorFrutaByFruta(frutaId, clienteFornecedorFrutaList);
			return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), service.listByFruta(frutaId))).build();
			
		} catch (NotFoundException nfex) {
			return Response.status(Status.NOT_FOUND).entity(new ResponseDTO(Status.NOT_FOUND.getStatusCode(), "ClienteFornecedor e/ou Fruta não encontrado(s)!", nfex)).build();
			
		} catch (ClienteFornecedorFrutaException fex) {
			return Response.status(Status.BAD_REQUEST).entity(new ResponseDTO(Status.BAD_REQUEST.getStatusCode(), fex.getMessage(), fex)).build();
			
		} catch (ClienteFornecedorException | FrutaException ex) {
			return Response.status(Status.NOT_FOUND).entity(new ResponseDTO(Status.NOT_FOUND.getStatusCode(), "ClienteFornecedor e/ou Fruta não encontrado(s)!", ex)).build();
		}
	}
	
	/**
	 * @api {get} /clienteFornecedorFruta/clienteFornecedor/fruta?clienteFornecedor=:clienteFornecedor&fruta=:fruta
	 *    List All ClienteFornecedorxFruta 
	 *    
	 * @apiDescription
	 *    Recupera uma lista de registros de ClienteFornecedorFrutaDTO
	 *    
	 * @apiName findOne
	 * 
	 * @apiGroup ClienteFornecedorFruta
	 * 
	 * @apiParam {Integer} clienteFornecedor ID da ClienteFornecedor
	 * @apiParam {Integer} fruta ID do Fruta
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia de ClienteFornecedorFrutaDTO para clienteFornecedor e fruta especificados (use responseDTO.getEntity() para recuperar este objeto) 
	 *
	 */
	@GET
	@Path("/clienteFornecedor/fruta")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response findOne(@QueryParam("clienteFornecedor") Integer clienteFornecedorId, @QueryParam("fruta") Integer frutaId) throws ClienteFornecedorFrutaException {
        try {
        	ClienteFornecedorDTO clienteFornecedor = clienteFornecedorService.get(clienteFornecedorId);
			FrutaDTO fruta = frutaService.get(frutaId);
			
			ClienteFornecedorFrutaKey key = new ClienteFornecedorFrutaKey();
			key.setClienteFornecedor(Util.buildDTO(clienteFornecedor, ClienteFornecedor.class));
			key.setFruta(Util.buildDTO(fruta, Fruta.class));
			
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), service.get(key))).build();
		} catch (ClienteFornecedorException | FrutaException pfex) {
			return Response.status(Status.NOT_FOUND).entity(new ResponseDTO(Status.NOT_FOUND.getStatusCode(), "ClienteFornecedor e/ou Fruta não encontrado(s)!", pfex)).build();
			
        } catch (ClienteFornecedorFrutaException fex) {
        	return Response.status(Status.BAD_REQUEST).entity(new ResponseDTO(Status.BAD_REQUEST.getStatusCode(), fex.getMessage(), fex)).build();
		}
    }
    
	/**
	 * @api {get} /clienteFornecedorFruta/clienteFornecedor/{clienteFornecedor}
	 *    List ClienteFornecedorxFruta by ClienteFornecedor 
	 *    
	 * @apiDescription
	 *    Recupera uma lista de registros de ClienteFornecedorFrutaDTO para o ClienteFornecedor informada
	 *    
	 * @apiName listByClienteFornecedor
	 * 
	 * @apiGroup ClienteFornecedorFruta
	 * 
	 * @apiParam {Integer} clienteFornecedor ID da ClienteFornecedor
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia de ClienteFornecedorDTO com perfis associados (use responseDTO.getEntity() para recuperar este objeto) 
	 *
	 */
	@GET
	@Path("/clienteFornecedor/{clienteFornecedorId}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response listByClienteFornecedor(@PathParam("clienteFornecedorId") Integer clienteFornecedorId) throws ClienteFornecedorFrutaException {
        try {
        	return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), service.listByClienteFornecedor(clienteFornecedorId))).build();
		} catch (NotFoundException nfex) {
			return Response.status(Status.NOT_FOUND).entity(new ResponseDTO(Status.NOT_FOUND.getStatusCode(), "ClienteFornecedor e/ou Fruta não encontrado(s)!", nfex)).build();
			
		} catch (ClienteFornecedorFrutaException fex) {
			return Response.status(Status.BAD_REQUEST).entity(new ResponseDTO(Status.BAD_REQUEST.getStatusCode(), fex.getMessage(), fex)).build();
			
		} catch (ClienteFornecedorException ex) {
			return Response.status(Status.NOT_FOUND).entity(new ResponseDTO(Status.NOT_FOUND.getStatusCode(), "ClienteFornecedor e/ou Fruta não encontrado(s)!", ex)).build();
		}
    }
	
	/**
	 * @api {get} /clienteFornecedorFruta/fruta/{fruta}
	 *    List ClienteFornecedorxFruta by Fruta 
	 *    
	 * @apiDescription
	 *    Recupera uma lista de registros de ClienteFornecedorFrutaDTO para o Fruta informado
	 *    
	 * @apiName listByFruta
	 * 
	 * @apiGroup ClienteFornecedorFruta
	 * 
	 * @apiParam {Long} fruta ID do Fruta
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia de FrutaDTO com lista de clienteFornecedors associados (use responseDTO.getEntity() para recuperar este objeto) 
	 *
	 */
	@GET
	@Path("/fruta/{frutaId}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response listByFruta(@PathParam("frutaId") Integer frutaId) throws ClienteFornecedorFrutaException {
        try {
        	return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), service.listByFruta(frutaId))).build();
        	
		} catch (NotFoundException nfex) {
			return Response.status(Status.NOT_FOUND).entity(new ResponseDTO(Status.NOT_FOUND.getStatusCode(), "ClienteFornecedor e/ou Fruta não encontrado(s)!", nfex)).build();
			
		} catch (ClienteFornecedorFrutaException fex) {
			return Response.status(Status.BAD_REQUEST).entity(new ResponseDTO(Status.BAD_REQUEST.getStatusCode(), fex.getMessage(), fex)).build();
			
		} catch (FrutaException ex) {
			return Response.status(Status.NOT_FOUND).entity(new ResponseDTO(Status.NOT_FOUND.getStatusCode(), "ClienteFornecedor e/ou Fruta não encontrado(s)!", ex)).build();
		}
    }
    
}
	