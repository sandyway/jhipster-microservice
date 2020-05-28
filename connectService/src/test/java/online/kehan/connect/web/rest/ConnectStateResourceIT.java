package online.kehan.connect.web.rest;

import online.kehan.connect.RedisTestContainerExtension;
import online.kehan.connect.ConnectServiceApp;
import online.kehan.connect.config.TestSecurityConfiguration;
import online.kehan.connect.domain.ConnectState;
import online.kehan.connect.repository.ConnectStateRepository;
import online.kehan.connect.service.ConnectStateService;
import online.kehan.connect.service.dto.ConnectStateDTO;
import online.kehan.connect.service.mapper.ConnectStateMapper;

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

import static online.kehan.connect.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import online.kehan.connect.domain.enumeration.Channel;
import online.kehan.connect.domain.enumeration.IntentTypes;
/**
 * Integration tests for the {@link ConnectStateResource} REST controller.
 */
@SpringBootTest(classes = { ConnectServiceApp.class, TestSecurityConfiguration.class })
@ExtendWith({ RedisTestContainerExtension.class, MockitoExtension.class })
@AutoConfigureMockMvc
@WithMockUser
public class ConnectStateResourceIT {

    private static final String DEFAULT_CAMPAIGN_ID = "AAAAAAAAAA";
    private static final String UPDATED_CAMPAIGN_ID = "BBBBBBBBBB";

    private static final String DEFAULT_RECIPIENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_RECIPIENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CONNECT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_CONNECT_DETAILS = "BBBBBBBBBB";

    private static final Channel DEFAULT_CHANNEL = Channel.SMS;
    private static final Channel UPDATED_CHANNEL = Channel.MESSENGER;

    private static final String DEFAULT_INTENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_INTENT_ID = "BBBBBBBBBB";

    private static final IntentTypes DEFAULT_INTENT_TYPE = IntentTypes.BROADCAST;
    private static final IntentTypes UPDATED_INTENT_TYPE = IntentTypes.START;

    private static final String DEFAULT_CONNECT_EVENT = "AAAAAAAAAA";
    private static final String UPDATED_CONNECT_EVENT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ConnectStateRepository connectStateRepository;

    @Autowired
    private ConnectStateMapper connectStateMapper;

    @Autowired
    private ConnectStateService connectStateService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConnectStateMockMvc;

    private ConnectState connectState;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConnectState createEntity(EntityManager em) {
        ConnectState connectState = new ConnectState()
            .campaignId(DEFAULT_CAMPAIGN_ID)
            .recipientId(DEFAULT_RECIPIENT_ID)
            .connectDetails(DEFAULT_CONNECT_DETAILS)
            .channel(DEFAULT_CHANNEL)
            .intentId(DEFAULT_INTENT_ID)
            .intentType(DEFAULT_INTENT_TYPE)
            .connectEvent(DEFAULT_CONNECT_EVENT)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return connectState;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConnectState createUpdatedEntity(EntityManager em) {
        ConnectState connectState = new ConnectState()
            .campaignId(UPDATED_CAMPAIGN_ID)
            .recipientId(UPDATED_RECIPIENT_ID)
            .connectDetails(UPDATED_CONNECT_DETAILS)
            .channel(UPDATED_CHANNEL)
            .intentId(UPDATED_INTENT_ID)
            .intentType(UPDATED_INTENT_TYPE)
            .connectEvent(UPDATED_CONNECT_EVENT)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        return connectState;
    }

    @BeforeEach
    public void initTest() {
        connectState = createEntity(em);
    }

    @Test
    @Transactional
    public void createConnectState() throws Exception {
        int databaseSizeBeforeCreate = connectStateRepository.findAll().size();
        // Create the ConnectState
        ConnectStateDTO connectStateDTO = connectStateMapper.toDto(connectState);
        restConnectStateMockMvc.perform(post("/api/connect-states").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(connectStateDTO)))
            .andExpect(status().isCreated());

        // Validate the ConnectState in the database
        List<ConnectState> connectStateList = connectStateRepository.findAll();
        assertThat(connectStateList).hasSize(databaseSizeBeforeCreate + 1);
        ConnectState testConnectState = connectStateList.get(connectStateList.size() - 1);
        assertThat(testConnectState.getCampaignId()).isEqualTo(DEFAULT_CAMPAIGN_ID);
        assertThat(testConnectState.getRecipientId()).isEqualTo(DEFAULT_RECIPIENT_ID);
        assertThat(testConnectState.getConnectDetails()).isEqualTo(DEFAULT_CONNECT_DETAILS);
        assertThat(testConnectState.getChannel()).isEqualTo(DEFAULT_CHANNEL);
        assertThat(testConnectState.getIntentId()).isEqualTo(DEFAULT_INTENT_ID);
        assertThat(testConnectState.getIntentType()).isEqualTo(DEFAULT_INTENT_TYPE);
        assertThat(testConnectState.getConnectEvent()).isEqualTo(DEFAULT_CONNECT_EVENT);
        assertThat(testConnectState.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testConnectState.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void createConnectStateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = connectStateRepository.findAll().size();

        // Create the ConnectState with an existing ID
        connectState.setId(1L);
        ConnectStateDTO connectStateDTO = connectStateMapper.toDto(connectState);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConnectStateMockMvc.perform(post("/api/connect-states").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(connectStateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConnectState in the database
        List<ConnectState> connectStateList = connectStateRepository.findAll();
        assertThat(connectStateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCampaignIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = connectStateRepository.findAll().size();
        // set the field null
        connectState.setCampaignId(null);

        // Create the ConnectState, which fails.
        ConnectStateDTO connectStateDTO = connectStateMapper.toDto(connectState);


        restConnectStateMockMvc.perform(post("/api/connect-states").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(connectStateDTO)))
            .andExpect(status().isBadRequest());

        List<ConnectState> connectStateList = connectStateRepository.findAll();
        assertThat(connectStateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRecipientIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = connectStateRepository.findAll().size();
        // set the field null
        connectState.setRecipientId(null);

        // Create the ConnectState, which fails.
        ConnectStateDTO connectStateDTO = connectStateMapper.toDto(connectState);


        restConnectStateMockMvc.perform(post("/api/connect-states").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(connectStateDTO)))
            .andExpect(status().isBadRequest());

        List<ConnectState> connectStateList = connectStateRepository.findAll();
        assertThat(connectStateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkChannelIsRequired() throws Exception {
        int databaseSizeBeforeTest = connectStateRepository.findAll().size();
        // set the field null
        connectState.setChannel(null);

        // Create the ConnectState, which fails.
        ConnectStateDTO connectStateDTO = connectStateMapper.toDto(connectState);


        restConnectStateMockMvc.perform(post("/api/connect-states").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(connectStateDTO)))
            .andExpect(status().isBadRequest());

        List<ConnectState> connectStateList = connectStateRepository.findAll();
        assertThat(connectStateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = connectStateRepository.findAll().size();
        // set the field null
        connectState.setCreatedDate(null);

        // Create the ConnectState, which fails.
        ConnectStateDTO connectStateDTO = connectStateMapper.toDto(connectState);


        restConnectStateMockMvc.perform(post("/api/connect-states").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(connectStateDTO)))
            .andExpect(status().isBadRequest());

        List<ConnectState> connectStateList = connectStateRepository.findAll();
        assertThat(connectStateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConnectStates() throws Exception {
        // Initialize the database
        connectStateRepository.saveAndFlush(connectState);

        // Get all the connectStateList
        restConnectStateMockMvc.perform(get("/api/connect-states?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(connectState.getId().intValue())))
            .andExpect(jsonPath("$.[*].campaignId").value(hasItem(DEFAULT_CAMPAIGN_ID)))
            .andExpect(jsonPath("$.[*].recipientId").value(hasItem(DEFAULT_RECIPIENT_ID)))
            .andExpect(jsonPath("$.[*].connectDetails").value(hasItem(DEFAULT_CONNECT_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].channel").value(hasItem(DEFAULT_CHANNEL.toString())))
            .andExpect(jsonPath("$.[*].intentId").value(hasItem(DEFAULT_INTENT_ID)))
            .andExpect(jsonPath("$.[*].intentType").value(hasItem(DEFAULT_INTENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].connectEvent").value(hasItem(DEFAULT_CONNECT_EVENT.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(sameInstant(DEFAULT_UPDATED_DATE))));
    }
    
    @Test
    @Transactional
    public void getConnectState() throws Exception {
        // Initialize the database
        connectStateRepository.saveAndFlush(connectState);

        // Get the connectState
        restConnectStateMockMvc.perform(get("/api/connect-states/{id}", connectState.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(connectState.getId().intValue()))
            .andExpect(jsonPath("$.campaignId").value(DEFAULT_CAMPAIGN_ID))
            .andExpect(jsonPath("$.recipientId").value(DEFAULT_RECIPIENT_ID))
            .andExpect(jsonPath("$.connectDetails").value(DEFAULT_CONNECT_DETAILS.toString()))
            .andExpect(jsonPath("$.channel").value(DEFAULT_CHANNEL.toString()))
            .andExpect(jsonPath("$.intentId").value(DEFAULT_INTENT_ID))
            .andExpect(jsonPath("$.intentType").value(DEFAULT_INTENT_TYPE.toString()))
            .andExpect(jsonPath("$.connectEvent").value(DEFAULT_CONNECT_EVENT.toString()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.updatedDate").value(sameInstant(DEFAULT_UPDATED_DATE)));
    }
    @Test
    @Transactional
    public void getNonExistingConnectState() throws Exception {
        // Get the connectState
        restConnectStateMockMvc.perform(get("/api/connect-states/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConnectState() throws Exception {
        // Initialize the database
        connectStateRepository.saveAndFlush(connectState);

        int databaseSizeBeforeUpdate = connectStateRepository.findAll().size();

        // Update the connectState
        ConnectState updatedConnectState = connectStateRepository.findById(connectState.getId()).get();
        // Disconnect from session so that the updates on updatedConnectState are not directly saved in db
        em.detach(updatedConnectState);
        updatedConnectState
            .campaignId(UPDATED_CAMPAIGN_ID)
            .recipientId(UPDATED_RECIPIENT_ID)
            .connectDetails(UPDATED_CONNECT_DETAILS)
            .channel(UPDATED_CHANNEL)
            .intentId(UPDATED_INTENT_ID)
            .intentType(UPDATED_INTENT_TYPE)
            .connectEvent(UPDATED_CONNECT_EVENT)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        ConnectStateDTO connectStateDTO = connectStateMapper.toDto(updatedConnectState);

        restConnectStateMockMvc.perform(put("/api/connect-states").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(connectStateDTO)))
            .andExpect(status().isOk());

        // Validate the ConnectState in the database
        List<ConnectState> connectStateList = connectStateRepository.findAll();
        assertThat(connectStateList).hasSize(databaseSizeBeforeUpdate);
        ConnectState testConnectState = connectStateList.get(connectStateList.size() - 1);
        assertThat(testConnectState.getCampaignId()).isEqualTo(UPDATED_CAMPAIGN_ID);
        assertThat(testConnectState.getRecipientId()).isEqualTo(UPDATED_RECIPIENT_ID);
        assertThat(testConnectState.getConnectDetails()).isEqualTo(UPDATED_CONNECT_DETAILS);
        assertThat(testConnectState.getChannel()).isEqualTo(UPDATED_CHANNEL);
        assertThat(testConnectState.getIntentId()).isEqualTo(UPDATED_INTENT_ID);
        assertThat(testConnectState.getIntentType()).isEqualTo(UPDATED_INTENT_TYPE);
        assertThat(testConnectState.getConnectEvent()).isEqualTo(UPDATED_CONNECT_EVENT);
        assertThat(testConnectState.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testConnectState.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingConnectState() throws Exception {
        int databaseSizeBeforeUpdate = connectStateRepository.findAll().size();

        // Create the ConnectState
        ConnectStateDTO connectStateDTO = connectStateMapper.toDto(connectState);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConnectStateMockMvc.perform(put("/api/connect-states").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(connectStateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConnectState in the database
        List<ConnectState> connectStateList = connectStateRepository.findAll();
        assertThat(connectStateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConnectState() throws Exception {
        // Initialize the database
        connectStateRepository.saveAndFlush(connectState);

        int databaseSizeBeforeDelete = connectStateRepository.findAll().size();

        // Delete the connectState
        restConnectStateMockMvc.perform(delete("/api/connect-states/{id}", connectState.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConnectState> connectStateList = connectStateRepository.findAll();
        assertThat(connectStateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
