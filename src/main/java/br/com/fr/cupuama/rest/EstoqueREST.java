package br.com.fr.cupuama.rest;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

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
	 * @api {get} /estoque/{anoMes}?produto={produto}&fruta={fruta}&localEstoque={localEstoque}
	 *    Find One
	 *    
	 * @apiDescription
	 *    Recupera um registro de Estoque
	 *    
	 * @apiName findOne
	 * 
	 * @apiGroup Estoque
	 *
	 * @apiParam {String} anoMes AnoMes de referencia
	 * @apiParam {Long} produto ID do produto
	 * @apiParam {Long} fruta ID da fruta
	 * @apiParam {Long} localEstoque ID do local de estoque
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia do EstoqueDTO (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@GET
	@Path("/{anoMes}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response findOne(@PathParam("anoMes") String anoMes, @QueryParam("produto") Integer produtoId, 
			@QueryParam("fruta") Integer frutaId, @QueryParam("localEstoque") Integer localEstoqueId) throws EstoqueException {
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
	