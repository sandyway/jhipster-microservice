import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import {
  byteSize,
  Translate,
  ICrudGetAllAction,
  TextFormat,
  getSortState,
  IPaginationBaseState,
  JhiPagination,
  JhiItemCount,
} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './connect-state.reducer';
import { IConnectState } from 'app/shared/model/connectService/connect-state.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IConnectStateProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const ConnectState = (props: IConnectStateProps) => {
  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE), props.location.search)
  );

  const getAllEntities = () => {
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get('sort');
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const { connectStateList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="connect-state-heading">
        <Translate contentKey="connectGatewayApp.connectServiceConnectState.home.title">Connect States</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="connectGatewayApp.connectServiceConnectState.home.createLabel">Create new Connect State</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {connectStateList && connectStateList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('campaignId')}>
                  <Translate contentKey="connectGatewayApp.connectServiceConnectState.campaignId">Campaign Id</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('recipientId')}>
                  <Translate contentKey="connectGatewayApp.connectServiceConnectState.recipientId">Recipient Id</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('connectDetails')}>
                  <Translate contentKey="connectGatewayApp.connectServiceConnectState.connectDetails">Connect Details</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('channel')}>
                  <Translate contentKey="connectGatewayApp.connectServiceConnectState.channel">Channel</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('intentId')}>
                  <Translate contentKey="connectGatewayApp.connectServiceConnectState.intentId">Intent Id</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('intentType')}>
                  <Translate contentKey="connectGatewayApp.connectServiceConnectState.intentType">Intent Type</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('connectEvent')}>
                  <Translate contentKey="connectGatewayApp.connectServiceConnectState.connectEvent">Connect Event</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdDate')}>
                  <Translate contentKey="connectGatewayApp.connectServiceConnectState.createdDate">Created Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedDate')}>
                  <Translate contentKey="connectGatewayApp.connectServiceConnectState.updatedDate">Updated Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {connectStateList.map((connectState, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${connectState.id}`} color="link" size="sm">
                      {connectState.id}
                    </Button>
                  </td>
                  <td>{connectState.campaignId}</td>
                  <td>{connectState.recipientId}</td>
                  <td>{connectState.connectDetails}</td>
                  <td>
                    <Translate contentKey={`connectGatewayApp.Channel.${connectState.channel}`} />
                  </td>
                  <td>{connectState.intentId}</td>
                  <td>
                    <Translate contentKey={`connectGatewayApp.IntentTypes.${connectState.intentType}`} />
                  </td>
                  <td>{connectState.connectEvent}</td>
                  <td>
                    {connectState.createdDate ? <TextFormat type="date" value={connectState.createdDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {connectState.updatedDate ? <TextFormat type="date" value={connectState.updatedDate} format={APP_DATE_FORMAT} /> : null}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${connectState.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${connectState.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${connectState.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="connectGatewayApp.connectServiceConnectState.home.notFound">No Connect States found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={connectStateList && connectStateList.length > 0 ? '' : 'd-none'}>
          <Row className="justify-content-center">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </Row>
          <Row className="justify-content-center">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={props.totalItems}
            />
          </Row>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

const mapStateToProps = ({ connectState }: IRootState) => ({
  connectStateList: connectState.entities,
  loading: connectState.loading,
  totalItems: connectState.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ConnectState);
