package online.kehan.connect.analytic.collection.web.rest;

import online.kehan.connect.analytic.collection.AnalyticCollectionServiceApp;
import online.kehan.connect.analytic.collection.config.TestSecurityConfiguration;
import online.kehan.connect.analytic.collection.domain.State;
import online.kehan.connect.analytic.collection.repository.StateRepository;
import online.kehan.connect.analytic.collection.service.StateService;

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
 * Integration tests for the {@link StateResource} REST controller.
 */
@SpringBootTest(classes = { AnalyticCollectionServiceApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class StateResourceIT {

    private static final UUID DEFAULT_CAMPAIGN_ID = UUID.randomUUID();
    private static final UUID UPDATED_CAMPAIGN_ID = UUID.randomUUID();

    private static final UUID DEFAULT_RECIPIENT_ID = UUID.randomUUID();
    private static final UUID UPDATED_RECIPIENT_ID = UUID.randomUUID();

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    private static final String DEFAULT_CHANNEL = "AAAAAAAAAA";
    private static final String UPDATED_CHANNEL = "BBBBBBBBBB";

    private static final String DEFAULT_INTENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_INTENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_INTENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_INTENT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_EVENT = "AAAAAAAAAA";
    private static final String UPDATED_EVENT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private StateService stateService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStateMockMvc;

    private State state;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static State createEntity(EntityManager em) {
        State state = new State()
            .campaignId(DEFAULT_CAMPAIGN_ID)
            .recipientId(DEFAULT_RECIPIENT_ID)
            .details(DEFAULT_DETAILS)
            .channel(DEFAULT_CHANNEL)
            .intentId(DEFAULT_INTENT_ID)
            .intentType(DEFAULT_INTENT_TYPE)
            .event(DEFAULT_EVENT)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return state;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static State createUpdatedEntity(EntityManager em) {
        State state = new State()
            .campaignId(UPDATED_CAMPAIGN_ID)
            .recipientId(UPDATED_RECIPIENT_ID)
            .details(UPDATED_DETAILS)
            .channel(UPDATED_CHANNEL)
            .intentId(UPDATED_INTENT_ID)
            .intentType(UPDATED_INTENT_TYPE)
            .event(UPDATED_EVENT)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        return state;
    }

    @BeforeEach
    public void initTest() {
        state = createEntity(em);
    }

    @Test
    @Transactional
    public void createState() throws Exception {
        int databaseSizeBeforeCreate = stateRepository.findAll().size();
        // Create the State
        restStateMockMvc.perform(post("/api/states").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(state)))
            .andExpect(status().isCreated());

        // Validate the State in the database
        List<State> stateList = stateRepository.findAll();
        assertThat(stateList).hasSize(databaseSizeBeforeCreate + 1);
        State testState = stateList.get(stateList.size() - 1);
        assertThat(testState.getCampaignId()).isEqualTo(DEFAULT_CAMPAIGN_ID);
        assertThat(testState.getRecipientId()).isEqualTo(DEFAULT_RECIPIENT_ID);
        assertThat(testState.getDetails()).isEqualTo(DEFAULT_DETAILS);
        assertThat(testState.getChannel()).isEqualTo(DEFAULT_CHANNEL);
        assertThat(testState.getIntentId()).isEqualTo(DEFAULT_INTENT_ID);
        assertThat(testState.getIntentType()).isEqualTo(DEFAULT_INTENT_TYPE);
        assertThat(testState.getEvent()).isEqualTo(DEFAULT_EVENT);
        assertThat(testState.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testState.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void createStateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stateRepository.findAll().size();

        // Create the State with an existing ID
        state.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStateMockMvc.perform(post("/api/states").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(state)))
            .andExpect(status().isBadRequest());

        // Validate the State in the database
        List<State> stateList = stateRepository.findAll();
        assertThat(stateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCampaignIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = stateRepository.findAll().size();
        // set the field null
        state.setCampaignId(null);

        // Create the State, which fails.


        restStateMockMvc.perform(post("/api/states").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(state)))
            .andExpect(status().isBadRequest());

        List<State> stateList = stateRepository.findAll();
        assertThat(stateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRecipientIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = stateRepository.findAll().size();
        // set the field null
        state.setRecipientId(null);

        // Create the State, which fails.


        restStateMockMvc.perform(post("/api/states").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(state)))
            .andExpect(status().isBadRequest());

        List<State> stateList = stateRepository.findAll();
        assertThat(stateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkChannelIsRequired() throws Exception {
        int databaseSizeBeforeTest = stateRepository.findAll().size();
        // set the field null
        state.setChannel(null);

        // Create the State, which fails.


        restStateMockMvc.perform(post("/api/states").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(state)))
            .andExpect(status().isBadRequest());

        List<State> stateList = stateRepository.findAll();
        assertThat(stateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = stateRepository.findAll().size();
        // set the field null
        state.setCreatedDate(null);

        // Create the State, which fails.


        restStateMockMvc.perform(post("/api/states").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(state)))
            .andExpect(status().isBadRequest());

        List<State> stateList = stateRepository.findAll();
        assertThat(stateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStates() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);

        // Get all the stateList
        restStateMockMvc.perform(get("/api/states?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(state.getId().intValue())))
            .andExpect(jsonPath("$.[*].campaignId").value(hasItem(DEFAULT_CAMPAIGN_ID.toString())))
            .andExpect(jsonPath("$.[*].recipientId").value(hasItem(DEFAULT_RECIPIENT_ID.toString())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].channel").value(hasItem(DEFAULT_CHANNEL)))
            .andExpect(jsonPath("$.[*].intentId").value(hasItem(DEFAULT_INTENT_ID)))
            .andExpect(jsonPath("$.[*].intentType").value(hasItem(DEFAULT_INTENT_TYPE)))
            .andExpect(jsonPath("$.[*].event").value(hasItem(DEFAULT_EVENT.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(sameInstant(DEFAULT_UPDATED_DATE))));
    }
    
    @Test
    @Transactional
    public void getState() throws Exception {
        // Initialize the database
        stateRepository.saveAndFlush(state);

        // Get the state
        restStateMockMvc.perform(get("/api/states/{id}", state.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(state.getId().intValue()))
            .andExpect(jsonPath("$.campaignId").value(DEFAULT_CAMPAIGN_ID.toString()))
            .andExpect(jsonPath("$.recipientId").value(DEFAULT_RECIPIENT_ID.toString()))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS.toString()))
            .andExpect(jsonPath("$.channel").value(DEFAULT_CHANNEL))
            .andExpect(jsonPath("$.intentId").value(DEFAULT_INTENT_ID))
            .andExpect(jsonPath("$.intentType").value(DEFAULT_INTENT_TYPE))
            .andExpect(jsonPath("$.event").value(DEFAULT_EVENT.toString()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.updatedDate").value(sameInstant(DEFAULT_UPDATED_DATE)));
    }
    @Test
    @Transactional
    public void getNonExistingState() throws Exception {
        // Get the state
        restStateMockMvc.perform(get("/api/states/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateState() throws Exception {
        // Initialize the database
        stateService.save(state);

        int databaseSizeBeforeUpdate = stateRepository.findAll().size();

        // Update the state
        State updatedState = stateRepository.findById(state.getId()).get();
        // Disconnect from session so that the updates on updatedState are not directly saved in db
        em.detach(updatedState);
        updatedState
            .campaignId(UPDATED_CAMPAIGN_ID)
            .recipientId(UPDATED_RECIPIENT_ID)
            .details(UPDATED_DETAILS)
            .channel(UPDATED_CHANNEL)
            .intentId(UPDATED_INTENT_ID)
            .intentType(UPDATED_INTENT_TYPE)
            .event(UPDATED_EVENT)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);

        restStateMockMvc.perform(put("/api/states").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedState)))
            .andExpect(status().isOk());

        // Validate the State in the database
        List<State> stateList = stateRepository.findAll();
        assertThat(stateList).hasSize(databaseSizeBeforeUpdate);
        State testState = stateList.get(stateList.size() - 1);
        assertThat(testState.getCampaignId()).isEqualTo(UPDATED_CAMPAIGN_ID);
        assertThat(testState.getRecipientId()).isEqualTo(UPDATED_RECIPIENT_ID);
        assertThat(testState.getDetails()).isEqualTo(UPDATED_DETAILS);
        assertThat(testState.getChannel()).isEqualTo(UPDATED_CHANNEL);
        assertThat(testState.getIntentId()).isEqualTo(UPDATED_INTENT_ID);
        assertThat(testState.getIntentType()).isEqualTo(UPDATED_INTENT_TYPE);
        assertThat(testState.getEvent()).isEqualTo(UPDATED_EVENT);
        assertThat(testState.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testState.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingState() throws Exception {
        int databaseSizeBeforeUpdate = stateRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStateMockMvc.perform(put("/api/states").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(state)))
            .andExpect(status().isBadRequest());

        // Validate the State in the database
        List<State> stateList = stateRepository.findAll();
        assertThat(stateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteState() throws Exception {
        // Initialize the database
        stateService.save(state);

        int databaseSizeBeforeDelete = stateRepository.findAll().size();

        // Delete the state
        restStateMockMvc.perform(delete("/api/states/{id}", state.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<State> stateList = stateRepository.findAll();
        assertThat(stateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
