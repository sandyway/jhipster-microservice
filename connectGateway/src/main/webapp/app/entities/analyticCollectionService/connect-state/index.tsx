import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ConnectState from './connect-state';
import ConnectStateDetail from './connect-state-detail';
import ConnectStateUpdate from './connect-state-update';
import ConnectStateDeleteDialog from './connect-state-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ConnectStateDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ConnectStateUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ConnectStateUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ConnectStateDetail} />
      <ErrorBoundaryRoute path={match.url} component={ConnectState} />
    </Switch>
  </>
);

export default Routes;
