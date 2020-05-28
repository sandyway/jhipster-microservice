package online.kehan.connect.service;

import online.kehan.connect.domain.Recipient;
import online.kehan.connect.repository.RecipientRepository;
import online.kehan.connect.service.dto.RecipientDTO;
import online.kehan.connect.service.mapper.RecipientMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Recipient}.
 */
@Service
@Transactional
public class RecipientService {

    private final Logger log = LoggerFactory.getLogger(RecipientService.class);

    private final RecipientRepository recipientRepository;

    private final RecipientMapper recipientMapper;

    public RecipientService(RecipientRepository recipientRepository, RecipientMapper recipientMapper) {
        this.recipientRepository = recipientRepository;
        this.recipientMapper = recipientMapper;
    }

    /**
     * Save a recipient.
     *
     * @param recipientDTO the entity to save.
     * @return the persisted entity.
     */
    public RecipientDTO save(RecipientDTO recipientDTO) {
        log.debug("Request to save Recipient : {}", recipientDTO);
        Recipient recipient = recipientMapper.toEntity(recipientDTO);
        recipient = recipientRepository.save(recipient);
        return recipientMapper.toDto(recipient);
    }

    /**
     * Get all the recipients.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RecipientDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Recipients");
        return recipientRepository.findAll(pageable)
            .map(recipientMapper::toDto);
    }


    /**
     * Get one recipient by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RecipientDTO> findOne(Long id) {
        log.debug("Request to get Recipient : {}", id);
        return recipientRepository.findById(id)
            .map(recipientMapper::toDto);
    }

    /**
     * Delete the recipient by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Recipient : {}", id);

        recipientRepository.deleteById(id);
    }
}
