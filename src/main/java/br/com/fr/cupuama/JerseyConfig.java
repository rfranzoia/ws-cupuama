package br.com.fr.cupuama;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import br.com.fr.cupuama.rest.ClienteFornecedorFrutaREST;
import br.com.fr.cupuama.rest.ClienteFornecedorREST;
import br.com.fr.cupuama.rest.FrutaREST;
import br.com.fr.cupuama.rest.LocalEstoqueREST;
import br.com.fr.cupuama.rest.ProcessoMovimentacaoREST;
import br.com.fr.cupuama.rest.ProdutoFrutaREST;
import br.com.fr.cupuama.rest.ProdutoREST;
import br.com.fr.cupuama.rest.TipoMovimentacaoREST;

@Component
@ApplicationPath("/ws-cupuama/api")
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		register(FrutaREST.class);
		register(ProdutoREST.class);
		register(ProdutoFrutaREST.class);
		register(ClienteFornecedorFrutaREST.class);
		register(TipoMovimentacaoREST.class);
		register(LocalEstoqueREST.class);
		register(ProcessoMovimentacaoREST.class);
		register(ClienteFornecedorREST.class);
	}
}
