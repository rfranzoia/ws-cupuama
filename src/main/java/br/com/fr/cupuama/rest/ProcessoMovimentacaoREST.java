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

import br.com.fr.cupuama.entity.Produto;
import br.com.fr.cupuama.entity.TipoMovimentacao;
import br.com.fr.cupuama.entity.dto.ProcessoMovimentacaoDTO;
import br.com.fr.cupuama.entity.dto.ProdutoDTO;
import br.com.fr.cupuama.entity.dto.ResponseDTO;
import br.com.fr.cupuama.entity.dto.TipoMovimentacaoDTO;
import br.com.fr.cupuama.entity.key.ProcessoMovimentacaoKey;
import br.com.fr.cupuama.exception.LocalEstoqueException;
import br.com.fr.cupuama.exception.ProcessoMovimentacaoException;
import br.com.fr.cupuama.exception.ProdutoException;
import br.com.fr.cupuama.exception.TipoMovimentacaoException;
import br.com.fr.cupuama.service.ProcessoMovimentacaoService;
import br.com.fr.cupuama.service.ProdutoService;
import br.com.fr.cupuama.service.TipoMovimentacaoService;
import br.com.fr.cupuama.util.Util;

@Component
@Path("v1/processoMovimentacao")
public class ProcessoMovimentacaoREST extends BasicREST {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ProcessoMovimentacaoService service;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private TipoMovimentacaoService tipoMovimentacaoService;
	
	/**
	 * @api {post} /processoMovimentacao
	 *    Save
	 *    
	 * @apiDescription
	 *    Inclui um conjunto de registros de ProdutosxTipoMovimentacao
	 *    
	 * @apiName save
	 * 
	 * @apiGroup ProcessoMovimentacao
	 *
	 * @apiParam {ProcessoMovimentacaoDTO} dto DTO com os dados do(s) registro(s)
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia do ProcessoMovimentacaoDTO inserido (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	public Response save(ProcessoMovimentacaoDTO dto, @Context UriInfo uriInfo) throws ProcessoMovimentacaoException {
		try {
			service.save(dto);
			
			ProdutoDTO produto = produtoService.get(dto.getKeyProdutoId());
			TipoMovimentacaoDTO tipoMovimentacao = tipoMovimentacaoService.get(dto.getKeyTipoMovimentacaoId());
			
			ProcessoMovimentacaoKey key = new ProcessoMovimentacaoKey();
			key.setProduto(Util.buildDTO(produto, Produto.class));
			key.setTipoMovimentacao(Util.buildDTO(tipoMovimentacao, TipoMovimentacao.class));
			key.setTipoEntradaSaida(dto.getKeyTipoEntradaSaida());
			
			URI location = uriInfo.getRequestUriBuilder().path(key.toString()).build();
			
			return Response.created(location).entity(new ResponseDTO(Status.CREATED.getStatusCode(), dto)).build();
			
		} catch (NotFoundException nfex) {
			return Response.status(Status.NOT_FOUND).entity(new ResponseDTO(Status.NOT_FOUND.getStatusCode(), "Produto e/ou TipoMovimentacao e/ou LocalEstoque não encontrado(s)!", nfex)).build();
			
		} catch (ProdutoException | TipoMovimentacaoException | LocalEstoqueException lptmex) {
			return Response.status(Status.NOT_FOUND).entity(new ResponseDTO(Status.NOT_FOUND.getStatusCode(), "Produto e/ou TipoMovimentacao e/ou LocalEstoque não encontrado(s)!", lptmex)).build();
			
		} catch (ProcessoMovimentacaoException pmex) {
			return Response.status(Status.BAD_REQUEST).entity(new ResponseDTO(Status.BAD_REQUEST.getStatusCode(), pmex.getMessage(), pmex)).build();
		}        
	}
	
	/**
	 * @api {put} /processoMovimentacao
	 *    Update
	 *    
	 * @apiDescription
	 *    Atualiza um registro de ProdutoTipoMovimentacao
	 *    
	 * @apiName update
	 * 
	 * @apiGroup ProcessoMovimentacao
	 *
	 * @apiParam {ProcessoMovimentacaoDTO} dto DTO com os dados do(s) registro(s)
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia do ProcessoMovimentacaoDTO inserido (use responseDTO.getEntity() para recuperar este objeto)
	 *
	 */
	@PUT
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	public Response update(ProcessoMovimentacaoDTO dto) throws ProcessoMovimentacaoException {
		try {
			service.update(dto);
			
			return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), dto)).build();
			
		} catch (ProdutoException | TipoMovimentacaoException | LocalEstoqueException lptmex) {
			return Response.status(Status.NOT_FOUND).entity(new ResponseDTO(Status.NOT_FOUND.getStatusCode(), "Produto e/ou TipoMovimentacao e/ou LocalEstoque não encontrado(s)!", lptmex)).build();
			
		} catch (ProcessoMovimentacaoException pmex) {
			return Response.status(Status.BAD_REQUEST).entity(new ResponseDTO(Status.BAD_REQUEST.getStatusCode(), pmex.getMessage(), pmex)).build();
		}
	}
	
	/**
	 * @api {delete} /processoMovimentacao/produto/tipoMovimentacao/tipoEntadaSaida?produto=:produto&tipoMovimentacao=:tipoMovimentacao&tipoEntradaSaida=:tipoEntradaSaida
	 *    Delete
	 *    
	 * @apiDescription
	 *    Remove um registro de ProcessoMovimentacao utilizando os IDs de Produto / TipoMovimentacao e també o TipoEntradaSaida
	 *    
	 * @apiName delete
	 * 
	 * @apiGroup ProcessoMovimentacao
	 *
	 * @apiParam {Integer} produto ID da Produto
	 * @apiParam {Integer} tipoMovimentacao ID do TipoMovimentacao
	 * @apiParam {Character} tipoEntradaSaida Tipo de Entrada/Saida (E ou S)
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso do processo de exclusão do registro
	 *
	 */
	@DELETE
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	public Response delete(@QueryParam("produto") Integer produtoId, @QueryParam("tipoMovimentacao") Integer tipoMovimentacaoId, @QueryParam("tipoEntradaSaida") Character tipoEntradaSaida) throws ProcessoMovimentacaoException {
		try {
			ProdutoDTO produto = produtoService.get(produtoId);
			TipoMovimentacaoDTO tipoMovimentacao = tipoMovimentacaoService.get(tipoMovimentacaoId);
			
			ProcessoMovimentacaoKey key = new ProcessoMovimentacaoKey();
			key.setProduto(Util.buildDTO(produto, Produto.class));
			key.setTipoMovimentacao(Util.buildDTO(tipoMovimentacao, TipoMovimentacao.class));
			key.setTipoEntradaSaida(tipoEntradaSaida);
			
			service.deleteByKey(key);
			
			return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode())).build();
			
		} catch (ProdutoException | TipoMovimentacaoException pfex) {
			return Response.status(Status.NOT_FOUND).entity(new ResponseDTO(Status.NOT_FOUND.getStatusCode(), "Produto e/ou TipoMovimentacao não encontrado(s)!", pfex)).build();
			
		} catch (NotFoundException nfex) {
			return Response.status(Status.NOT_FOUND).entity(new ResponseDTO(Status.NOT_FOUND.getStatusCode(), "ProcessoMovimentacao não encontrado(s)!", nfex)).build();
			
		} catch (ProcessoMovimentacaoException fex) {
			return Response.status(Status.BAD_REQUEST).entity(new ResponseDTO(Status.BAD_REQUEST.getStatusCode(), fex.getMessage(), fex)).build();
		}        
	}
	/**
	 * @api {get} /processoMovimentacao/produto/tipoMovimentacao/tipoEntadaSaida?produto=:produto&tipoMovimentacao=:tipoMovimentacao&tipoEntradaSaida=:tipoEntradaSaida
	 *    Get ProcessoMovimentacao By Produto, TipoMovimentacao & TipoEntradaSaida 
	 *    
	 * @apiDescription
	 *    Recupera um registro de ProcessoMovimentacaoDTO
	 *    
	 * @apiName findOne
	 * 
	 * @apiGroup ProcessoMovimentacao
	 * 
	 * @apiParam {Integer} produto ID da Produto
	 * @apiParam {Integer} tipoMovimentacao ID do TipoMovimentacao
	 * @apiParam {Character} tipoEntradaSaida Tipo de Entrada/Saida (E ou S)
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia de ProcessoMovimentacaoDTO para produto e tipoMovimentacao especificados (use responseDTO.getEntity() para recuperar este objeto) 
	 *
	 */
	@GET
	@Path("/produto/tipoMovimentacao/tipoEntradaSaida")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response findOne(@QueryParam("produto") Integer produtoId, @QueryParam("tipoMovimentacao") Integer tipoMovimentacaoId, @QueryParam("tipoEntradaSaida") Character tipoEntradaSaida) throws ProcessoMovimentacaoException {
        try {
        	ProdutoDTO produto = produtoService.get(produtoId);
			TipoMovimentacaoDTO tipoMovimentacao = tipoMovimentacaoService.get(tipoMovimentacaoId);
			
			ProcessoMovimentacaoKey key = new ProcessoMovimentacaoKey();
			key.setProduto(Util.buildDTO(produto, Produto.class));
			key.setTipoMovimentacao(Util.buildDTO(tipoMovimentacao, TipoMovimentacao.class));
			key.setTipoEntradaSaida(tipoEntradaSaida);
			
            return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), service.get(key))).build();
		} catch (ProdutoException | TipoMovimentacaoException pfex) {
			return Response.status(Status.NOT_FOUND).entity(new ResponseDTO(Status.NOT_FOUND.getStatusCode(), "Produto e/ou TipoMovimentacao não encontrado(s)!", pfex)).build();
			
        } catch (ProcessoMovimentacaoException fex) {
        	return Response.status(Status.BAD_REQUEST).entity(new ResponseDTO(Status.BAD_REQUEST.getStatusCode(), fex.getMessage(), fex)).build();
		}
    }
    
	/**
	 * @api {get} /processoMovimentacao/produto/{produto}
	 *    List All 
	 *    
	 * @apiDescription
	 *    Recupera a lista de todos os registros de ProcessoMovimentacaoDTO 
	 *    
	 * @apiName listAll
	 * 
	 * @apiGroup ProcessoMovimentacao
	 * 
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e lista de ProcessoMovimentacaoDTO com perfis associados (use responseDTO.getEntity() para recuperar este objeto) 
	 *
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response listAll() throws ProcessoMovimentacaoException, ProdutoException {
        try {
        	return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), service.listAllOrderByTipoMovimentacaoProdutoTipoEntradaSaida())).build();
		} catch (NotFoundException nfex) {
			return Response.status(Status.NOT_FOUND).entity(new ResponseDTO(Status.NOT_FOUND.getStatusCode(), "Produto e/ou TipoMovimentacao não encontrado(s)!", nfex)).build();
			
		} catch (ProcessoMovimentacaoException fex) {
			return Response.status(Status.BAD_REQUEST).entity(new ResponseDTO(Status.BAD_REQUEST.getStatusCode(), fex.getMessage(), fex)).build();
		}
    }
	
	/**
	 * @api {get} /processoMovimentacao/produto/{produto}
	 *    List ProdutoxTipoMovimentacao by Produto 
	 *    
	 * @apiDescription
	 *    Recupera uma lista de registros de ProcessoMovimentacaoDTO para o Produto informada
	 *    
	 * @apiName listByProduto
	 * 
	 * @apiGroup ProcessoMovimentacao
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
    public Response listByProduto(@PathParam("produtoId") Integer produtoId) throws ProcessoMovimentacaoException, ProdutoException {
        try {
        	return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), service.listByProdutoOrderByTipoMovimentacao(produtoId))).build();
		} catch (NotFoundException nfex) {
			return Response.status(Status.NOT_FOUND).entity(new ResponseDTO(Status.NOT_FOUND.getStatusCode(), "Produto e/ou TipoMovimentacao não encontrado(s)!", nfex)).build();
			
		} catch (ProcessoMovimentacaoException fex) {
			return Response.status(Status.BAD_REQUEST).entity(new ResponseDTO(Status.BAD_REQUEST.getStatusCode(), fex.getMessage(), fex)).build();
		}
    }
	
	/**
	 * @api {get} /processoMovimentacao/tipoMovimentacao/{tipoMovimentacao}
	 *    List ProdutoxTipoMovimentacao by TipoMovimentacao 
	 *    
	 * @apiDescription
	 *    Recupera uma lista de registros de ProcessoMovimentacaoDTO para o TipoMovimentacao informado
	 *    
	 * @apiName listByTipoMovimentacao
	 * 
	 * @apiGroup ProcessoMovimentacao
	 * 
	 * @apiParam {Long} tipoMovimentacao ID do TipoMovimentacao
	 *
	 * @apiSuccess {ResponseDTO} responseDTO	
	 *    Mensagem de sucesso e instancia de TipoMovimentacaoDTO com lista de produtos associados (use responseDTO.getEntity() para recuperar este objeto) 
	 *
	 */
	@GET
	@Path("/tipoMovimentacao/{tipoMovimentacaoId}")
	@Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response listByTipoMovimentacao(@PathParam("tipoMovimentacaoId") Integer tipoMovimentacaoId) throws ProcessoMovimentacaoException, TipoMovimentacaoException {
        try {
        	return Response.ok().entity(new ResponseDTO(Status.OK.getStatusCode(), service.listByTipoMovimentacaoOrderByProduto(tipoMovimentacaoId))).build();
		} catch (NotFoundException nfex) {
			return Response.status(Status.NOT_FOUND).entity(new ResponseDTO(Status.NOT_FOUND.getStatusCode(), "Produto e/ou TipoMovimentacao não encontrado(s)!", nfex)).build();
			
		} catch (ProcessoMovimentacaoException fex) {
			return Response.status(Status.BAD_REQUEST).entity(new ResponseDTO(Status.BAD_REQUEST.getStatusCode(), fex.getMessage(), fex)).build();
		}
    }
    
}
	