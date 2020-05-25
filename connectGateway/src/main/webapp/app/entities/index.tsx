import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Campaign from './analyticCollectionService/campaign';
import CampaignFlow from './analyticCollectionService/campaign-flow';
import ConnectConfig from './analyticCollectionService/connect-config';
import ConnectEvent from './analyticCollectionService/connect-event';
import ConnectIntent from './analyticCollectionService/connect-intent';
import Recipient from './analyticCollectionService/recipient';
import Reminder from './analyticCollectionService/reminder';
import ConnectState from './analyticCollectionService/connect-state';
import TemplateFlow from './analyticCollectionService/template-flow';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}campaign`} component={Campaign} />
      <ErrorBoundaryRoute path={`${match.url}campaign-flow`} component={CampaignFlow} />
      <ErrorBoundaryRoute path={`${match.url}connect-config`} component={ConnectConfig} />
      <ErrorBoundaryRoute path={`${match.url}connect-event`} component={ConnectEvent} />
      <ErrorBoundaryRoute path={`${match.url}connect-intent`} component={ConnectIntent} />
      <ErrorBoundaryRoute path={`${match.url}recipient`} component={Recipient} />
      <ErrorBoundaryRoute path={`${match.url}reminder`} component={Reminder} />
      <ErrorBoundaryRoute path={`${match.url}connect-state`} component={ConnectState} />
      <ErrorBoundaryRoute path={`${match.url}template-flow`} component={TemplateFlow} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
