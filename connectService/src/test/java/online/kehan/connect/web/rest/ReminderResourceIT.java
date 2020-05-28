package online.kehan.connect.web.rest;

import online.kehan.connect.RedisTestContainerExtension;
import online.kehan.connect.ConnectServiceApp;
import online.kehan.connect.config.TestSecurityConfiguration;
import online.kehan.connect.domain.Reminder;
import online.kehan.connect.repository.ReminderRepository;
import online.kehan.connect.service.ReminderService;
import online.kehan.connect.service.dto.ReminderDTO;
import online.kehan.connect.service.mapper.ReminderMapper;

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
/**
 * Integration tests for the {@link ReminderResource} REST controller.
 */
@SpringBootTest(classes = { ConnectServiceApp.class, TestSecurityConfiguration.class })
@ExtendWith({ RedisTestContainerExtension.class, MockitoExtension.class })
@AutoConfigureMockMvc
@WithMockUser
public class ReminderResourceIT {

    private static final String DEFAULT_RECIPIENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_RECIPIENT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CAMPAIGN_ID = "AAAAAAAAAA";
    private static final String UPDATED_CAMPAIGN_ID = "BBBBBBBBBB";

    private static final String DEFAULT_INTENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_INTENT_ID = "BBBBBBBBBB";

    private static final Channel DEFAULT_CHANNEL = Channel.SMS;
    private static final Channel UPDATED_CHANNEL = Channel.MESSENGER;

    private static final String DEFAULT_CONNECT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_CONNECT_DETAILS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DONE = false;
    private static final Boolean UPDATED_DONE = true;

    private static final Integer DEFAULT_EXECUTIONS = 1;
    private static final Integer UPDATED_EXECUTIONS = 2;

    private static final ZonedDateTime DEFAULT_CREATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_UPDATED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private ReminderMapper reminderMapper;

    @Autowired
    private ReminderService reminderService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReminderMockMvc;

    private Reminder reminder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reminder createEntity(EntityManager em) {
        Reminder reminder = new Reminder()
            .recipientId(DEFAULT_RECIPIENT_ID)
            .campaignId(DEFAULT_CAMPAIGN_ID)
            .intentId(DEFAULT_INTENT_ID)
            .channel(DEFAULT_CHANNEL)
            .connectDetails(DEFAULT_CONNECT_DETAILS)
            .done(DEFAULT_DONE)
            .executions(DEFAULT_EXECUTIONS)
            .createdDate(DEFAULT_CREATED_DATE)
            .updatedDate(DEFAULT_UPDATED_DATE);
        return reminder;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Reminder createUpdatedEntity(EntityManager em) {
        Reminder reminder = new Reminder()
            .recipientId(UPDATED_RECIPIENT_ID)
            .campaignId(UPDATED_CAMPAIGN_ID)
            .intentId(UPDATED_INTENT_ID)
            .channel(UPDATED_CHANNEL)
            .connectDetails(UPDATED_CONNECT_DETAILS)
            .done(UPDATED_DONE)
            .executions(UPDATED_EXECUTIONS)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        return reminder;
    }

    @BeforeEach
    public void initTest() {
        reminder = createEntity(em);
    }

    @Test
    @Transactional
    public void createReminder() throws Exception {
        int databaseSizeBeforeCreate = reminderRepository.findAll().size();
        // Create the Reminder
        ReminderDTO reminderDTO = reminderMapper.toDto(reminder);
        restReminderMockMvc.perform(post("/api/reminders").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reminderDTO)))
            .andExpect(status().isCreated());

        // Validate the Reminder in the database
        List<Reminder> reminderList = reminderRepository.findAll();
        assertThat(reminderList).hasSize(databaseSizeBeforeCreate + 1);
        Reminder testReminder = reminderList.get(reminderList.size() - 1);
        assertThat(testReminder.getRecipientId()).isEqualTo(DEFAULT_RECIPIENT_ID);
        assertThat(testReminder.getCampaignId()).isEqualTo(DEFAULT_CAMPAIGN_ID);
        assertThat(testReminder.getIntentId()).isEqualTo(DEFAULT_INTENT_ID);
        assertThat(testReminder.getChannel()).isEqualTo(DEFAULT_CHANNEL);
        assertThat(testReminder.getConnectDetails()).isEqualTo(DEFAULT_CONNECT_DETAILS);
        assertThat(testReminder.isDone()).isEqualTo(DEFAULT_DONE);
        assertThat(testReminder.getExecutions()).isEqualTo(DEFAULT_EXECUTIONS);
        assertThat(testReminder.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testReminder.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void createReminderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = reminderRepository.findAll().size();

        // Create the Reminder with an existing ID
        reminder.setId(1L);
        ReminderDTO reminderDTO = reminderMapper.toDto(reminder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restReminderMockMvc.perform(post("/api/reminders").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reminderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Reminder in the database
        List<Reminder> reminderList = reminderRepository.findAll();
        assertThat(reminderList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkRecipientIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = reminderRepository.findAll().size();
        // set the field null
        reminder.setRecipientId(null);

        // Create the Reminder, which fails.
        ReminderDTO reminderDTO = reminderMapper.toDto(reminder);


        restReminderMockMvc.perform(post("/api/reminders").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reminderDTO)))
            .andExpect(status().isBadRequest());

        List<Reminder> reminderList = reminderRepository.findAll();
        assertThat(reminderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCampaignIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = reminderRepository.findAll().size();
        // set the field null
        reminder.setCampaignId(null);

        // Create the Reminder, which fails.
        ReminderDTO reminderDTO = reminderMapper.toDto(reminder);


        restReminderMockMvc.perform(post("/api/reminders").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reminderDTO)))
            .andExpect(status().isBadRequest());

        List<Reminder> reminderList = reminderRepository.findAll();
        assertThat(reminderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIntentIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = reminderRepository.findAll().size();
        // set the field null
        reminder.setIntentId(null);

        // Create the Reminder, which fails.
        ReminderDTO reminderDTO = reminderMapper.toDto(reminder);


        restReminderMockMvc.perform(post("/api/reminders").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reminderDTO)))
            .andExpect(status().isBadRequest());

        List<Reminder> reminderList = reminderRepository.findAll();
        assertThat(reminderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkChannelIsRequired() throws Exception {
        int databaseSizeBeforeTest = reminderRepository.findAll().size();
        // set the field null
        reminder.setChannel(null);

        // Create the Reminder, which fails.
        ReminderDTO reminderDTO = reminderMapper.toDto(reminder);


        restReminderMockMvc.perform(post("/api/reminders").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reminderDTO)))
            .andExpect(status().isBadRequest());

        List<Reminder> reminderList = reminderRepository.findAll();
        assertThat(reminderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = reminderRepository.findAll().size();
        // set the field null
        reminder.setDone(null);

        // Create the Reminder, which fails.
        ReminderDTO reminderDTO = reminderMapper.toDto(reminder);


        restReminderMockMvc.perform(post("/api/reminders").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reminderDTO)))
            .andExpect(status().isBadRequest());

        List<Reminder> reminderList = reminderRepository.findAll();
        assertThat(reminderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkExecutionsIsRequired() throws Exception {
        int databaseSizeBeforeTest = reminderRepository.findAll().size();
        // set the field null
        reminder.setExecutions(null);

        // Create the Reminder, which fails.
        ReminderDTO reminderDTO = reminderMapper.toDto(reminder);


        restReminderMockMvc.perform(post("/api/reminders").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reminderDTO)))
            .andExpect(status().isBadRequest());

        List<Reminder> reminderList = reminderRepository.findAll();
        assertThat(reminderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreatedDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = reminderRepository.findAll().size();
        // set the field null
        reminder.setCreatedDate(null);

        // Create the Reminder, which fails.
        ReminderDTO reminderDTO = reminderMapper.toDto(reminder);


        restReminderMockMvc.perform(post("/api/reminders").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reminderDTO)))
            .andExpect(status().isBadRequest());

        List<Reminder> reminderList = reminderRepository.findAll();
        assertThat(reminderList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllReminders() throws Exception {
        // Initialize the database
        reminderRepository.saveAndFlush(reminder);

        // Get all the reminderList
        restReminderMockMvc.perform(get("/api/reminders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reminder.getId().intValue())))
            .andExpect(jsonPath("$.[*].recipientId").value(hasItem(DEFAULT_RECIPIENT_ID)))
            .andExpect(jsonPath("$.[*].campaignId").value(hasItem(DEFAULT_CAMPAIGN_ID)))
            .andExpect(jsonPath("$.[*].intentId").value(hasItem(DEFAULT_INTENT_ID)))
            .andExpect(jsonPath("$.[*].channel").value(hasItem(DEFAULT_CHANNEL.toString())))
            .andExpect(jsonPath("$.[*].connectDetails").value(hasItem(DEFAULT_CONNECT_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].done").value(hasItem(DEFAULT_DONE.booleanValue())))
            .andExpect(jsonPath("$.[*].executions").value(hasItem(DEFAULT_EXECUTIONS)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(sameInstant(DEFAULT_CREATED_DATE))))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(sameInstant(DEFAULT_UPDATED_DATE))));
    }
    
    @Test
    @Transactional
    public void getReminder() throws Exception {
        // Initialize the database
        reminderRepository.saveAndFlush(reminder);

        // Get the reminder
        restReminderMockMvc.perform(get("/api/reminders/{id}", reminder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reminder.getId().intValue()))
            .andExpect(jsonPath("$.recipientId").value(DEFAULT_RECIPIENT_ID))
            .andExpect(jsonPath("$.campaignId").value(DEFAULT_CAMPAIGN_ID))
            .andExpect(jsonPath("$.intentId").value(DEFAULT_INTENT_ID))
            .andExpect(jsonPath("$.channel").value(DEFAULT_CHANNEL.toString()))
            .andExpect(jsonPath("$.connectDetails").value(DEFAULT_CONNECT_DETAILS.toString()))
            .andExpect(jsonPath("$.done").value(DEFAULT_DONE.booleanValue()))
            .andExpect(jsonPath("$.executions").value(DEFAULT_EXECUTIONS))
            .andExpect(jsonPath("$.createdDate").value(sameInstant(DEFAULT_CREATED_DATE)))
            .andExpect(jsonPath("$.updatedDate").value(sameInstant(DEFAULT_UPDATED_DATE)));
    }
    @Test
    @Transactional
    public void getNonExistingReminder() throws Exception {
        // Get the reminder
        restReminderMockMvc.perform(get("/api/reminders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateReminder() throws Exception {
        // Initialize the database
        reminderRepository.saveAndFlush(reminder);

        int databaseSizeBeforeUpdate = reminderRepository.findAll().size();

        // Update the reminder
        Reminder updatedReminder = reminderRepository.findById(reminder.getId()).get();
        // Disconnect from session so that the updates on updatedReminder are not directly saved in db
        em.detach(updatedReminder);
        updatedReminder
            .recipientId(UPDATED_RECIPIENT_ID)
            .campaignId(UPDATED_CAMPAIGN_ID)
            .intentId(UPDATED_INTENT_ID)
            .channel(UPDATED_CHANNEL)
            .connectDetails(UPDATED_CONNECT_DETAILS)
            .done(UPDATED_DONE)
            .executions(UPDATED_EXECUTIONS)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE);
        ReminderDTO reminderDTO = reminderMapper.toDto(updatedReminder);

        restReminderMockMvc.perform(put("/api/reminders").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reminderDTO)))
            .andExpect(status().isOk());

        // Validate the Reminder in the database
        List<Reminder> reminderList = reminderRepository.findAll();
        assertThat(reminderList).hasSize(databaseSizeBeforeUpdate);
        Reminder testReminder = reminderList.get(reminderList.size() - 1);
        assertThat(testReminder.getRecipientId()).isEqualTo(UPDATED_RECIPIENT_ID);
        assertThat(testReminder.getCampaignId()).isEqualTo(UPDATED_CAMPAIGN_ID);
        assertThat(testReminder.getIntentId()).isEqualTo(UPDATED_INTENT_ID);
        assertThat(testReminder.getChannel()).isEqualTo(UPDATED_CHANNEL);
        assertThat(testReminder.getConnectDetails()).isEqualTo(UPDATED_CONNECT_DETAILS);
        assertThat(testReminder.isDone()).isEqualTo(UPDATED_DONE);
        assertThat(testReminder.getExecutions()).isEqualTo(UPDATED_EXECUTIONS);
        assertThat(testReminder.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testReminder.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingReminder() throws Exception {
        int databaseSizeBeforeUpdate = reminderRepository.findAll().size();

        // Create the Reminder
        ReminderDTO reminderDTO = reminderMapper.toDto(reminder);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReminderMockMvc.perform(put("/api/reminders").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(reminderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Reminder in the database
        List<Reminder> reminderList = reminderRepository.findAll();
        assertThat(reminderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteReminder() throws Exception {
        // Initialize the database
        reminderRepository.saveAndFlush(reminder);

        int databaseSizeBeforeDelete = reminderRepository.findAll().size();

        // Delete the reminder
        restReminderMockMvc.perform(delete("/api/reminders/{id}", reminder.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Reminder> reminderList = reminderRepository.findAll();
        assertThat(reminderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
