import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, setBlob, reset } from './campaign.reducer';
import { ICampaign } from 'app/shared/model/connectService/campaign.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICampaignUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CampaignUpdate = (props: ICampaignUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { campaignEntity, loading, updating } = props;

  const { campaignDetails } = campaignEntity;

  const handleClose = () => {
    props.history.push('/campaign' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  const onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => props.setBlob(name, data, contentType), isAnImage);
  };

  const clearBlob = name => () => {
    props.setBlob(name, undefined, undefined);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.startDate = convertDateTimeToServer(values.startDate);
    values.endDate = convertDateTimeToServer(values.endDate);
    values.createdDate = convertDateTimeToServer(values.createdDate);

    if (errors.length === 0) {
      const entity = {
        ...campaignEntity,
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
          <h2 id="connectGatewayApp.connectServiceCampaign.home.createOrEditLabel">
            <Translate contentKey="connectGatewayApp.connectServiceCampaign.home.createOrEditLabel">Create or edit a Campaign</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : campaignEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="campaign-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="campaign-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="campaignDetailsLabel" for="campaign-campaignDetails">
                  <Translate contentKey="connectGatewayApp.connectServiceCampaign.campaignDetails">Campaign Details</Translate>
                </Label>
                <AvInput
                  id="campaign-campaignDetails"
                  type="textarea"
                  name="campaignDetails"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="campaignTypeLabel" for="campaign-campaignType">
                  <Translate contentKey="connectGatewayApp.connectServiceCampaign.campaignType">Campaign Type</Translate>
                </Label>
                <AvInput
                  id="campaign-campaignType"
                  type="select"
                  className="form-control"
                  name="campaignType"
                  value={(!isNew && campaignEntity.campaignType) || 'ADHOC'}
                >
                  <option value="ADHOC">{translate('connectGatewayApp.CampaignType.ADHOC')}</option>
                  <option value="BIRTHDAY">{translate('connectGatewayApp.CampaignType.BIRTHDAY')}</option>
                  <option value="MEMBERSHIP_LAPSE">{translate('connectGatewayApp.CampaignType.MEMBERSHIP_LAPSE')}</option>
                  <option value="MEMBERSHIP_RENEWAL">{translate('connectGatewayApp.CampaignType.MEMBERSHIP_RENEWAL')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="startDateLabel" for="campaign-startDate">
                  <Translate contentKey="connectGatewayApp.connectServiceCampaign.startDate">Start Date</Translate>
                </Label>
                <AvInput
                  id="campaign-startDate"
                  type="datetime-local"
                  className="form-control"
                  name="startDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.campaignEntity.startDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="endDateLabel" for="campaign-endDate">
                  <Translate contentKey="connectGatewayApp.connectServiceCampaign.endDate">End Date</Translate>
                </Label>
                <AvInput
                  id="campaign-endDate"
                  type="datetime-local"
                  className="form-control"
                  name="endDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.campaignEntity.endDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="createdByLabel" for="campaign-createdBy">
                  <Translate contentKey="connectGatewayApp.connectServiceCampaign.createdBy">Created By</Translate>
                </Label>
                <AvField
                  id="campaign-createdBy"
                  type="text"
                  name="createdBy"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="createdDateLabel" for="campaign-createdDate">
                  <Translate contentKey="connectGatewayApp.connectServiceCampaign.createdDate">Created Date</Translate>
                </Label>
                <AvInput
                  id="campaign-createdDate"
                  type="datetime-local"
                  className="form-control"
                  name="createdDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.campaignEntity.createdDate)}
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/campaign" replace color="info">
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
  campaignEntity: storeState.campaign.entity,
  loading: storeState.campaign.loading,
  updating: storeState.campaign.updating,
  updateSuccess: storeState.campaign.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CampaignUpdate);
