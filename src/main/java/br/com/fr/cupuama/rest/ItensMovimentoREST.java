package br.com.fr.cupuama.rest;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import br.com.fr.cupuama.entity.dto.ResponseDTO;
import br.com.fr.cupuama.exception.ItensMovimentoException;
import br.com.fr.cupuama.service.ItensMovimentoService;
import br.com.fr.cupuama.util.Util;

@Component
@Path("v1/itensMovimento")
public class ItensMovimentoREST extends BasicREST {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ItensMovimentoService service;
	
	/**
	 * @api {get} /itensMovimento/movimento/{movimentoId}
	 *    List by Movimento
	 *    
	 * @apiDescription
	 *    Recupera uma lista de ItensMovimento para um Movimento especificado
	 *    
	 * @apiName listAllOrderByDtItensMovimento
	 * 
	 * @apiGroup ItensMovimento
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e lista de ItensMovimentoDTO (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@GET
	@Path("/movimento/{movimentoId}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response listAllOrderByDtItensMovimento(@PathParam("movimentoId") Integer movimentoId) throws Exception {
        try {
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), service.listByMovimento(movimentoId))).build();
        } catch (ItensMovimentoException e) {
        	return badRequest(e);
		}
    }
	
	/**
	 * @api {get} /itensMovimento/periodo/produto/fruta/localEstoque/?inicio={inicio}&fim={fim}&produto={produtoId}&fruta={frutaId}&localEstoque={localEstoqueId}
	 *    List by Periodo & Produto & fruta & local
	 *    
	 * @apiDescription
	 *    Recupera uma lista de ItensMovimento para um periodo especificado.
	 *    
	 * @apiName listByPeriodoAndProdutoAndFutaAndLocal
	 * 
	 * @apiGroup ItensMovimento
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e lista de ItensMovimentoDTO (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@GET
	@Path("/periodo/produto/fruta/localEstoque")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response listByPeriodoAndProdutoAndFutaAndLocal(@QueryParam("inicio") String inicio, @QueryParam("fim") String fim, 
    		@QueryParam("produtoId") Integer produtoId, @QueryParam("frutaId") Integer frutaId, @QueryParam("localEstoqueId") Integer localEstoqueId) throws Exception {
        try {
        	if (inicio == null) {
        		inicio = Util.DATE_FORMAT_COMPACT.format(new Date());
        	}
        	
        	if (fim == null) {
        		fim = Util.DATE_FORMAT_COMPACT.format(new Date());
        	}
        	
        	Date dtInicio = Util.DATE_FORMAT_TIMESTAMP_COMPACT.parse(inicio + "000000");
        	Date dtFim = Util.DATE_FORMAT_TIMESTAMP_COMPACT.parse(fim + "235959");

        	if (dtInicio.after(dtFim)) {
        		return badRequest("Data inicial não pode ser posterior à final!");
        	}
        	
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), service.listByEstoque(dtInicio, dtFim, produtoId, frutaId, localEstoqueId))).build();
        } catch (ItensMovimentoException e) {
        	return badRequest(e);
		}
    }
	
}
	