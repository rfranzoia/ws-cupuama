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
import br.com.fr.cupuama.entity.Produto;
import br.com.fr.cupuama.entity.dto.FrutaDTO;
import br.com.fr.cupuama.entity.dto.ProdutoDTO;
import br.com.fr.cupuama.entity.dto.ProdutoFrutaDTO;
import br.com.fr.cupuama.entity.dto.ResponseDTO;
import br.com.fr.cupuama.entity.key.ProdutoFrutaKey;
import br.com.fr.cupuama.exception.FrutaException;
import br.com.fr.cupuama.exception.ProdutoException;
import br.com.fr.cupuama.exception.ProdutoFrutaException;
import br.com.fr.cupuama.service.FrutaService;
import br.com.fr.cupuama.service.ProdutoFrutaService;
import br.com.fr.cupuama.service.ProdutoService;
import br.com.fr.cupuama.util.Util;

@Component
@Path("v1/produtoFruta")
public class ProdutoFrutaREST extends BasicREST {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ProdutoFrutaService service;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private FrutaService frutaService;
	
	/**
	 * @api {post} /produtoFruta
	 *    Save
	 *    
	 * @apiDescription
	 *    Inclui um conjunto de registros de ProdutosxFruta
	 *    
	 * @apiName save
	 * 
	 * @apiGroup ProdutoFruta
	 *
	 * @apiParam {ProdutoFrutaDTO} dto DTO com os dados do(s) registro(s)
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia do ProdutoFrutaDTO inserido (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	public Response save(ProdutoFrutaDTO dto, @Context UriInfo uriInfo) throws ProdutoFrutaException {
		try {
			service.save(dto);
			
			ProdutoDTO produto = produtoService.get(dto.getKeyProdutoId());
			FrutaDTO fruta = frutaService.get(dto.getKeyFrutaId());
			
			ProdutoFrutaKey key = new ProdutoFrutaKey();
			key.setProduto(Util.buildDTO(produto, Produto.class));
			key.setFruta(Util.buildDTO(fruta, Fruta.class));
			
			URI location = uriInfo.getRequestUriBuilder().path(key.toString()).build();
			
			return Response.created(location).entity(new ResponseDTO(Status.CREATED.getStatusCode(), dto)).build();
			
		} catch (ProdutoException | FrutaException pfex) {
			return Response.status(Status.NOT_FOUND).entity(new ResponseDTO(Status.NOT_FOUND.getStatusCode(), "Produto e/ou Fruta não encontrado(s)!", pfex)).build();
			
		} catch (ProdutoFrutaException fex) {
			return badRequest(fex);
		}        
	}
	
	
	/**
	 * @api {put} /produtoFruta/sync/produto/{produto}
	 *    Sincronizar Por Produto
	 *    
	 * @apiDescription
	 *    Sincroniza as associações de um FILA com os PAINEL informados
	 *    
	 * @apiName synchronizeByProduto
	 * 
	 * @apiGroup ProdutoFruta
	 *
	 * @apiParam {Integer} produto ID da Produto
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia do ProdutoDTO atualizado (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@PUT
	@Path("/sync/produto/{produtoId}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	public Response synchronizeByProduto(@PathParam("produtoId") Integer produtoId, List<ProdutoFrutaDTO> produtoFrutaList) throws ProdutoFrutaException {
		try {
			service.syncronizeProdutoFrutaByProduto(produtoId, produtoFrutaList);
			return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), service.listByProduto(produtoId))).build();
		} catch (NotFoundException nfex) {
			return Response.status(Status.NOT_FOUND).entity(new ResponseDTO(Status.NOT_FOUND.getStatusCode(), "Produto e/ou Fruta não encontrado(s)!", nfex)).build();
			
		} catch (ProdutoException | FrutaException pfex) {
			return Response.status(Status.NOT_FOUND).entity(new ResponseDTO(Status.NOT_FOUND.getStatusCode(), "Produto e/ou Fruta não encontrado(s)!", pfex)).build();
			
		} catch (ProdutoFrutaException fex) {
			return badRequest(fex);
		}        
	}
	
	/**
	 * @api {put} /produtoFruta/sync/fruta/{fruta}
	 *    Sincronizar Por Fruta
	 *    
	 * @apiDescription
	 *    Sincroniza as associações de um PAINEL com os FILAS informadas
	 *    
	 * @apiName synchronizeByFruta
	 * 
	 * @apiGroup ProdutoFruta
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
	public Response synchronizeByFruta(@PathParam("frutaId") Integer frutaId, List<ProdutoFrutaDTO> produtoFrutaList) throws ProdutoFrutaException {
		try {
			service.syncronizeProdutoFrutaByFruta(frutaId, produtoFrutaList);
			return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), service.listByFruta(frutaId))).build();
		} catch (NotFoundException nfex) {
			return Response.status(Status.NOT_FOUND).entity(new ResponseDTO(Status.NOT_FOUND.getStatusCode(), "Produto e/ou Fruta não encontrado(s)!", nfex)).build();
			
		} catch (ProdutoException | FrutaException pfex) {
			return Response.status(Status.NOT_FOUND).entity(new ResponseDTO(Status.NOT_FOUND.getStatusCode(), "Produto e/ou Fruta não encontrado(s)!", pfex)).build();
			
		} catch (ProdutoFrutaException fex) {
			return badRequest(fex);
		}        
	}
	
	/**
	 * @api {get} /produtoFruta/produto/fruta?produto=:produto&fruta=:fruta
	 *    List All ProdutoxFruta 
	 *    
	 * @apiDescription
	 *    Recupera uma lista de registros de ProdutoFrutaDTO
	 *    
	 * @apiName findOne
	 * 
	 * @apiGroup ProdutoFruta
	 * 
	 * @apiParam {Integer} produto ID da Produto
	 * @apiParam {Integer} fruta ID do Fruta
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia de ProdutoFrutaDTO para produto e fruta especificados (use responseDTO.getEntity() para recuperar este objeto) 
	 *
	 */
	@GET
	@Path("/produto/fruta")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response findOne(@QueryParam("produto") Integer produtoId, @QueryParam("fruta") Integer frutaId) throws ProdutoFrutaException {
        try {
        	ProdutoDTO produto = produtoService.get(produtoId);
			FrutaDTO fruta = frutaService.get(frutaId);
			
			ProdutoFrutaKey key = new ProdutoFrutaKey();
			key.setProduto(Util.buildDTO(produto, Produto.class));
			key.setFruta(Util.buildDTO(fruta, Fruta.class));
			
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), service.get(key))).build();
		} catch (ProdutoException | FrutaException pfex) {
			return Response.status(Status.NOT_FOUND).entity(new ResponseDTO(Status.NOT_FOUND.getStatusCode(), "Produto e/ou Fruta não encontrado(s)!", pfex)).build();
			
        } catch (ProdutoFrutaException fex) {
        	return badRequest(fex);
		}
    }
    
	/**
	 * @api {get} /produtoFruta/produto/{produto}
	 *    List ProdutoxFruta by Produto 
	 *    
	 * @apiDescription
	 *    Recupera uma lista de registros de ProdutoFrutaDTO para o Produto informada
	 *    
	 * @apiName listByProduto
	 * 
	 * @apiGroup ProdutoFruta
	 * 
	 * @apiParam {Integer} produto ID da Produto
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia de ProdutoDTO com perfis associados (use responseDTO.getEntity() para recuperar este objeto) 
	 *
	 */
	@GET
	@Path("/produto/{produtoId}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response listByProduto(@PathParam("produtoId") Integer produtoId) throws ProdutoFrutaException {
        try {
        	return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), service.listByProduto(produtoId))).build();
		} catch (NotFoundException nfex) {
			return Response.status(Status.NOT_FOUND).entity(new ResponseDTO(Status.NOT_FOUND.getStatusCode(), "Produto e/ou Fruta não encontrado(s)!", nfex)).build();
			
		} catch (ProdutoFrutaException fex) {
			return badRequest(fex);
		}
    }
	
	/**
	 * @api {get} /produtoFruta/fruta/{fruta}
	 *    List ProdutoxFruta by Fruta 
	 *    
	 * @apiDescription
	 *    Recupera uma lista de registros de ProdutoFrutaDTO para o Fruta informado
	 *    
	 * @apiName listByFruta
	 * 
	 * @apiGroup ProdutoFruta
	 * 
	 * @apiParam {Long} fruta ID do Fruta
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia de FrutaDTO com lista de produtos associados (use responseDTO.getEntity() para recuperar este objeto) 
	 *
	 */
	@GET
	@Path("/fruta/{frutaId}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response listByFruta(@PathParam("frutaId") Integer frutaId) throws Exception {
        try {
        	return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), service.listByFruta(frutaId))).build();
		} catch (NotFoundException nfex) {
			return Response.status(Status.NOT_FOUND).entity(new ResponseDTO(Status.NOT_FOUND.getStatusCode(), "Produto e/ou Fruta não encontrado(s)!", nfex)).build();
			
		} catch (ProdutoFrutaException fex) {
			return badRequest(fex);
		}
    }
    
}
	