package online.kehan.connect.flow.web.rest;

import online.kehan.connect.flow.RedisTestContainerExtension;
import online.kehan.connect.flow.FlowServiceApp;
import online.kehan.connect.flow.config.TestSecurityConfiguration;
import online.kehan.connect.flow.domain.ConnectIntent;
import online.kehan.connect.flow.repository.ConnectIntentRepository;
import online.kehan.connect.flow.service.ConnectIntentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
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

import static online.kehan.connect.flow.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ConnectIntentResource} REST controller.
 */
@SpringBootTest(classes = { FlowServiceApp.class, TestSecurityConfiguration.class })
@ExtendWith({ RedisTestContainerExtension.class, MockitoExtension.class })
@AutoConfigureMockMvc
@WithMockUser
public class ConnectIntentResourceIT {

    private static final String DEFAULT_INTENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_INTENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_FLOW_ID = "AAAAAAAAAA";
    private static final String UPDATED_FLOW_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CONNECT_CHANNEL = "AAAAAAAAAA";
    private static final String UPDATED_CONNECT_CHANNEL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CONNECT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CONNECT_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGES = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGES = "BBBBBBBBBB";

    private static final String DEFAULT_REMINDER = "AAAAAAAAAA";
    private static final String UPDATED_REMINDER = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    @Autowired
    private ConnectIntentRepository connectIntentRepository;

    @Autowired
    private ConnectIntentService connectIntentService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConnectIntentMockMvc;

    private ConnectIntent connectIntent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConnectIntent createEntity(EntityManager em) {
        ConnectIntent connectIntent = new ConnectIntent()
            .intentId(DEFAULT_INTENT_ID)
            .flowId(DEFAULT_FLOW_ID)
            .connectChannel(DEFAULT_CONNECT_CHANNEL)
            .description(DEFAULT_DESCRIPTION)
            .connectType(DEFAULT_CONNECT_TYPE)
            .messages(DEFAULT_MESSAGES)
            .reminder(DEFAULT_REMINDER)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .createdBy(DEFAULT_CREATED_BY);
        return connectIntent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConnectIntent createUpdatedEntity(EntityManager em) {
        ConnectIntent connectIntent = new ConnectIntent()
            .intentId(UPDATED_INTENT_ID)
            .flowId(UPDATED_FLOW_ID)
            .connectChannel(UPDATED_CONNECT_CHANNEL)
            .description(UPDATED_DESCRIPTION)
            .connectType(UPDATED_CONNECT_TYPE)
            .messages(UPDATED_MESSAGES)
            .reminder(UPDATED_REMINDER)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .createdBy(UPDATED_CREATED_BY);
        return connectIntent;
    }

    @BeforeEach
    public void initTest() {
        connectIntent = createEntity(em);
    }

    @Test
    @Transactional
    public void createConnectIntent() throws Exception {
        int databaseSizeBeforeCreate = connectIntentRepository.findAll().size();
        // Create the ConnectIntent
        restConnectIntentMockMvc.perform(post("/api/connect-intents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(connectIntent)))
            .andExpect(status().isCreated());

        // Validate the ConnectIntent in the database
        List<ConnectIntent> connectIntentList = connectIntentRepository.findAll();
        assertThat(connectIntentList).hasSize(databaseSizeBeforeCreate + 1);
        ConnectIntent testConnectIntent = connectIntentList.get(connectIntentList.size() - 1);
        assertThat(testConnectIntent.getIntentId()).isEqualTo(DEFAULT_INTENT_ID);
        assertThat(testConnectIntent.getFlowId()).isEqualTo(DEFAULT_FLOW_ID);
        assertThat(testConnectIntent.getConnectChannel()).isEqualTo(DEFAULT_CONNECT_CHANNEL);
        assertThat(testConnectIntent.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testConnectIntent.getConnectType()).isEqualTo(DEFAULT_CONNECT_TYPE);
        assertThat(testConnectIntent.getMessages()).isEqualTo(DEFAULT_MESSAGES);
        assertThat(testConnectIntent.getReminder()).isEqualTo(DEFAULT_REMINDER);
        assertThat(testConnectIntent.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testConnectIntent.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testConnectIntent.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    public void createConnectIntentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = connectIntentRepository.findAll().size();

        // Create the ConnectIntent with an existing ID
        connectIntent.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConnectIntentMockMvc.perform(post("/api/connect-intents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(connectIntent)))
            .andExpect(status().isBadRequest());

        // Validate the ConnectIntent in the database
        List<ConnectIntent> connectIntentList = connectIntentRepository.findAll();
        assertThat(connectIntentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkIntentIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = connectIntentRepository.findAll().size();
        // set the field null
        connectIntent.setIntentId(null);

        // Create the ConnectIntent, which fails.


        restConnectIntentMockMvc.perform(post("/api/connect-intents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(connectIntent)))
            .andExpect(status().isBadRequest());

        List<ConnectIntent> connectIntentList = connectIntentRepository.findAll();
        assertThat(connectIntentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkConnectChannelIsRequired() throws Exception {
        int databaseSizeBeforeTest = connectIntentRepository.findAll().size();
        // set the field null
        connectIntent.setConnectChannel(null);

        // Create the ConnectIntent, which fails.


        restConnectIntentMockMvc.perform(post("/api/connect-intents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(connectIntent)))
            .andExpect(status().isBadRequest());

        List<ConnectIntent> connectIntentList = connectIntentRepository.findAll();
        assertThat(connectIntentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkConnectTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = connectIntentRepository.findAll().size();
        // set the field null
        connectIntent.setConnectType(null);

        // Create the ConnectIntent, which fails.


        restConnectIntentMockMvc.perform(post("/api/connect-intents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(connectIntent)))
            .andExpect(status().isBadRequest());

        List<ConnectIntent> connectIntentList = connectIntentRepository.findAll();
        assertThat(connectIntentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = connectIntentRepository.findAll().size();
        // set the field null
        connectIntent.setCreatedDate(null);

        // Create the ConnectIntent, which fails.


        restConnectIntentMockMvc.perform(post("/api/connect-intents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(connectIntent)))
            .andExpect(status().isBadRequest());

        List<ConnectIntent> connectIntentList = connectIntentRepository.findAll();
        assertThat(connectIntentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = connectIntentRepository.findAll().size();
        // set the field null
        connectIntent.setCreatedBy(null);

        // Create the ConnectIntent, which fails.


        restConnectIntentMockMvc.perform(post("/api/connect-intents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(connectIntent)))
            .andExpect(status().isBadRequest());

        List<ConnectIntent> connectIntentList = connectIntentRepository.findAll();
        assertThat(connectIntentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConnectIntents() throws Exception {
        // Initialize the database
        connectIntentRepository.saveAndFlush(connectIntent);

        // Get all the connectIntentList
        restConnectIntentMockMvc.perform(get("/api/connect-intents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(connectIntent.getId().intValue())))
            .andExpect(jsonPath("$.[*].intentId").value(hasItem(DEFAULT_INTENT_ID)))
            .andExpect(jsonPath("$.[*].flowId").value(hasItem(DEFAULT_FLOW_ID)))
            .andExpect(jsonPath("$.[*].connectChannel").value(hasItem(DEFAULT_CONNECT_CHANNEL)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].connectType").value(hasItem(DEFAULT_CONNECT_TYPE)))
            .andExpect(jsonPath("$.[*].messages").value(hasItem(DEFAULT_MESSAGES.toString())))
            .andExpect(jsonPath("$.[*].reminder").value(hasItem(DEFAULT_REMINDER.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(sameInstant(DEFAULT_UPDATED_DATE))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)));
    }
    
    @Test
    @Transactional
    public void getConnectIntent() throws Exception {
        // Initialize the database
        connectIntentRepository.saveAndFlush(connectIntent);

        // Get the connectIntent
        restConnectIntentMockMvc.perform(get("/api/connect-intents/{id}", connectIntent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(connectIntent.getId().intValue()))
            .andExpect(jsonPath("$.intentId").value(DEFAULT_INTENT_ID))
            .andExpect(jsonPath("$.flowId").value(DEFAULT_FLOW_ID))
            .andExpect(jsonPath("$.connectChannel").value(DEFAULT_CONNECT_CHANNEL))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.connectType").value(DEFAULT_CONNECT_TYPE))
            .andExpect(jsonPath("$.messages").value(DEFAULT_MESSAGES.toString()))
            .andExpect(jsonPath("$.reminder").value(DEFAULT_REMINDER.toString()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.updatedDate").value(sameInstant(DEFAULT_UPDATED_DATE)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY));
    }
    @Test
    @Transactional
    public void getNonExistingConnectIntent() throws Exception {
        // Get the connectIntent
        restConnectIntentMockMvc.perform(get("/api/connect-intents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConnectIntent() throws Exception {
        // Initialize the database
        connectIntentService.save(connectIntent);

        int databaseSizeBeforeUpdate = connectIntentRepository.findAll().size();

        // Update the connectIntent
        ConnectIntent updatedConnectIntent = connectIntentRepository.findById(connectIntent.getId()).get();
        // Disconnect from session so that the updates on updatedConnectIntent are not directly saved in db
        em.detach(updatedConnectIntent);
        updatedConnectIntent
            .intentId(UPDATED_INTENT_ID)
            .flowId(UPDATED_FLOW_ID)
            .connectChannel(UPDATED_CONNECT_CHANNEL)
            .description(UPDATED_DESCRIPTION)
            .connectType(UPDATED_CONNECT_TYPE)
            .messages(UPDATED_MESSAGES)
            .reminder(UPDATED_REMINDER)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .createdBy(UPDATED_CREATED_BY);

        restConnectIntentMockMvc.perform(put("/api/connect-intents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedConnectIntent)))
            .andExpect(status().isOk());

        // Validate the ConnectIntent in the database
        List<ConnectIntent> connectIntentList = connectIntentRepository.findAll();
        assertThat(connectIntentList).hasSize(databaseSizeBeforeUpdate);
        ConnectIntent testConnectIntent = connectIntentList.get(connectIntentList.size() - 1);
        assertThat(testConnectIntent.getIntentId()).isEqualTo(UPDATED_INTENT_ID);
        assertThat(testConnectIntent.getFlowId()).isEqualTo(UPDATED_FLOW_ID);
        assertThat(testConnectIntent.getConnectChannel()).isEqualTo(UPDATED_CONNECT_CHANNEL);
        assertThat(testConnectIntent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testConnectIntent.getConnectType()).isEqualTo(UPDATED_CONNECT_TYPE);
        assertThat(testConnectIntent.getMessages()).isEqualTo(UPDATED_MESSAGES);
        assertThat(testConnectIntent.getReminder()).isEqualTo(UPDATED_REMINDER);
        assertThat(testConnectIntent.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testConnectIntent.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testConnectIntent.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingConnectIntent() throws Exception {
        int databaseSizeBeforeUpdate = connectIntentRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConnectIntentMockMvc.perform(put("/api/connect-intents").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(connectIntent)))
            .andExpect(status().isBadRequest());

        // Validate the ConnectIntent in the database
        List<ConnectIntent> connectIntentList = connectIntentRepository.findAll();
        assertThat(connectIntentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConnectIntent() throws Exception {
        // Initialize the database
        connectIntentService.save(connectIntent);

        int databaseSizeBeforeDelete = connectIntentRepository.findAll().size();

        // Delete the connectIntent
        restConnectIntentMockMvc.perform(delete("/api/connect-intents/{id}", connectIntent.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConnectIntent> connectIntentList = connectIntentRepository.findAll();
        assertThat(connectIntentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
