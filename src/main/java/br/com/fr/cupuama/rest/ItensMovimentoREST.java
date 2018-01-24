package br.com.fr.cupuama.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import br.com.fr.cupuama.entity.dto.ResponseDTO;
import br.com.fr.cupuama.exception.ItensMovimentoException;
import br.com.fr.cupuama.service.ItensMovimentoService;

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
	
}
	