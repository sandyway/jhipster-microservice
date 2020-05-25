package online.kehan.connect.analytic.collection.web.rest;

import online.kehan.connect.analytic.collection.AnalyticCollectionServiceApp;
import online.kehan.connect.analytic.collection.config.TestSecurityConfiguration;
import online.kehan.connect.analytic.collection.domain.Intent;
import online.kehan.connect.analytic.collection.repository.IntentRepository;
import online.kehan.connect.analytic.collection.service.IntentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static online.kehan.connect.analytic.collection.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link IntentResource} REST controller.
 */
@SpringBootTest(classes = { AnalyticCollectionServiceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class IntentResourceIT {

    private static final String DEFAULT_INTENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_INTENT_ID = "BBBBBBBBBB";

    private static final UUID DEFAULT_FLOW_ID = UUID.randomUUID();
    private static final UUID UPDATED_FLOW_ID = UUID.randomUUID();

    private static final String DEFAULT_CHANNEL = "AAAAAAAAAA";
    private static final String UPDATED_CHANNEL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGES = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGES = "BBBBBBBBBB";

    private static final String DEFAULT_REMINDER = "AAAAAAAAAA";
    private static final String UPDATED_REMINDER = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final UUID DEFAULT_CREATED_BY = UUID.randomUUID();
    private static final UUID UPDATED_CREATED_BY = UUID.randomUUID();

    @Autowired
    private IntentRepository intentRepository;

    @Autowired
    private IntentService intentService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIntentMockMvc;

    private Intent intent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Intent createEntity(EntityManager em) {
        Intent intent = new Intent()
            .intentId(DEFAULT_INTENT_ID)
            .flowId(DEFAULT_FLOW_ID)
            .channel(DEFAULT_CHANNEL)
            .description(DEFAULT_DESCRIPTION)
            .type(DEFAULT_TYPE)
            .messages(DEFAULT_MESSAGES)
            .reminder(DEFAULT_REMINDER)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .createdBy(DEFAULT_CREATED_BY);
        return intent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Intent createUpdatedEntity(EntityManager em) {
        Intent intent = new Intent()
            .intentId(UPDATED_INTENT_ID)
            .flowId(UPDATED_FLOW_ID)
            .channel(UPDATED_CHANNEL)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .messages(UPDATED_MESSAGES)
            .reminder(UPDATED_REMINDER)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .createdBy(UPDATED_CREATED_BY);
        return intent;
    }

    @BeforeEach
    public void initTest() {
        intent = createEntity(em);
    }

    @Test
    @Transactional
    public void createIntent() throws Exception {
        int databaseSizeBeforeCreate = intentRepository.findAll().size();
        // Create the Intent
        restIntentMockMvc.perform(post("/api/intents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(intent)))
            .andExpect(status().isCreated());

        // Validate the Intent in the database
        List<Intent> intentList = intentRepository.findAll();
        assertThat(intentList).hasSize(databaseSizeBeforeCreate + 1);
        Intent testIntent = intentList.get(intentList.size() - 1);
        assertThat(testIntent.getIntentId()).isEqualTo(DEFAULT_INTENT_ID);
        assertThat(testIntent.getFlowId()).isEqualTo(DEFAULT_FLOW_ID);
        assertThat(testIntent.getChannel()).isEqualTo(DEFAULT_CHANNEL);
        assertThat(testIntent.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testIntent.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testIntent.getMessages()).isEqualTo(DEFAULT_MESSAGES);
        assertThat(testIntent.getReminder()).isEqualTo(DEFAULT_REMINDER);
        assertThat(testIntent.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testIntent.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testIntent.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    public void createIntentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = intentRepository.findAll().size();

        // Create the Intent with an existing ID
        intent.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIntentMockMvc.perform(post("/api/intents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(intent)))
            .andExpect(status().isBadRequest());

        // Validate the Intent in the database
        List<Intent> intentList = intentRepository.findAll();
        assertThat(intentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIntentIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = intentRepository.findAll().size();
        // set the field null
        intent.setIntentId(null);

        // Create the Intent, which fails.


        restIntentMockMvc.perform(post("/api/intents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(intent)))
            .andExpect(status().isBadRequest());

        List<Intent> intentList = intentRepository.findAll();
        assertThat(intentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkChannelIsRequired() throws Exception {
        int databaseSizeBeforeTest = intentRepository.findAll().size();
        // set the field null
        intent.setChannel(null);

        // Create the Intent, which fails.


        restIntentMockMvc.perform(post("/api/intents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(intent)))
            .andExpect(status().isBadRequest());

        List<Intent> intentList = intentRepository.findAll();
        assertThat(intentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = intentRepository.findAll().size();
        // set the field null
        intent.setType(null);

        // Create the Intent, which fails.


        restIntentMockMvc.perform(post("/api/intents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(intent)))
            .andExpect(status().isBadRequest());

        List<Intent> intentList = intentRepository.findAll();
        assertThat(intentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = intentRepository.findAll().size();
        // set the field null
        intent.setCreatedDate(null);

        // Create the Intent, which fails.


        restIntentMockMvc.perform(post("/api/intents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(intent)))
            .andExpect(status().isBadRequest());

        List<Intent> intentList = intentRepository.findAll();
        assertThat(intentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = intentRepository.findAll().size();
        // set the field null
        intent.setCreatedBy(null);

        // Create the Intent, which fails.


        restIntentMockMvc.perform(post("/api/intents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(intent)))
            .andExpect(status().isBadRequest());

        List<Intent> intentList = intentRepository.findAll();
        assertThat(intentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllIntents() throws Exception {
        // Initialize the database
        intentRepository.saveAndFlush(intent);

        // Get all the intentList
        restIntentMockMvc.perform(get("/api/intents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(intent.getId().intValue())))
            .andExpect(jsonPath("$.[*].intentId").value(hasItem(DEFAULT_INTENT_ID)))
            .andExpect(jsonPath("$.[*].flowId").value(hasItem(DEFAULT_FLOW_ID.toString())))
            .andExpect(jsonPath("$.[*].channel").value(hasItem(DEFAULT_CHANNEL)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].messages").value(hasItem(DEFAULT_MESSAGES.toString())))
            .andExpect(jsonPath("$.[*].reminder").value(hasItem(DEFAULT_REMINDER.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(sameInstant(DEFAULT_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())));
    }
    
    @Test
    @Transactional
    public void getIntent() throws Exception {
        // Initialize the database
        intentRepository.saveAndFlush(intent);

        // Get the intent
        restIntentMockMvc.perform(get("/api/intents/{id}", intent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(intent.getId().intValue()))
            .andExpect(jsonPath("$.intentId").value(DEFAULT_INTENT_ID))
            .andExpect(jsonPath("$.flowId").value(DEFAULT_FLOW_ID.toString()))
            .andExpect(jsonPath("$.channel").value(DEFAULT_CHANNEL))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.messages").value(DEFAULT_MESSAGES.toString()))
            .andExpect(jsonPath("$.reminder").value(DEFAULT_REMINDER.toString()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.updatedDate").value(sameInstant(DEFAULT_UPDATED_DATE)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingIntent() throws Exception {
        // Get the intent
        restIntentMockMvc.perform(get("/api/intents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIntent() throws Exception {
        // Initialize the database
        intentService.save(intent);

        int databaseSizeBeforeUpdate = intentRepository.findAll().size();

        // Update the intent
        Intent updatedIntent = intentRepository.findById(intent.getId()).get();
        // Disconnect from session so that the updates on updatedIntent are not directly saved in db
        em.detach(updatedIntent);
        updatedIntent
            .intentId(UPDATED_INTENT_ID)
            .flowId(UPDATED_FLOW_ID)
            .channel(UPDATED_CHANNEL)
            .description(UPDATED_DESCRIPTION)
            .type(UPDATED_TYPE)
            .messages(UPDATED_MESSAGES)
            .reminder(UPDATED_REMINDER)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .createdBy(UPDATED_CREATED_BY);

        restIntentMockMvc.perform(put("/api/intents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedIntent)))
            .andExpect(status().isOk());

        // Validate the Intent in the database
        List<Intent> intentList = intentRepository.findAll();
        assertThat(intentList).hasSize(databaseSizeBeforeUpdate);
        Intent testIntent = intentList.get(intentList.size() - 1);
        assertThat(testIntent.getIntentId()).isEqualTo(UPDATED_INTENT_ID);
        assertThat(testIntent.getFlowId()).isEqualTo(UPDATED_FLOW_ID);
        assertThat(testIntent.getChannel()).isEqualTo(UPDATED_CHANNEL);
        assertThat(testIntent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testIntent.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testIntent.getMessages()).isEqualTo(UPDATED_MESSAGES);
        assertThat(testIntent.getReminder()).isEqualTo(UPDATED_REMINDER);
        assertThat(testIntent.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testIntent.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testIntent.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingIntent() throws Exception {
        int databaseSizeBeforeUpdate = intentRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIntentMockMvc.perform(put("/api/intents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(intent)))
            .andExpect(status().isBadRequest());

        // Validate the Intent in the database
        List<Intent> intentList = intentRepository.findAll();
        assertThat(intentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteIntent() throws Exception {
        // Initialize the database
        intentService.save(intent);

        int databaseSizeBeforeDelete = intentRepository.findAll().size();

        // Delete the intent
        restIntentMockMvc.perform(delete("/api/intents/{id}", intent.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Intent> intentList = intentRepository.findAll();
        assertThat(intentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
