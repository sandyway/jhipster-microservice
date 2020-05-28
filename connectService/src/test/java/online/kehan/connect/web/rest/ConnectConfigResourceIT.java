package online.kehan.connect.web.rest;

import online.kehan.connect.RedisTestContainerExtension;
import online.kehan.connect.ConnectServiceApp;
import online.kehan.connect.config.TestSecurityConfiguration;
import online.kehan.connect.domain.ConnectConfig;
import online.kehan.connect.repository.ConnectConfigRepository;
import online.kehan.connect.service.ConnectConfigService;
import online.kehan.connect.service.dto.ConnectConfigDTO;
import online.kehan.connect.service.mapper.ConnectConfigMapper;

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

/**
 * Integration tests for the {@link ConnectConfigResource} REST controller.
 */
@SpringBootTest(classes = { ConnectServiceApp.class, TestSecurityConfiguration.class })
@ExtendWith({ RedisTestContainerExtension.class, MockitoExtension.class })
@AutoConfigureMockMvc
@WithMockUser
public class ConnectConfigResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_FACEBOOK = "AAAAAAAAAA";
    private static final String UPDATED_FACEBOOK = "BBBBBBBBBB";

    private static final String DEFAULT_VIBER = "AAAAAAAAAA";
    private static final String UPDATED_VIBER = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ConnectConfigRepository connectConfigRepository;

    @Autowired
    private ConnectConfigMapper connectConfigMapper;

    @Autowired
    private ConnectConfigService connectConfigService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConnectConfigMockMvc;

    private ConnectConfig connectConfig;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConnectConfig createEntity(EntityManager em) {
        ConnectConfig connectConfig = new ConnectConfig()
            .userId(DEFAULT_USER_ID)
            .facebook(DEFAULT_FACEBOOK)
            .viber(DEFAULT_VIBER)
            .createdDate(DEFAULT_CREATED_DATE);
        return connectConfig;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConnectConfig createUpdatedEntity(EntityManager em) {
        ConnectConfig connectConfig = new ConnectConfig()
            .userId(UPDATED_USER_ID)
            .facebook(UPDATED_FACEBOOK)
            .viber(UPDATED_VIBER)
            .createdDate(UPDATED_CREATED_DATE);
        return connectConfig;
    }

    @BeforeEach
    public void initTest() {
        connectConfig = createEntity(em);
    }

    @Test
    @Transactional
    public void createConnectConfig() throws Exception {
        int databaseSizeBeforeCreate = connectConfigRepository.findAll().size();
        // Create the ConnectConfig
        ConnectConfigDTO connectConfigDTO = connectConfigMapper.toDto(connectConfig);
        restConnectConfigMockMvc.perform(post("/api/connect-configs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(connectConfigDTO)))
            .andExpect(status().isCreated());

        // Validate the ConnectConfig in the database
        List<ConnectConfig> connectConfigList = connectConfigRepository.findAll();
        assertThat(connectConfigList).hasSize(databaseSizeBeforeCreate + 1);
        ConnectConfig testConnectConfig = connectConfigList.get(connectConfigList.size() - 1);
        assertThat(testConnectConfig.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testConnectConfig.getFacebook()).isEqualTo(DEFAULT_FACEBOOK);
        assertThat(testConnectConfig.getViber()).isEqualTo(DEFAULT_VIBER);
        assertThat(testConnectConfig.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    public void createConnectConfigWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = connectConfigRepository.findAll().size();

        // Create the ConnectConfig with an existing ID
        connectConfig.setId(1L);
        ConnectConfigDTO connectConfigDTO = connectConfigMapper.toDto(connectConfig);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConnectConfigMockMvc.perform(post("/api/connect-configs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(connectConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConnectConfig in the database
        List<ConnectConfig> connectConfigList = connectConfigRepository.findAll();
        assertThat(connectConfigList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = connectConfigRepository.findAll().size();
        // set the field null
        connectConfig.setUserId(null);

        // Create the ConnectConfig, which fails.
        ConnectConfigDTO connectConfigDTO = connectConfigMapper.toDto(connectConfig);


        restConnectConfigMockMvc.perform(post("/api/connect-configs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(connectConfigDTO)))
            .andExpect(status().isBadRequest());

        List<ConnectConfig> connectConfigList = connectConfigRepository.findAll();
        assertThat(connectConfigList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConnectConfigs() throws Exception {
        // Initialize the database
        connectConfigRepository.saveAndFlush(connectConfig);

        // Get all the connectConfigList
        restConnectConfigMockMvc.perform(get("/api/connect-configs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(connectConfig.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].facebook").value(hasItem(DEFAULT_FACEBOOK.toString())))
            .andExpect(jsonPath("$.[*].viber").value(hasItem(DEFAULT_VIBER.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))));
    }
    
    @Test
    @Transactional
    public void getConnectConfig() throws Exception {
        // Initialize the database
        connectConfigRepository.saveAndFlush(connectConfig);

        // Get the connectConfig
        restConnectConfigMockMvc.perform(get("/api/connect-configs/{id}", connectConfig.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(connectConfig.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.facebook").value(DEFAULT_FACEBOOK.toString()))
            .andExpect(jsonPath("$.viber").value(DEFAULT_VIBER.toString()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)));
    }
    @Test
    @Transactional
    public void getNonExistingConnectConfig() throws Exception {
        // Get the connectConfig
        restConnectConfigMockMvc.perform(get("/api/connect-configs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConnectConfig() throws Exception {
        // Initialize the database
        connectConfigRepository.saveAndFlush(connectConfig);

        int databaseSizeBeforeUpdate = connectConfigRepository.findAll().size();

        // Update the connectConfig
        ConnectConfig updatedConnectConfig = connectConfigRepository.findById(connectConfig.getId()).get();
        // Disconnect from session so that the updates on updatedConnectConfig are not directly saved in db
        em.detach(updatedConnectConfig);
        updatedConnectConfig
            .userId(UPDATED_USER_ID)
            .facebook(UPDATED_FACEBOOK)
            .viber(UPDATED_VIBER)
            .createdDate(UPDATED_CREATED_DATE);
        ConnectConfigDTO connectConfigDTO = connectConfigMapper.toDto(updatedConnectConfig);

        restConnectConfigMockMvc.perform(put("/api/connect-configs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(connectConfigDTO)))
            .andExpect(status().isOk());

        // Validate the ConnectConfig in the database
        List<ConnectConfig> connectConfigList = connectConfigRepository.findAll();
        assertThat(connectConfigList).hasSize(databaseSizeBeforeUpdate);
        ConnectConfig testConnectConfig = connectConfigList.get(connectConfigList.size() - 1);
        assertThat(testConnectConfig.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testConnectConfig.getFacebook()).isEqualTo(UPDATED_FACEBOOK);
        assertThat(testConnectConfig.getViber()).isEqualTo(UPDATED_VIBER);
        assertThat(testConnectConfig.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingConnectConfig() throws Exception {
        int databaseSizeBeforeUpdate = connectConfigRepository.findAll().size();

        // Create the ConnectConfig
        ConnectConfigDTO connectConfigDTO = connectConfigMapper.toDto(connectConfig);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConnectConfigMockMvc.perform(put("/api/connect-configs").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(connectConfigDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConnectConfig in the database
        List<ConnectConfig> connectConfigList = connectConfigRepository.findAll();
        assertThat(connectConfigList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConnectConfig() throws Exception {
        // Initialize the database
        connectConfigRepository.saveAndFlush(connectConfig);

        int databaseSizeBeforeDelete = connectConfigRepository.findAll().size();

        // Delete the connectConfig
        restConnectConfigMockMvc.perform(delete("/api/connect-configs/{id}", connectConfig.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConnectConfig> connectConfigList = connectConfigRepository.findAll();
        assertThat(connectConfigList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
