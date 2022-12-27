package uz.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.domain.Text;
import uz.repository.TextRepository;
import uz.service.dto.TextDTO;
import uz.service.mapper.TextMapper;

/**
 * Service Implementation for managing {@link Text}.
 */
@Service
@Transactional
public class TextService {

    private final Logger log = LoggerFactory.getLogger(TextService.class);

    private final TextRepository textRepository;

    private final TextMapper textMapper;

    public TextService(TextRepository textRepository, TextMapper textMapper) {
        this.textRepository = textRepository;
        this.textMapper = textMapper;
    }

    /**
     * Save a text.
     *
     * @param textDTO the entity to save.
     * @return the persisted entity.
     */
    public TextDTO save(TextDTO textDTO) {
        log.debug("Request to save Text : {}", textDTO);
        Text text = textMapper.toEntity(textDTO);
        text = textRepository.save(text);
        return textMapper.toDto(text);
    }

    /**
     * Update a text.
     *
     * @param textDTO the entity to save.
     * @return the persisted entity.
     */
    public TextDTO update(TextDTO textDTO) {
        log.debug("Request to update Text : {}", textDTO);
        Text text = textMapper.toEntity(textDTO);
        text = textRepository.save(text);
        return textMapper.toDto(text);
    }

    /**
     * Partially update a text.
     *
     * @param textDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TextDTO> partialUpdate(TextDTO textDTO) {
        log.debug("Request to partially update Text : {}", textDTO);

        return textRepository
            .findById(textDTO.getId())
            .map(existingText -> {
                textMapper.partialUpdate(existingText, textDTO);

                return existingText;
            })
            .map(textRepository::save)
            .map(textMapper::toDto);
    }

    /**
     * Get all the texts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TextDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Texts");
        return textRepository.findAll(pageable).map(textMapper::toDto);
    }

    /**
     * Get one text by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TextDTO> findOne(Long id) {
        log.debug("Request to get Text : {}", id);
        return textRepository.findById(id).map(textMapper::toDto);
    }

    /**
     * Delete the text by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Text : {}", id);
        textRepository.deleteById(id);
    }
}
