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

import br.com.fr.cupuama.entity.dto.ClienteFornecedorDTO;
import br.com.fr.cupuama.entity.dto.ResponseDTO;
import br.com.fr.cupuama.exception.ClienteFornecedorException;
import br.com.fr.cupuama.service.ClienteFornecedorService;

@Component
@Path("v1/clienteFornecedor")
public class ClienteFornecedorREST extends BasicREST {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ClienteFornecedorService service;
	
	/**
	 * @api {post} /clienteFornecedor
	 *    Save
	 *    
	 * @apiDescription
	 *    Inclui um registro em ClienteFornecedor
	 *    
	 * @apiName save
	 * 
	 * @apiGroup ClienteFornecedor
	 *
	 * @apiParam {ClienteFornecedorDTO} dto DTO com os dados do registro de ClienteFornecedor
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia do ClienteFornecedorDTO inserido (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	public Response save(ClienteFornecedorDTO dto, @Context UriInfo uriInfo) throws ClienteFornecedorException {
		try {
			dto = service.save(dto);
			
			URI location = uriInfo.getRequestUriBuilder().path(dto.getId().toString()).build();
			
			return Response.created(location).entity(new ResponseDTO(Status.CREATED.getStatusCode(), dto)).build();
			
		} catch (ClienteFornecedorException e) {
			return badRequest(e);
		}        
	}
	
	/**
	 * @api {put} /clienteFornecedor/{clienteFornecedorId}
	 *    Update
	 *    
	 * @apiDescription
	 *    Altera os dados de um registro de ClienteFornecedor
	 *    
	 * @apiName update
	 * 
	 * @apiGroup ClienteFornecedor
	 *
	 * @apiParam {Long} clienteFornecedorId ID de ClienteFornecedor
	 * @apiParam {ClienteFornecedorDTO} dto DTO com os dados para alteração do registro de ClienteFornecedor
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia do ClienteFornecedorDTO inserido (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@PUT
	@Path("/{clienteFornecedorId}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response update(@PathParam("clienteFornecedorId") Integer clienteFornecedorId, ClienteFornecedorDTO dto) throws ClienteFornecedorException {
        try {
        	
        	dto = service.update(clienteFornecedorId, dto);
        	
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), dto)).build();
            
		} catch (NotFoundException nfe) {
			return notFound("Nenhuma clienteFornecedor foi encontrada com o ID especificado: " + clienteFornecedorId);
			
		} catch (ClienteFornecedorException e) {
			return badRequest(e);
		}
    }
	
	/**
	 * @api {delete} /clienteFornecedor/{clienteFornecedorId}
	 *    Delete
	 *    
	 * @apiDescription
	 *    Remove um registro de ClienteFornecedor
	 *    
	 * @apiName delete
	 * 
	 * @apiGroup ClienteFornecedor
	 *
	 * @apiParam {Long} clienteFornecedorId ID de ClienteFornecedor
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso da operação de remoção do registro
	 *
	 */
	@DELETE
	@Path("/{clienteFornecedorId}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	public Response delete(@PathParam("clienteFornecedorId") Integer clienteFornecedorId) throws ClienteFornecedorException {
        try {
        	
        	service.delete(clienteFornecedorId);

        	return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode())).build();
            
		} catch (NotFoundException nfe) {
			return notFound("Nenhuma clienteFornecedor foi encontrada com o ID especificado: " + clienteFornecedorId);
			
		} catch (ClienteFornecedorException e) {
			return badRequest(e);
		}
    }
	
	/**
	 * @api {get} /clienteFornecedor/{clienteFornecedorId
	 *    Find One
	 *    
	 * @apiDescription
	 *    Recupera um registro de ClienteFornecedor
	 *    
	 * @apiName findOne
	 * 
	 * @apiGroup ClienteFornecedor
	 *
	 * @apiParam {Long} clienteFornecedorId ID de ClienteFornecedor
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia do ClienteFornecedorDTO (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@GET
	@Path("/{clienteFornecedorId}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response findOne(@PathParam("clienteFornecedorId") Integer clienteFornecedorId) throws ClienteFornecedorException {
        try {
        	ClienteFornecedorDTO dto = service.get(clienteFornecedorId);
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), dto)).build();
        } catch (NotFoundException nfe) {
        	return notFound("Nenhuma clienteFornecedor foi encontrada com o ID especificado: " + clienteFornecedorId);
        	
        } catch (ClienteFornecedorException e) {
        	return badRequest(e);
		}
    }

	/**
	 * @api {get} /clienteFornecedor
	 *    List All Order By Id
	 *    
	 * @apiDescription
	 *    Recupera uma lista de ClienteFornecedor ordenada por NOME
	 *    
	 * @apiName listAll
	 * 
	 * @apiGroup ClienteFornecedor
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e lista de ClienteFornecedorDTO (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response listAll() throws Exception {
        try {
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), service.listAll())).build();
        } catch (ClienteFornecedorException e) {
        	return badRequest(e);
		}
    }
	
	/**
	 * @api {get} /clienteFornecedor/orderByNome
	 *    List All Order By Nome
	 *    
	 * @apiDescription
	 *    Recupera uma lista de ClienteFornecedor ordenada por NOME
	 *    
	 * @apiName listAllOrderByNome
	 * 
	 * @apiGroup ClienteFornecedor
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e lista de ClienteFornecedorDTO (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@GET
	@Path("/orderByNome")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response listAllOrderByNome() throws Exception {
        try {
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), service.listAllOrderByNome())).build();
        } catch (ClienteFornecedorException e) {
        	return badRequest(e);
		}
    }
	
	/**
	 * @api {get} /clienteFornecedor/tipo/{tipo}
	 *    List By Tipo
	 *    
	 * @apiDescription
	 *    Recupera uma lista de ClienteFornecedor de uma SAFRA especificada ordenada por NOME
	 *    
	 * @apiName listByTipo
	 * 
	 * @apiGroup ClienteFornecedor
	 * 
	 * @apiParam {Charactger} tipo Tipo C para cliente ou F para fornecedor
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e lista de ClienteFornecedorDTO (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@GET
	@Path("/tipo/{tipo}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response listByTipo(@PathParam("tipo") Character tipo) throws Exception {
        try {
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), service.listByTipoOrderByNome(tipo))).build();
        } catch (ClienteFornecedorException e) {
        	return badRequest(e);
		}
    }
	
    
}
	