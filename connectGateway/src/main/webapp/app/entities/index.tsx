import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Campaign from './connectService/campaign';
import CampaignFlow from './connectService/campaign-flow';
import ConnectConfig from './connectService/connect-config';
import ConnectEvent from './connectService/connect-event';
import ConnectIntent from './connectService/connect-intent';
import Recipient from './connectService/recipient';
import Reminder from './connectService/reminder';
import ConnectState from './connectService/connect-state';
import TemplateFlow from './connectService/template-flow';
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
