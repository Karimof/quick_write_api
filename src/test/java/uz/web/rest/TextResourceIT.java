package uz.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import uz.IntegrationTest;
import uz.domain.Text;
import uz.repository.TextRepository;
import uz.service.criteria.TextCriteria;
import uz.service.dto.TextDTO;
import uz.service.mapper.TextMapper;

/**
 * Integration tests for the {@link TextResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TextResourceIT {

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/texts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TextRepository textRepository;

    @Autowired
    private TextMapper textMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTextMockMvc;

    private Text text;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Text createEntity(EntityManager em) {
        Text text = new Text().text(DEFAULT_TEXT);
        return text;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Text createUpdatedEntity(EntityManager em) {
        Text text = new Text().text(UPDATED_TEXT);
        return text;
    }

    @BeforeEach
    public void initTest() {
        text = createEntity(em);
    }

    @Test
    @Transactional
    void createText() throws Exception {
        int databaseSizeBeforeCreate = textRepository.findAll().size();
        // Create the Text
        TextDTO textDTO = textMapper.toDto(text);
        restTextMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(textDTO)))
            .andExpect(status().isCreated());

        // Validate the Text in the database
        List<Text> textList = textRepository.findAll();
        assertThat(textList).hasSize(databaseSizeBeforeCreate + 1);
        Text testText = textList.get(textList.size() - 1);
        assertThat(testText.getText()).isEqualTo(DEFAULT_TEXT);
    }

    @Test
    @Transactional
    void createTextWithExistingId() throws Exception {
        // Create the Text with an existing ID
        text.setId(1L);
        TextDTO textDTO = textMapper.toDto(text);

        int databaseSizeBeforeCreate = textRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTextMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(textDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Text in the database
        List<Text> textList = textRepository.findAll();
        assertThat(textList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = textRepository.findAll().size();
        // set the field null
        text.setText(null);

        // Create the Text, which fails.
        TextDTO textDTO = textMapper.toDto(text);

        restTextMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(textDTO)))
            .andExpect(status().isBadRequest());

        List<Text> textList = textRepository.findAll();
        assertThat(textList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTexts() throws Exception {
        // Initialize the database
        textRepository.saveAndFlush(text);

        // Get all the textList
        restTextMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(text.getId().intValue())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)));
    }

    @Test
    @Transactional
    void getText() throws Exception {
        // Initialize the database
        textRepository.saveAndFlush(text);

        // Get the text
        restTextMockMvc
            .perform(get(ENTITY_API_URL_ID, text.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(text.getId().intValue()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT));
    }

    @Test
    @Transactional
    void getTextsByIdFiltering() throws Exception {
        // Initialize the database
        textRepository.saveAndFlush(text);

        Long id = text.getId();

        defaultTextShouldBeFound("id.equals=" + id);
        defaultTextShouldNotBeFound("id.notEquals=" + id);

        defaultTextShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTextShouldNotBeFound("id.greaterThan=" + id);

        defaultTextShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTextShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTextsByTextIsEqualToSomething() throws Exception {
        // Initialize the database
        textRepository.saveAndFlush(text);

        // Get all the textList where text equals to DEFAULT_TEXT
        defaultTextShouldBeFound("text.equals=" + DEFAULT_TEXT);

        // Get all the textList where text equals to UPDATED_TEXT
        defaultTextShouldNotBeFound("text.equals=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    void getAllTextsByTextIsInShouldWork() throws Exception {
        // Initialize the database
        textRepository.saveAndFlush(text);

        // Get all the textList where text in DEFAULT_TEXT or UPDATED_TEXT
        defaultTextShouldBeFound("text.in=" + DEFAULT_TEXT + "," + UPDATED_TEXT);

        // Get all the textList where text equals to UPDATED_TEXT
        defaultTextShouldNotBeFound("text.in=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    void getAllTextsByTextIsNullOrNotNull() throws Exception {
        // Initialize the database
        textRepository.saveAndFlush(text);

        // Get all the textList where text is not null
        defaultTextShouldBeFound("text.specified=true");

        // Get all the textList where text is null
        defaultTextShouldNotBeFound("text.specified=false");
    }

    @Test
    @Transactional
    void getAllTextsByTextContainsSomething() throws Exception {
        // Initialize the database
        textRepository.saveAndFlush(text);

        // Get all the textList where text contains DEFAULT_TEXT
        defaultTextShouldBeFound("text.contains=" + DEFAULT_TEXT);

        // Get all the textList where text contains UPDATED_TEXT
        defaultTextShouldNotBeFound("text.contains=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    void getAllTextsByTextNotContainsSomething() throws Exception {
        // Initialize the database
        textRepository.saveAndFlush(text);

        // Get all the textList where text does not contain DEFAULT_TEXT
        defaultTextShouldNotBeFound("text.doesNotContain=" + DEFAULT_TEXT);

        // Get all the textList where text does not contain UPDATED_TEXT
        defaultTextShouldBeFound("text.doesNotContain=" + UPDATED_TEXT);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTextShouldBeFound(String filter) throws Exception {
        restTextMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(text.getId().intValue())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT)));

        // Check, that the count call also returns 1
        restTextMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTextShouldNotBeFound(String filter) throws Exception {
        restTextMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTextMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingText() throws Exception {
        // Get the text
        restTextMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingText() throws Exception {
        // Initialize the database
        textRepository.saveAndFlush(text);

        int databaseSizeBeforeUpdate = textRepository.findAll().size();

        // Update the text
        Text updatedText = textRepository.findById(text.getId()).get();
        // Disconnect from session so that the updates on updatedText are not directly saved in db
        em.detach(updatedText);
        updatedText.text(UPDATED_TEXT);
        TextDTO textDTO = textMapper.toDto(updatedText);

        restTextMockMvc
            .perform(
                put(ENTITY_API_URL_ID, textDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(textDTO))
            )
            .andExpect(status().isOk());

        // Validate the Text in the database
        List<Text> textList = textRepository.findAll();
        assertThat(textList).hasSize(databaseSizeBeforeUpdate);
        Text testText = textList.get(textList.size() - 1);
        assertThat(testText.getText()).isEqualTo(UPDATED_TEXT);
    }

    @Test
    @Transactional
    void putNonExistingText() throws Exception {
        int databaseSizeBeforeUpdate = textRepository.findAll().size();
        text.setId(count.incrementAndGet());

        // Create the Text
        TextDTO textDTO = textMapper.toDto(text);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTextMockMvc
            .perform(
                put(ENTITY_API_URL_ID, textDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(textDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Text in the database
        List<Text> textList = textRepository.findAll();
        assertThat(textList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchText() throws Exception {
        int databaseSizeBeforeUpdate = textRepository.findAll().size();
        text.setId(count.incrementAndGet());

        // Create the Text
        TextDTO textDTO = textMapper.toDto(text);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTextMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(textDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Text in the database
        List<Text> textList = textRepository.findAll();
        assertThat(textList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamText() throws Exception {
        int databaseSizeBeforeUpdate = textRepository.findAll().size();
        text.setId(count.incrementAndGet());

        // Create the Text
        TextDTO textDTO = textMapper.toDto(text);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTextMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(textDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Text in the database
        List<Text> textList = textRepository.findAll();
        assertThat(textList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTextWithPatch() throws Exception {
        // Initialize the database
        textRepository.saveAndFlush(text);

        int databaseSizeBeforeUpdate = textRepository.findAll().size();

        // Update the text using partial update
        Text partialUpdatedText = new Text();
        partialUpdatedText.setId(text.getId());

        partialUpdatedText.text(UPDATED_TEXT);

        restTextMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedText.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedText))
            )
            .andExpect(status().isOk());

        // Validate the Text in the database
        List<Text> textList = textRepository.findAll();
        assertThat(textList).hasSize(databaseSizeBeforeUpdate);
        Text testText = textList.get(textList.size() - 1);
        assertThat(testText.getText()).isEqualTo(UPDATED_TEXT);
    }

    @Test
    @Transactional
    void fullUpdateTextWithPatch() throws Exception {
        // Initialize the database
        textRepository.saveAndFlush(text);

        int databaseSizeBeforeUpdate = textRepository.findAll().size();

        // Update the text using partial update
        Text partialUpdatedText = new Text();
        partialUpdatedText.setId(text.getId());

        partialUpdatedText.text(UPDATED_TEXT);

        restTextMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedText.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedText))
            )
            .andExpect(status().isOk());

        // Validate the Text in the database
        List<Text> textList = textRepository.findAll();
        assertThat(textList).hasSize(databaseSizeBeforeUpdate);
        Text testText = textList.get(textList.size() - 1);
        assertThat(testText.getText()).isEqualTo(UPDATED_TEXT);
    }

    @Test
    @Transactional
    void patchNonExistingText() throws Exception {
        int databaseSizeBeforeUpdate = textRepository.findAll().size();
        text.setId(count.incrementAndGet());

        // Create the Text
        TextDTO textDTO = textMapper.toDto(text);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTextMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, textDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(textDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Text in the database
        List<Text> textList = textRepository.findAll();
        assertThat(textList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchText() throws Exception {
        int databaseSizeBeforeUpdate = textRepository.findAll().size();
        text.setId(count.incrementAndGet());

        // Create the Text
        TextDTO textDTO = textMapper.toDto(text);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTextMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(textDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Text in the database
        List<Text> textList = textRepository.findAll();
        assertThat(textList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamText() throws Exception {
        int databaseSizeBeforeUpdate = textRepository.findAll().size();
        text.setId(count.incrementAndGet());

        // Create the Text
        TextDTO textDTO = textMapper.toDto(text);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTextMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(textDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Text in the database
        List<Text> textList = textRepository.findAll();
        assertThat(textList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteText() throws Exception {
        // Initialize the database
        textRepository.saveAndFlush(text);

        int databaseSizeBeforeDelete = textRepository.findAll().size();

        // Delete the text
        restTextMockMvc
            .perform(delete(ENTITY_API_URL_ID, text.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Text> textList = textRepository.findAll();
        assertThat(textList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
