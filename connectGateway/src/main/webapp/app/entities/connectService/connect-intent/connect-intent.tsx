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
import { getEntities } from './connect-intent.reducer';
import { IConnectIntent } from 'app/shared/model/connectService/connect-intent.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';

export interface IConnectIntentProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const ConnectIntent = (props: IConnectIntentProps) => {
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

  const { connectIntentList, match, loading, totalItems } = props;
  return (
    <div>
      <h2 id="connect-intent-heading">
        <Translate contentKey="connectGatewayApp.connectServiceConnectIntent.home.title">Connect Intents</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="connectGatewayApp.connectServiceConnectIntent.home.createLabel">Create new Connect Intent</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {connectIntentList && connectIntentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('intentId')}>
                  <Translate contentKey="connectGatewayApp.connectServiceConnectIntent.intentId">Intent Id</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('flowId')}>
                  <Translate contentKey="connectGatewayApp.connectServiceConnectIntent.flowId">Flow Id</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('connectChannel')}>
                  <Translate contentKey="connectGatewayApp.connectServiceConnectIntent.connectChannel">Connect Channel</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('description')}>
                  <Translate contentKey="connectGatewayApp.connectServiceConnectIntent.description">Description</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('connectType')}>
                  <Translate contentKey="connectGatewayApp.connectServiceConnectIntent.connectType">Connect Type</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('messages')}>
                  <Translate contentKey="connectGatewayApp.connectServiceConnectIntent.messages">Messages</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('reminder')}>
                  <Translate contentKey="connectGatewayApp.connectServiceConnectIntent.reminder">Reminder</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdDate')}>
                  <Translate contentKey="connectGatewayApp.connectServiceConnectIntent.createdDate">Created Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('updatedDate')}>
                  <Translate contentKey="connectGatewayApp.connectServiceConnectIntent.updatedDate">Updated Date</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('createdBy')}>
                  <Translate contentKey="connectGatewayApp.connectServiceConnectIntent.createdBy">Created By</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {connectIntentList.map((connectIntent, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${connectIntent.id}`} color="link" size="sm">
                      {connectIntent.id}
                    </Button>
                  </td>
                  <td>{connectIntent.intentId}</td>
                  <td>{connectIntent.flowId}</td>
                  <td>
                    <Translate contentKey={`connectGatewayApp.Channel.${connectIntent.connectChannel}`} />
                  </td>
                  <td>{connectIntent.description}</td>
                  <td>
                    <Translate contentKey={`connectGatewayApp.CampaignType.${connectIntent.connectType}`} />
                  </td>
                  <td>{connectIntent.messages}</td>
                  <td>{connectIntent.reminder}</td>
                  <td>
                    {connectIntent.createdDate ? (
                      <TextFormat type="date" value={connectIntent.createdDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {connectIntent.updatedDate ? (
                      <TextFormat type="date" value={connectIntent.updatedDate} format={APP_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{connectIntent.createdBy}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${connectIntent.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${connectIntent.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`${match.url}/${connectIntent.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="connectGatewayApp.connectServiceConnectIntent.home.notFound">No Connect Intents found</Translate>
            </div>
          )
        )}
      </div>
      {props.totalItems ? (
        <div className={connectIntentList && connectIntentList.length > 0 ? '' : 'd-none'}>
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

const mapStateToProps = ({ connectIntent }: IRootState) => ({
  connectIntentList: connectIntent.entities,
  loading: connectIntent.loading,
  totalItems: connectIntent.totalItems,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ConnectIntent);
