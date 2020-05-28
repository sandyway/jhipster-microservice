import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ConnectEvent from './connect-event';
import ConnectEventDetail from './connect-event-detail';
import ConnectEventUpdate from './connect-event-update';
import ConnectEventDeleteDialog from './connect-event-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ConnectEventDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ConnectEventUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ConnectEventUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ConnectEventDetail} />
      <ErrorBoundaryRoute path={match.url} component={ConnectEvent} />
    </Switch>
  </>
);

export default Routes;
