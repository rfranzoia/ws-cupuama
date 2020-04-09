package br.com.cupuama.service.cashflow;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.cupuama.domain.cashflow.DocumentType;
import br.com.cupuama.exception.EntityNotFoundException;
import br.com.cupuama.repository.DocumentTypeRepository;
import br.com.cupuama.service.DefaultServiceImplementation;


/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for some documentType specific things.
 * <p/>
 */
@Service
public class DocumentTypeService extends DefaultServiceImplementation<DocumentType, Long> {

	public DocumentTypeService(final DocumentTypeRepository documentTypeRepository) {
		super(documentTypeRepository);
	}

	/**
	 * Update the location for a documentType.
	 *
	 * @param documentTypeId
	 * @param longitude
	 * @param latitude
	 * @throws EntityNotFoundException
	 */
	@Transactional
	public void update(long documentTypeId, String name) throws EntityNotFoundException {
		DocumentType documentType = findByIdChecked(documentTypeId);
		documentType.setName(name);
	}

	/**
	 * Find all documentTypes by name.
	 *
	 * @param name
	 */
	public List<DocumentType> findByName(String name) {
		return ((DocumentTypeRepository) repository).findByNameLike(name + "%");
	}

}
