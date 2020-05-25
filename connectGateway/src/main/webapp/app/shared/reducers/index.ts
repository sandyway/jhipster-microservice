import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import locale, { LocaleState } from './locale';
import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from './user-management';
// prettier-ignore
import campaign, {
  CampaignState
} from 'app/entities/analyticCollectionService/campaign/campaign.reducer';
// prettier-ignore
import campaignFlow, {
  CampaignFlowState
} from 'app/entities/analyticCollectionService/campaign-flow/campaign-flow.reducer';
// prettier-ignore
import connectConfig, {
  ConnectConfigState
} from 'app/entities/analyticCollectionService/connect-config/connect-config.reducer';
// prettier-ignore
import connectEvent, {
  ConnectEventState
} from 'app/entities/analyticCollectionService/connect-event/connect-event.reducer';
// prettier-ignore
import connectIntent, {
  ConnectIntentState
} from 'app/entities/analyticCollectionService/connect-intent/connect-intent.reducer';
// prettier-ignore
import recipient, {
  RecipientState
} from 'app/entities/analyticCollectionService/recipient/recipient.reducer';
// prettier-ignore
import reminder, {
  ReminderState
} from 'app/entities/analyticCollectionService/reminder/reminder.reducer';
// prettier-ignore
import connectState, {
  ConnectStateState
} from 'app/entities/analyticCollectionService/connect-state/connect-state.reducer';
// prettier-ignore
import templateFlow, {
  TemplateFlowState
} from 'app/entities/analyticCollectionService/template-flow/template-flow.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly locale: LocaleState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly campaign: CampaignState;
  readonly campaignFlow: CampaignFlowState;
  readonly connectConfig: ConnectConfigState;
  readonly connectEvent: ConnectEventState;
  readonly connectIntent: ConnectIntentState;
  readonly recipient: RecipientState;
  readonly reminder: ReminderState;
  readonly connectState: ConnectStateState;
  readonly templateFlow: TemplateFlowState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  locale,
  applicationProfile,
  administration,
  userManagement,
  campaign,
  campaignFlow,
  connectConfig,
  connectEvent,
  connectIntent,
  recipient,
  reminder,
  connectState,
  templateFlow,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
});

export default rootReducer;
