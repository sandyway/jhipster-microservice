package online.kehan.connect.analytic.query.web.rest;

import online.kehan.connect.analytic.query.RedisTestContainerExtension;
import online.kehan.connect.analytic.query.AnalyticQueryServiceApp;
import online.kehan.connect.analytic.query.config.TestSecurityConfiguration;
import online.kehan.connect.analytic.query.domain.ConnectEvent;
import online.kehan.connect.analytic.query.repository.ConnectEventRepository;
import online.kehan.connect.analytic.query.service.ConnectEventService;

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

import static online.kehan.connect.analytic.query.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ConnectEventResource} REST controller.
 */
@SpringBootTest(classes = { AnalyticQueryServiceApp.class, TestSecurityConfiguration.class })
@ExtendWith({ RedisTestContainerExtension.class, MockitoExtension.class })
@AutoConfigureMockMvc
@WithMockUser
public class ConnectEventResourceIT {

    private static final String DEFAULT_ANALYTIC_ID = "AAAAAAAAAA";
    private static final String UPDATED_ANALYTIC_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CONNECT_EVENT = "AAAAAAAAAA";
    private static final String UPDATED_CONNECT_EVENT = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ConnectEventRepository connectEventRepository;

    @Autowired
    private ConnectEventService connectEventService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConnectEventMockMvc;

    private ConnectEvent connectEvent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConnectEvent createEntity(EntityManager em) {
        ConnectEvent connectEvent = new ConnectEvent()
            .analyticId(DEFAULT_ANALYTIC_ID)
            .connectEvent(DEFAULT_CONNECT_EVENT)
            .reference(DEFAULT_REFERENCE)
            .createdDate(DEFAULT_CREATED_DATE);
        return connectEvent;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConnectEvent createUpdatedEntity(EntityManager em) {
        ConnectEvent connectEvent = new ConnectEvent()
            .analyticId(UPDATED_ANALYTIC_ID)
            .connectEvent(UPDATED_CONNECT_EVENT)
            .reference(UPDATED_REFERENCE)
            .createdDate(UPDATED_CREATED_DATE);
        return connectEvent;
    }

    @BeforeEach
    public void initTest() {
        connectEvent = createEntity(em);
    }

    @Test
    @Transactional
    public void createConnectEvent() throws Exception {
        int databaseSizeBeforeCreate = connectEventRepository.findAll().size();
        // Create the ConnectEvent
        restConnectEventMockMvc.perform(post("/api/connect-events").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(connectEvent)))
            .andExpect(status().isCreated());

        // Validate the ConnectEvent in the database
        List<ConnectEvent> connectEventList = connectEventRepository.findAll();
        assertThat(connectEventList).hasSize(databaseSizeBeforeCreate + 1);
        ConnectEvent testConnectEvent = connectEventList.get(connectEventList.size() - 1);
        assertThat(testConnectEvent.getAnalyticId()).isEqualTo(DEFAULT_ANALYTIC_ID);
        assertThat(testConnectEvent.getConnectEvent()).isEqualTo(DEFAULT_CONNECT_EVENT);
        assertThat(testConnectEvent.getReference()).isEqualTo(DEFAULT_REFERENCE);
        assertThat(testConnectEvent.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    public void createConnectEventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = connectEventRepository.findAll().size();

        // Create the ConnectEvent with an existing ID
        connectEvent.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConnectEventMockMvc.perform(post("/api/connect-events").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(connectEvent)))
            .andExpect(status().isBadRequest());

        // Validate the ConnectEvent in the database
        List<ConnectEvent> connectEventList = connectEventRepository.findAll();
        assertThat(connectEventList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAnalyticIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = connectEventRepository.findAll().size();
        // set the field null
        connectEvent.setAnalyticId(null);

        // Create the ConnectEvent, which fails.


        restConnectEventMockMvc.perform(post("/api/connect-events").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(connectEvent)))
            .andExpect(status().isBadRequest());

        List<ConnectEvent> connectEventList = connectEventRepository.findAll();
        assertThat(connectEventList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConnectEvents() throws Exception {
        // Initialize the database
        connectEventRepository.saveAndFlush(connectEvent);

        // Get all the connectEventList
        restConnectEventMockMvc.perform(get("/api/connect-events?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(connectEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].analyticId").value(hasItem(DEFAULT_ANALYTIC_ID)))
            .andExpect(jsonPath("$.[*].connectEvent").value(hasItem(DEFAULT_CONNECT_EVENT.toString())))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))));
    }
    
    @Test
    @Transactional
    public void getConnectEvent() throws Exception {
        // Initialize the database
        connectEventRepository.saveAndFlush(connectEvent);

        // Get the connectEvent
        restConnectEventMockMvc.perform(get("/api/connect-events/{id}", connectEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(connectEvent.getId().intValue()))
            .andExpect(jsonPath("$.analyticId").value(DEFAULT_ANALYTIC_ID))
            .andExpect(jsonPath("$.connectEvent").value(DEFAULT_CONNECT_EVENT.toString()))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.toString()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)));
    }
    @Test
    @Transactional
    public void getNonExistingConnectEvent() throws Exception {
        // Get the connectEvent
        restConnectEventMockMvc.perform(get("/api/connect-events/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConnectEvent() throws Exception {
        // Initialize the database
        connectEventService.save(connectEvent);

        int databaseSizeBeforeUpdate = connectEventRepository.findAll().size();

        // Update the connectEvent
        ConnectEvent updatedConnectEvent = connectEventRepository.findById(connectEvent.getId()).get();
        // Disconnect from session so that the updates on updatedConnectEvent are not directly saved in db
        em.detach(updatedConnectEvent);
        updatedConnectEvent
            .analyticId(UPDATED_ANALYTIC_ID)
            .connectEvent(UPDATED_CONNECT_EVENT)
            .reference(UPDATED_REFERENCE)
            .createdDate(UPDATED_CREATED_DATE);

        restConnectEventMockMvc.perform(put("/api/connect-events").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedConnectEvent)))
            .andExpect(status().isOk());

        // Validate the ConnectEvent in the database
        List<ConnectEvent> connectEventList = connectEventRepository.findAll();
        assertThat(connectEventList).hasSize(databaseSizeBeforeUpdate);
        ConnectEvent testConnectEvent = connectEventList.get(connectEventList.size() - 1);
        assertThat(testConnectEvent.getAnalyticId()).isEqualTo(UPDATED_ANALYTIC_ID);
        assertThat(testConnectEvent.getConnectEvent()).isEqualTo(UPDATED_CONNECT_EVENT);
        assertThat(testConnectEvent.getReference()).isEqualTo(UPDATED_REFERENCE);
        assertThat(testConnectEvent.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingConnectEvent() throws Exception {
        int databaseSizeBeforeUpdate = connectEventRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConnectEventMockMvc.perform(put("/api/connect-events").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(connectEvent)))
            .andExpect(status().isBadRequest());

        // Validate the ConnectEvent in the database
        List<ConnectEvent> connectEventList = connectEventRepository.findAll();
        assertThat(connectEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConnectEvent() throws Exception {
        // Initialize the database
        connectEventService.save(connectEvent);

        int databaseSizeBeforeDelete = connectEventRepository.findAll().size();

        // Delete the connectEvent
        restConnectEventMockMvc.perform(delete("/api/connect-events/{id}", connectEvent.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConnectEvent> connectEventList = connectEventRepository.findAll();
        assertThat(connectEventList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
