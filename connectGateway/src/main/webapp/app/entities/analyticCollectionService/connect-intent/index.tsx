import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ConnectIntent from './connect-intent';
import ConnectIntentDetail from './connect-intent-detail';
import ConnectIntentUpdate from './connect-intent-update';
import ConnectIntentDeleteDialog from './connect-intent-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ConnectIntentDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ConnectIntentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ConnectIntentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ConnectIntentDetail} />
      <ErrorBoundaryRoute path={match.url} component={ConnectIntent} />
    </Switch>
  </>
);

export default Routes;
