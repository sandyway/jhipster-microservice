package online.kehan.connect.flow.web.rest;

import online.kehan.connect.flow.RedisTestContainerExtension;
import online.kehan.connect.flow.FlowServiceApp;
import online.kehan.connect.flow.config.TestSecurityConfiguration;
import online.kehan.connect.flow.domain.TemplateFlow;
import online.kehan.connect.flow.repository.TemplateFlowRepository;
import online.kehan.connect.flow.service.TemplateFlowService;

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
 * Integration tests for the {@link TemplateFlowResource} REST controller.
 */
@SpringBootTest(classes = { FlowServiceApp.class, TestSecurityConfiguration.class })
@ExtendWith({ RedisTestContainerExtension.class, MockitoExtension.class })
@AutoConfigureMockMvc
@WithMockUser
public class TemplateFlowResourceIT {

    private static final String DEFAULT_CONNECT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_CONNECT_DETAILS = "BBBBBBBBBB";

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private TemplateFlowRepository templateFlowRepository;

    @Autowired
    private TemplateFlowService templateFlowService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTemplateFlowMockMvc;

    private TemplateFlow templateFlow;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TemplateFlow createEntity(EntityManager em) {
        TemplateFlow templateFlow = new TemplateFlow()
            .connectDetails(DEFAULT_CONNECT_DETAILS)
            .userId(DEFAULT_USER_ID)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE);
        return templateFlow;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TemplateFlow createUpdatedEntity(EntityManager em) {
        TemplateFlow templateFlow = new TemplateFlow()
            .connectDetails(UPDATED_CONNECT_DETAILS)
            .userId(UPDATED_USER_ID)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE);
        return templateFlow;
    }

    @BeforeEach
    public void initTest() {
        templateFlow = createEntity(em);
    }

    @Test
    @Transactional
    public void createTemplateFlow() throws Exception {
        int databaseSizeBeforeCreate = templateFlowRepository.findAll().size();
        // Create the TemplateFlow
        restTemplateFlowMockMvc.perform(post("/api/template-flows").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(templateFlow)))
            .andExpect(status().isCreated());

        // Validate the TemplateFlow in the database
        List<TemplateFlow> templateFlowList = templateFlowRepository.findAll();
        assertThat(templateFlowList).hasSize(databaseSizeBeforeCreate + 1);
        TemplateFlow testTemplateFlow = templateFlowList.get(templateFlowList.size() - 1);
        assertThat(testTemplateFlow.getConnectDetails()).isEqualTo(DEFAULT_CONNECT_DETAILS);
        assertThat(testTemplateFlow.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testTemplateFlow.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testTemplateFlow.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    public void createTemplateFlowWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = templateFlowRepository.findAll().size();

        // Create the TemplateFlow with an existing ID
        templateFlow.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTemplateFlowMockMvc.perform(post("/api/template-flows").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(templateFlow)))
            .andExpect(status().isBadRequest());

        // Validate the TemplateFlow in the database
        List<TemplateFlow> templateFlowList = templateFlowRepository.findAll();
        assertThat(templateFlowList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = templateFlowRepository.findAll().size();
        // set the field null
        templateFlow.setUserId(null);

        // Create the TemplateFlow, which fails.


        restTemplateFlowMockMvc.perform(post("/api/template-flows").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(templateFlow)))
            .andExpect(status().isBadRequest());

        List<TemplateFlow> templateFlowList = templateFlowRepository.findAll();
        assertThat(templateFlowList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedByIsRequired() throws Exception {
        int databaseSizeBeforeTest = templateFlowRepository.findAll().size();
        // set the field null
        templateFlow.setCreatedBy(null);

        // Create the TemplateFlow, which fails.


        restTemplateFlowMockMvc.perform(post("/api/template-flows").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(templateFlow)))
            .andExpect(status().isBadRequest());

        List<TemplateFlow> templateFlowList = templateFlowRepository.findAll();
        assertThat(templateFlowList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTemplateFlows() throws Exception {
        // Initialize the database
        templateFlowRepository.saveAndFlush(templateFlow);

        // Get all the templateFlowList
        restTemplateFlowMockMvc.perform(get("/api/template-flows?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(templateFlow.getId().intValue())))
            .andExpect(jsonPath("$.[*].connectDetails").value(hasItem(DEFAULT_CONNECT_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))));
    }
    
    @Test
    @Transactional
    public void getTemplateFlow() throws Exception {
        // Initialize the database
        templateFlowRepository.saveAndFlush(templateFlow);

        // Get the templateFlow
        restTemplateFlowMockMvc.perform(get("/api/template-flows/{id}", templateFlow.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(templateFlow.getId().intValue()))
            .andExpect(jsonPath("$.connectDetails").value(DEFAULT_CONNECT_DETAILS.toString()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)));
    }
    @Test
    @Transactional
    public void getNonExistingTemplateFlow() throws Exception {
        // Get the templateFlow
        restTemplateFlowMockMvc.perform(get("/api/template-flows/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTemplateFlow() throws Exception {
        // Initialize the database
        templateFlowService.save(templateFlow);

        int databaseSizeBeforeUpdate = templateFlowRepository.findAll().size();

        // Update the templateFlow
        TemplateFlow updatedTemplateFlow = templateFlowRepository.findById(templateFlow.getId()).get();
        // Disconnect from session so that the updates on updatedTemplateFlow are not directly saved in db
        em.detach(updatedTemplateFlow);
        updatedTemplateFlow
            .connectDetails(UPDATED_CONNECT_DETAILS)
            .userId(UPDATED_USER_ID)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE);

        restTemplateFlowMockMvc.perform(put("/api/template-flows").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTemplateFlow)))
            .andExpect(status().isOk());

        // Validate the TemplateFlow in the database
        List<TemplateFlow> templateFlowList = templateFlowRepository.findAll();
        assertThat(templateFlowList).hasSize(databaseSizeBeforeUpdate);
        TemplateFlow testTemplateFlow = templateFlowList.get(templateFlowList.size() - 1);
        assertThat(testTemplateFlow.getConnectDetails()).isEqualTo(UPDATED_CONNECT_DETAILS);
        assertThat(testTemplateFlow.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testTemplateFlow.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testTemplateFlow.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingTemplateFlow() throws Exception {
        int databaseSizeBeforeUpdate = templateFlowRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTemplateFlowMockMvc.perform(put("/api/template-flows").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(templateFlow)))
            .andExpect(status().isBadRequest());

        // Validate the TemplateFlow in the database
        List<TemplateFlow> templateFlowList = templateFlowRepository.findAll();
        assertThat(templateFlowList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTemplateFlow() throws Exception {
        // Initialize the database
        templateFlowService.save(templateFlow);

        int databaseSizeBeforeDelete = templateFlowRepository.findAll().size();

        // Delete the templateFlow
        restTemplateFlowMockMvc.perform(delete("/api/template-flows/{id}", templateFlow.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TemplateFlow> templateFlowList = templateFlowRepository.findAll();
        assertThat(templateFlowList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
