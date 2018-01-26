package br.com.fr.cupuama.rest;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import br.com.fr.cupuama.entity.dto.CaixaDTO;
import br.com.fr.cupuama.entity.dto.ResponseDTO;
import br.com.fr.cupuama.exception.CaixaException;
import br.com.fr.cupuama.service.CaixaService;

@Component
@Path("v1/caixa")
public class CaixaREST extends BasicREST {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private CaixaService service;
	
	/**
	 * @api {get} /caixa/{anoMes}
	 *    Find One
	 *    
	 * @apiDescription
	 *    Recupera um registro de Caixa
	 *    
	 * @apiName findOne
	 * 
	 * @apiGroup Caixa
	 *
	 * @apiParam {String} anoMes AnoMes de referencia
	 * @apiParam {Long} produto ID do produto
	 * @apiParam {Long} fruta ID da fruta
	 * @apiParam {Long} localCaixa ID do local de caixa
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia do CaixaDTO (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@GET
	@Path("/{anoMes}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response findOne(@PathParam("anoMes") String anoMes) throws CaixaException {
        try {
        	CaixaDTO dto = service.get(anoMes);
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), dto)).build();
        } catch (NotFoundException nfe) {
        	return notFound("Nenhum registro de caixa foi encontrado");
        	
        } catch (CaixaException e) {
			return badRequest(e);
		}
    }

	/**
	 * @api {get} /caixa/list
	 *    List All Order By Id
	 *    
	 * @apiDescription
	 *    Recupera uma lista de Caixa ordenada por NOME
	 *    
	 * @apiName listAll
	 * 
	 * @apiGroup Caixa
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e lista de CaixaDTO (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response listAll() throws Exception {
        try {
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), service.listAll())).build();
        } catch (CaixaException e) {
			return badRequest(e);
		}
    }
	
}
	