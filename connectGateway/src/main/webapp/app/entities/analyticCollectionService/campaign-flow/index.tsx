import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CampaignFlow from './campaign-flow';
import CampaignFlowDetail from './campaign-flow-detail';
import CampaignFlowUpdate from './campaign-flow-update';
import CampaignFlowDeleteDialog from './campaign-flow-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CampaignFlowDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CampaignFlowUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CampaignFlowUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CampaignFlowDetail} />
      <ErrorBoundaryRoute path={match.url} component={CampaignFlow} />
    </Switch>
  </>
);

export default Routes;
