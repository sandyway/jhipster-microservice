import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TemplateFlow from './template-flow';
import TemplateFlowDetail from './template-flow-detail';
import TemplateFlowUpdate from './template-flow-update';
import TemplateFlowDeleteDialog from './template-flow-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TemplateFlowDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TemplateFlowUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TemplateFlowUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TemplateFlowDetail} />
      <ErrorBoundaryRoute path={match.url} component={TemplateFlow} />
    </Switch>
  </>
);

export default Routes;
