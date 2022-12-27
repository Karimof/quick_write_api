package uz.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;
import uz.repository.TextRepository;
import uz.service.TextQueryService;
import uz.service.TextService;
import uz.service.criteria.TextCriteria;
import uz.service.dto.TextDTO;
import uz.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link uz.domain.Text}.
 */
@RestController
@RequestMapping("/api")
public class TextResource {

    private final Logger log = LoggerFactory.getLogger(TextResource.class);

    private static final String ENTITY_NAME = "text";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TextService textService;

    private final TextRepository textRepository;

    private final TextQueryService textQueryService;

    public TextResource(TextService textService, TextRepository textRepository, TextQueryService textQueryService) {
        this.textService = textService;
        this.textRepository = textRepository;
        this.textQueryService = textQueryService;
    }

    /**
     * {@code POST  /texts} : Create a new text.
     *
     * @param textDTO the textDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new textDTO, or with status {@code 400 (Bad Request)} if the text has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/texts")
    public ResponseEntity<TextDTO> createText(@Valid @RequestBody TextDTO textDTO) throws URISyntaxException {
        log.debug("REST request to save Text : {}", textDTO);
        if (textDTO.getId() != null) {
            throw new BadRequestAlertException("A new text cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TextDTO result = textService.save(textDTO);
        return ResponseEntity
            .created(new URI("/api/texts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /texts/:id} : Updates an existing text.
     *
     * @param id the id of the textDTO to save.
     * @param textDTO the textDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated textDTO,
     * or with status {@code 400 (Bad Request)} if the textDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the textDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/texts/{id}")
    public ResponseEntity<TextDTO> updateText(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TextDTO textDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Text : {}, {}", id, textDTO);
        if (textDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, textDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!textRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TextDTO result = textService.update(textDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, textDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /texts/:id} : Partial updates given fields of an existing text, field will ignore if it is null
     *
     * @param id the id of the textDTO to save.
     * @param textDTO the textDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated textDTO,
     * or with status {@code 400 (Bad Request)} if the textDTO is not valid,
     * or with status {@code 404 (Not Found)} if the textDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the textDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/texts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TextDTO> partialUpdateText(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TextDTO textDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Text partially : {}, {}", id, textDTO);
        if (textDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, textDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!textRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TextDTO> result = textService.partialUpdate(textDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, textDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /texts} : get all the texts.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of texts in body.
     */
    @GetMapping("/texts")
    public ResponseEntity<List<TextDTO>> getAllTexts(
        TextCriteria criteria,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get Texts by criteria: {}", criteria);
        Page<TextDTO> page = textQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /texts/count} : count all the texts.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/texts/count")
    public ResponseEntity<Long> countTexts(TextCriteria criteria) {
        log.debug("REST request to count Texts by criteria: {}", criteria);
        return ResponseEntity.ok().body(textQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /texts/:id} : get the "id" text.
     *
     * @param id the id of the textDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the textDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/texts/{id}")
    public ResponseEntity<TextDTO> getText(@PathVariable Long id) {
        log.debug("REST request to get Text : {}", id);
        Optional<TextDTO> textDTO = textService.findOne(id);
        return ResponseUtil.wrapOrNotFound(textDTO);
    }

    /**
     * {@code DELETE  /texts/:id} : delete the "id" text.
     *
     * @param id the id of the textDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/texts/{id}")
    public ResponseEntity<Void> deleteText(@PathVariable Long id) {
        log.debug("REST request to delete Text : {}", id);
        textService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
