import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './campaign-flow.reducer';
import { ICampaignFlow } from 'app/shared/model/connectService/campaign-flow.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICampaignFlowUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CampaignFlowUpdate = (props: ICampaignFlowUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { campaignFlowEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/campaign-flow' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.createdDate = convertDateTimeToServer(values.createdDate);

    if (errors.length === 0) {
      const entity = {
        ...campaignFlowEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="connectGatewayApp.connectServiceCampaignFlow.home.createOrEditLabel">
            <Translate contentKey="connectGatewayApp.connectServiceCampaignFlow.home.createOrEditLabel">
              Create or edit a CampaignFlow
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : campaignFlowEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="campaign-flow-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="campaign-flow-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="campaignIdLabel" for="campaign-flow-campaignId">
                  <Translate contentKey="connectGatewayApp.connectServiceCampaignFlow.campaignId">Campaign Id</Translate>
                </Label>
                <AvField
                  id="campaign-flow-campaignId"
                  type="text"
                  name="campaignId"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="templateFlowIdLabel" for="campaign-flow-templateFlowId">
                  <Translate contentKey="connectGatewayApp.connectServiceCampaignFlow.templateFlowId">Template Flow Id</Translate>
                </Label>
                <AvField id="campaign-flow-templateFlowId" type="text" name="templateFlowId" />
              </AvGroup>
              <AvGroup>
                <Label id="createdDateLabel" for="campaign-flow-createdDate">
                  <Translate contentKey="connectGatewayApp.connectServiceCampaignFlow.createdDate">Created Date</Translate>
                </Label>
                <AvInput
                  id="campaign-flow-createdDate"
                  type="datetime-local"
                  className="form-control"
                  name="createdDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.campaignFlowEntity.createdDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="createdByLabel" for="campaign-flow-createdBy">
                  <Translate contentKey="connectGatewayApp.connectServiceCampaignFlow.createdBy">Created By</Translate>
                </Label>
                <AvField id="campaign-flow-createdBy" type="text" name="createdBy" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/campaign-flow" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  campaignFlowEntity: storeState.campaignFlow.entity,
  loading: storeState.campaignFlow.loading,
  updating: storeState.campaignFlow.updating,
  updateSuccess: storeState.campaignFlow.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CampaignFlowUpdate);
