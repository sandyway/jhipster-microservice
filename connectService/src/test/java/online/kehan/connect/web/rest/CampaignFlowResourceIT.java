package online.kehan.connect.web.rest;

import online.kehan.connect.RedisTestContainerExtension;
import online.kehan.connect.ConnectServiceApp;
import online.kehan.connect.config.TestSecurityConfiguration;
import online.kehan.connect.domain.CampaignFlow;
import online.kehan.connect.repository.CampaignFlowRepository;
import online.kehan.connect.service.CampaignFlowService;
import online.kehan.connect.service.dto.CampaignFlowDTO;
import online.kehan.connect.service.mapper.CampaignFlowMapper;

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

/**
 * Integration tests for the {@link CampaignFlowResource} REST controller.
 */
@SpringBootTest(classes = { ConnectServiceApp.class, TestSecurityConfiguration.class })
@ExtendWith({ RedisTestContainerExtension.class, MockitoExtension.class })
@AutoConfigureMockMvc
@WithMockUser
public class CampaignFlowResourceIT {

    private static final String DEFAULT_CAMPAIGN_ID = "AAAAAAAAAA";
    private static final String UPDATED_CAMPAIGN_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TEMPLATE_FLOW_ID = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE_FLOW_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    @Autowired
    private CampaignFlowRepository campaignFlowRepository;

    @Autowired
    private CampaignFlowMapper campaignFlowMapper;

    @Autowired
    private CampaignFlowService campaignFlowService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCampaignFlowMockMvc;

    private CampaignFlow campaignFlow;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CampaignFlow createEntity(EntityManager em) {
        CampaignFlow campaignFlow = new CampaignFlow()
            .campaignId(DEFAULT_CAMPAIGN_ID)
            .templateFlowId(DEFAULT_TEMPLATE_FLOW_ID)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY);
        return campaignFlow;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CampaignFlow createUpdatedEntity(EntityManager em) {
        CampaignFlow campaignFlow = new CampaignFlow()
            .campaignId(UPDATED_CAMPAIGN_ID)
            .templateFlowId(UPDATED_TEMPLATE_FLOW_ID)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY);
        return campaignFlow;
    }

    @BeforeEach
    public void initTest() {
        campaignFlow = createEntity(em);
    }

    @Test
    @Transactional
    public void createCampaignFlow() throws Exception {
        int databaseSizeBeforeCreate = campaignFlowRepository.findAll().size();
        // Create the CampaignFlow
        CampaignFlowDTO campaignFlowDTO = campaignFlowMapper.toDto(campaignFlow);
        restCampaignFlowMockMvc.perform(post("/api/campaign-flows").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(campaignFlowDTO)))
            .andExpect(status().isCreated());

        // Validate the CampaignFlow in the database
        List<CampaignFlow> campaignFlowList = campaignFlowRepository.findAll();
        assertThat(campaignFlowList).hasSize(databaseSizeBeforeCreate + 1);
        CampaignFlow testCampaignFlow = campaignFlowList.get(campaignFlowList.size() - 1);
        assertThat(testCampaignFlow.getCampaignId()).isEqualTo(DEFAULT_CAMPAIGN_ID);
        assertThat(testCampaignFlow.getTemplateFlowId()).isEqualTo(DEFAULT_TEMPLATE_FLOW_ID);
        assertThat(testCampaignFlow.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testCampaignFlow.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
    }

    @Test
    @Transactional
    public void createCampaignFlowWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = campaignFlowRepository.findAll().size();

        // Create the CampaignFlow with an existing ID
        campaignFlow.setId(1L);
        CampaignFlowDTO campaignFlowDTO = campaignFlowMapper.toDto(campaignFlow);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCampaignFlowMockMvc.perform(post("/api/campaign-flows").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(campaignFlowDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CampaignFlow in the database
        List<CampaignFlow> campaignFlowList = campaignFlowRepository.findAll();
        assertThat(campaignFlowList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCampaignIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = campaignFlowRepository.findAll().size();
        // set the field null
        campaignFlow.setCampaignId(null);

        // Create the CampaignFlow, which fails.
        CampaignFlowDTO campaignFlowDTO = campaignFlowMapper.toDto(campaignFlow);


        restCampaignFlowMockMvc.perform(post("/api/campaign-flows").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(campaignFlowDTO)))
            .andExpect(status().isBadRequest());

        List<CampaignFlow> campaignFlowList = campaignFlowRepository.findAll();
        assertThat(campaignFlowList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCampaignFlows() throws Exception {
        // Initialize the database
        campaignFlowRepository.saveAndFlush(campaignFlow);

        // Get all the campaignFlowList
        restCampaignFlowMockMvc.perform(get("/api/campaign-flows?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(campaignFlow.getId().intValue())))
            .andExpect(jsonPath("$.[*].campaignId").value(hasItem(DEFAULT_CAMPAIGN_ID)))
            .andExpect(jsonPath("$.[*].templateFlowId").value(hasItem(DEFAULT_TEMPLATE_FLOW_ID)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)));
    }
    
    @Test
    @Transactional
    public void getCampaignFlow() throws Exception {
        // Initialize the database
        campaignFlowRepository.saveAndFlush(campaignFlow);

        // Get the campaignFlow
        restCampaignFlowMockMvc.perform(get("/api/campaign-flows/{id}", campaignFlow.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(campaignFlow.getId().intValue()))
            .andExpect(jsonPath("$.campaignId").value(DEFAULT_CAMPAIGN_ID))
            .andExpect(jsonPath("$.templateFlowId").value(DEFAULT_TEMPLATE_FLOW_ID))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY));
    }
    @Test
    @Transactional
    public void getNonExistingCampaignFlow() throws Exception {
        // Get the campaignFlow
        restCampaignFlowMockMvc.perform(get("/api/campaign-flows/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCampaignFlow() throws Exception {
        // Initialize the database
        campaignFlowRepository.saveAndFlush(campaignFlow);

        int databaseSizeBeforeUpdate = campaignFlowRepository.findAll().size();

        // Update the campaignFlow
        CampaignFlow updatedCampaignFlow = campaignFlowRepository.findById(campaignFlow.getId()).get();
        // Disconnect from session so that the updates on updatedCampaignFlow are not directly saved in db
        em.detach(updatedCampaignFlow);
        updatedCampaignFlow
            .campaignId(UPDATED_CAMPAIGN_ID)
            .templateFlowId(UPDATED_TEMPLATE_FLOW_ID)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY);
        CampaignFlowDTO campaignFlowDTO = campaignFlowMapper.toDto(updatedCampaignFlow);

        restCampaignFlowMockMvc.perform(put("/api/campaign-flows").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(campaignFlowDTO)))
            .andExpect(status().isOk());

        // Validate the CampaignFlow in the database
        List<CampaignFlow> campaignFlowList = campaignFlowRepository.findAll();
        assertThat(campaignFlowList).hasSize(databaseSizeBeforeUpdate);
        CampaignFlow testCampaignFlow = campaignFlowList.get(campaignFlowList.size() - 1);
        assertThat(testCampaignFlow.getCampaignId()).isEqualTo(UPDATED_CAMPAIGN_ID);
        assertThat(testCampaignFlow.getTemplateFlowId()).isEqualTo(UPDATED_TEMPLATE_FLOW_ID);
        assertThat(testCampaignFlow.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testCampaignFlow.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingCampaignFlow() throws Exception {
        int databaseSizeBeforeUpdate = campaignFlowRepository.findAll().size();

        // Create the CampaignFlow
        CampaignFlowDTO campaignFlowDTO = campaignFlowMapper.toDto(campaignFlow);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCampaignFlowMockMvc.perform(put("/api/campaign-flows").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(campaignFlowDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CampaignFlow in the database
        List<CampaignFlow> campaignFlowList = campaignFlowRepository.findAll();
        assertThat(campaignFlowList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCampaignFlow() throws Exception {
        // Initialize the database
        campaignFlowRepository.saveAndFlush(campaignFlow);

        int databaseSizeBeforeDelete = campaignFlowRepository.findAll().size();

        // Delete the campaignFlow
        restCampaignFlowMockMvc.perform(delete("/api/campaign-flows/{id}", campaignFlow.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CampaignFlow> campaignFlowList = campaignFlowRepository.findAll();
        assertThat(campaignFlowList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
