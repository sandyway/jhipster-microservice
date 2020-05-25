import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ConnectConfig from './connect-config';
import ConnectConfigDetail from './connect-config-detail';
import ConnectConfigUpdate from './connect-config-update';
import ConnectConfigDeleteDialog from './connect-config-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ConnectConfigDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ConnectConfigUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ConnectConfigUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ConnectConfigDetail} />
      <ErrorBoundaryRoute path={match.url} component={ConnectConfig} />
    </Switch>
  </>
);

export default Routes;
