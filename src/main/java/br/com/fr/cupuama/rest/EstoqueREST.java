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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import br.com.fr.cupuama.entity.dto.EstoqueDTO;
import br.com.fr.cupuama.entity.dto.ResponseDTO;
import br.com.fr.cupuama.entity.key.EstoqueKey;
import br.com.fr.cupuama.exception.EstoqueException;
import br.com.fr.cupuama.service.EstoqueService;

@Component
@Path("v1/estoque")
public class EstoqueREST extends BasicREST {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private EstoqueService service;
	
	/**
	 * @api {post} /estoque
	 *    Save
	 *    
	 * @apiDescription
	 *    Inclui um registro em Estoque
	 *    
	 * @apiName save
	 * 
	 * @apiGroup Estoque
	 *
	 * @apiParam {EstoqueDTO} dto DTO com os dados do registro de Estoque
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia do EstoqueDTO inserido (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	public Response save(EstoqueDTO dto, @Context UriInfo uriInfo) throws EstoqueException {
		try {
			dto = service.save(dto);
			
			URI location = uriInfo
					.getRequestUriBuilder()
					.path(service.getEstoqueKey(dto.getKeyProdutoId(), dto.getKeyFrutaId(), dto.getKeyLocalEstoqueId()).toString())
					.build();
			
			return Response.created(location).entity(new ResponseDTO(Status.CREATED.getStatusCode(), dto)).build();
			
		} catch (EstoqueException e) {
			return badRequest(e);
		}        
	}
	
	/**
	 * @api {put} /estoque
	 *    Update
	 *    
	 * @apiDescription
	 *    Altera os dados de um registro de Estoque
	 *    
	 * @apiName update
	 * 
	 * @apiGroup Estoque
	 *
	 * @apiParam {EstoqueDTO} dto DTO com os dados para alteração do registro de Estoque
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia do EstoqueDTO inserido (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response update(@PathParam("estoqueId") Integer estoqueId, EstoqueDTO dto) throws EstoqueException {
        try {
        	
        	EstoqueKey key = service.getEstoqueKey(dto.getKeyProdutoId(), dto.getKeyFrutaId(), dto.getKeyLocalEstoqueId());
        	key.setAnoMes(dto.getKeyAnoMes());
        	
        	dto = service.update(key, dto);
        	
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), dto)).build();
            
		} catch (NotFoundException nfe) {
			return notFound("Nenhuma estoque foi encontrada com o ID especificado: " + estoqueId);
			
		} catch (EstoqueException e) {
			return badRequest(e);
		}
    }
	
	/**
	 * @api {delete} /estoque/?anoMes={anoMes}&produtoId={produtoId}&frutaId={frutaId}&localEstoqueId={localEstoqueId}
	 *    Delete
	 *    
	 * @apiDescription
	 *    Remove um registro de Estoque
	 *    
	 * @apiName delete
	 * 
	 * @apiGroup Estoque
	 *
	 * @apiParam {String} anoMes Ano/Mes do registro
	 * @apiParam {Long} produtoId ID do produto
	 * @apiParam {Long} frutaId ID da fruta
	 * @apiParam {Long} localEstoqueId ID do local de estoque
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso da operação de remoção do registro
	 *
	 */
	@DELETE
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response delete(@QueryParam("anoMes") String anoMes, @QueryParam("produtoId") Integer produtoId, 
			@QueryParam("frutaId") Integer frutaId, @QueryParam("localEstoqueId") Integer localEstoqueId) throws EstoqueException {
		
        try {
        	
        	EstoqueKey key = service.getEstoqueKey(produtoId, frutaId, localEstoqueId);
        	key.setAnoMes(anoMes);
        	
        	service.delete(key);

        	return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode())).build();
            
		} catch (NotFoundException nfe) {
			return notFound("Nenhum registro de estoque foi encontrado!");
			
		} catch (EstoqueException e) {
			return badRequest(e);
		}
    }
	
	/**
	 * @api {get} /estoque/{anoMes}?produtoId={produtoId}&frutaId={frutaId}&localEstoqueId={localEstoqueId}
	 *    Find One
	 *    
	 * @apiDescription
	 *    Recupera um registro de Estoque
	 *    
	 * @apiName findOne
	 * 
	 * @apiGroup Estoque
	 *
	 * @apiParam {Long} estoqueId ID de Estoque
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia do EstoqueDTO (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@GET
	@Path("/{anoMes}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response findOne(@PathParam("anoMes") String anoMes, @QueryParam("produtoId") Integer produtoId, 
			@QueryParam("frutaId") Integer frutaId, @QueryParam("localEstoqueId") Integer localEstoqueId) throws EstoqueException {
        try {
        	EstoqueKey key = service.getEstoqueKey(produtoId, frutaId, localEstoqueId);
        	key.setAnoMes(anoMes);
        	
        	EstoqueDTO dto = service.get(key);
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), dto)).build();
        } catch (NotFoundException nfe) {
        	return notFound("Nenhum registro de estoque foi encontrado");
        	
        } catch (EstoqueException e) {
			return badRequest(e);
		}
    }

	/**
	 * @api {get} /estoque/list
	 *    List All Order By Id
	 *    
	 * @apiDescription
	 *    Recupera uma lista de Estoque ordenada por NOME
	 *    
	 * @apiName listAll
	 * 
	 * @apiGroup Estoque
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e lista de EstoqueDTO (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response listAll() throws Exception {
        try {
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), service.listAll())).build();
        } catch (EstoqueException e) {
			return badRequest(e);
		}
    }
	
	/**
	 * @api {get} /estoque/orderByNome
	 *    List All Order By Nome
	 *    
	 * @apiDescription
	 *    Recupera uma lista de Estoque ordenada por NOME
	 *    
	 * @apiName listAllOrderByNome
	 * 
	 * @apiGroup Estoque
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e lista de EstoqueDTO (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@GET
	@Path("/anoMes/{anoMes}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response listAllOrderByNome(@PathParam("anoMes") String anoMes, @QueryParam("produtoId") Integer produtoId, 
			@QueryParam("frutaId") Integer frutaId, @QueryParam("localEstoqueId") Integer localEstoqueId) throws EstoqueException {
		
        try {
        	
        	produtoId = (produtoId == null)? -1: produtoId;
			frutaId = (frutaId == null)? -1: frutaId;
			localEstoqueId = (localEstoqueId == null)? -1: localEstoqueId;
        	
            return Response.ok()
            		.entity(new ResponseDTO(Status.OK.getStatusCode(), service.listByAnoMesAndProdutoOrFrutaOrLocalEstoque(anoMes, produtoId, frutaId, localEstoqueId)))
            		.build();
        } catch (NotFoundException nfe) {
        	return notFound("Nenhum registro de estoque foi encontrado", nfe);
        	
        } catch (EstoqueException e) {
			return badRequest(e);
		}
    }

}
	