package online.kehan.connect.analytic.collection.web.rest;

import online.kehan.connect.analytic.collection.RedisTestContainerExtension;
import online.kehan.connect.analytic.collection.AnalyticCollectionServiceApp;
import online.kehan.connect.analytic.collection.config.TestSecurityConfiguration;
import online.kehan.connect.analytic.collection.domain.Recipient;
import online.kehan.connect.analytic.collection.repository.RecipientRepository;
import online.kehan.connect.analytic.collection.service.RecipientService;

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

import static online.kehan.connect.analytic.collection.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link RecipientResource} REST controller.
 */
@SpringBootTest(classes = { AnalyticCollectionServiceApp.class, TestSecurityConfiguration.class })
@ExtendWith({ RedisTestContainerExtension.class, MockitoExtension.class })
@AutoConfigureMockMvc
@WithMockUser
public class RecipientResourceIT {

    private static final String DEFAULT_CAMPAIGN_ID = "AAAAAAAAAA";
    private static final String UPDATED_CAMPAIGN_ID = "BBBBBBBBBB";

    private static final String DEFAULT_REF = "AAAAAAAAAA";
    private static final String UPDATED_REF = "BBBBBBBBBB";

    private static final String DEFAULT_CONNECT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_CONNECT_DETAILS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private RecipientRepository recipientRepository;

    @Autowired
    private RecipientService recipientService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRecipientMockMvc;

    private Recipient recipient;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recipient createEntity(EntityManager em) {
        Recipient recipient = new Recipient()
            .campaignId(DEFAULT_CAMPAIGN_ID)
            .ref(DEFAULT_REF)
            .connectDetails(DEFAULT_CONNECT_DETAILS)
            .createdDate(DEFAULT_CREATED_DATE);
        return recipient;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Recipient createUpdatedEntity(EntityManager em) {
        Recipient recipient = new Recipient()
            .campaignId(UPDATED_CAMPAIGN_ID)
            .ref(UPDATED_REF)
            .connectDetails(UPDATED_CONNECT_DETAILS)
            .createdDate(UPDATED_CREATED_DATE);
        return recipient;
    }

    @BeforeEach
    public void initTest() {
        recipient = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecipient() throws Exception {
        int databaseSizeBeforeCreate = recipientRepository.findAll().size();
        // Create the Recipient
        restRecipientMockMvc.perform(post("/api/recipients").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recipient)))
            .andExpect(status().isCreated());

        // Validate the Recipient in the database
        List<Recipient> recipientList = recipientRepository.findAll();
        assertThat(recipientList).hasSize(databaseSizeBeforeCreate + 1);
        Recipient testRecipient = recipientList.get(recipientList.size() - 1);
        assertThat(testRecipient.getCampaignId()).isEqualTo(DEFAULT_CAMPAIGN_ID);
        assertThat(testRecipient.getRef()).isEqualTo(DEFAULT_REF);
        assertThat(testRecipient.getConnectDetails()).isEqualTo(DEFAULT_CONNECT_DETAILS);
        assertThat(testRecipient.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    public void createRecipientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recipientRepository.findAll().size();

        // Create the Recipient with an existing ID
        recipient.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecipientMockMvc.perform(post("/api/recipients").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recipient)))
            .andExpect(status().isBadRequest());

        // Validate the Recipient in the database
        List<Recipient> recipientList = recipientRepository.findAll();
        assertThat(recipientList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = recipientRepository.findAll().size();
        // set the field null
        recipient.setCreatedDate(null);

        // Create the Recipient, which fails.


        restRecipientMockMvc.perform(post("/api/recipients").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recipient)))
            .andExpect(status().isBadRequest());

        List<Recipient> recipientList = recipientRepository.findAll();
        assertThat(recipientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRecipients() throws Exception {
        // Initialize the database
        recipientRepository.saveAndFlush(recipient);

        // Get all the recipientList
        restRecipientMockMvc.perform(get("/api/recipients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recipient.getId().intValue())))
            .andExpect(jsonPath("$.[*].campaignId").value(hasItem(DEFAULT_CAMPAIGN_ID)))
            .andExpect(jsonPath("$.[*].ref").value(hasItem(DEFAULT_REF)))
            .andExpect(jsonPath("$.[*].connectDetails").value(hasItem(DEFAULT_CONNECT_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))));
    }
    
    @Test
    @Transactional
    public void getRecipient() throws Exception {
        // Initialize the database
        recipientRepository.saveAndFlush(recipient);

        // Get the recipient
        restRecipientMockMvc.perform(get("/api/recipients/{id}", recipient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(recipient.getId().intValue()))
            .andExpect(jsonPath("$.campaignId").value(DEFAULT_CAMPAIGN_ID))
            .andExpect(jsonPath("$.ref").value(DEFAULT_REF))
            .andExpect(jsonPath("$.connectDetails").value(DEFAULT_CONNECT_DETAILS.toString()))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)));
    }
    @Test
    @Transactional
    public void getNonExistingRecipient() throws Exception {
        // Get the recipient
        restRecipientMockMvc.perform(get("/api/recipients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecipient() throws Exception {
        // Initialize the database
        recipientService.save(recipient);

        int databaseSizeBeforeUpdate = recipientRepository.findAll().size();

        // Update the recipient
        Recipient updatedRecipient = recipientRepository.findById(recipient.getId()).get();
        // Disconnect from session so that the updates on updatedRecipient are not directly saved in db
        em.detach(updatedRecipient);
        updatedRecipient
            .campaignId(UPDATED_CAMPAIGN_ID)
            .ref(UPDATED_REF)
            .connectDetails(UPDATED_CONNECT_DETAILS)
            .createdDate(UPDATED_CREATED_DATE);

        restRecipientMockMvc.perform(put("/api/recipients").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedRecipient)))
            .andExpect(status().isOk());

        // Validate the Recipient in the database
        List<Recipient> recipientList = recipientRepository.findAll();
        assertThat(recipientList).hasSize(databaseSizeBeforeUpdate);
        Recipient testRecipient = recipientList.get(recipientList.size() - 1);
        assertThat(testRecipient.getCampaignId()).isEqualTo(UPDATED_CAMPAIGN_ID);
        assertThat(testRecipient.getRef()).isEqualTo(UPDATED_REF);
        assertThat(testRecipient.getConnectDetails()).isEqualTo(UPDATED_CONNECT_DETAILS);
        assertThat(testRecipient.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingRecipient() throws Exception {
        int databaseSizeBeforeUpdate = recipientRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRecipientMockMvc.perform(put("/api/recipients").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(recipient)))
            .andExpect(status().isBadRequest());

        // Validate the Recipient in the database
        List<Recipient> recipientList = recipientRepository.findAll();
        assertThat(recipientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRecipient() throws Exception {
        // Initialize the database
        recipientService.save(recipient);

        int databaseSizeBeforeDelete = recipientRepository.findAll().size();

        // Delete the recipient
        restRecipientMockMvc.perform(delete("/api/recipients/{id}", recipient.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Recipient> recipientList = recipientRepository.findAll();
        assertThat(recipientList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
