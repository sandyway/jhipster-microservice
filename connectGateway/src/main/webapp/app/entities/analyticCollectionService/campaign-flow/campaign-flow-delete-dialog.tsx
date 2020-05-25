import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate, ICrudGetAction, ICrudDeleteAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { ICampaignFlow } from 'app/shared/model/analyticCollectionService/campaign-flow.model';
import { IRootState } from 'app/shared/reducers';
import { getEntity, deleteEntity } from './campaign-flow.reducer';

export interface ICampaignFlowDeleteDialogProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CampaignFlowDeleteDialog = (props: ICampaignFlowDeleteDialogProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const handleClose = () => {
    props.history.push('/campaign-flow' + props.location.search);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const confirmDelete = () => {
    props.deleteEntity(props.campaignFlowEntity.id);
  };

  const { campaignFlowEntity } = props;
  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose}>
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="connectGatewayApp.analyticCollectionServiceCampaignFlow.delete.question">
        <Translate
          contentKey="connectGatewayApp.analyticCollectionServiceCampaignFlow.delete.question"
          interpolate={{ id: campaignFlowEntity.id }}
        >
          Are you sure you want to delete this CampaignFlow?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button id="jhi-confirm-delete-campaignFlow" color="danger" onClick={confirmDelete}>
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

const mapStateToProps = ({ campaignFlow }: IRootState) => ({
  campaignFlowEntity: campaignFlow.entity,
  updateSuccess: campaignFlow.updateSuccess,
});

const mapDispatchToProps = { getEntity, deleteEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CampaignFlowDeleteDialog);
