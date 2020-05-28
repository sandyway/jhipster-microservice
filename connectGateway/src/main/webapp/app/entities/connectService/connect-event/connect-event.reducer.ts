import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IConnectEvent, defaultValue } from 'app/shared/model/connectService/connect-event.model';

export const ACTION_TYPES = {
  FETCH_CONNECTEVENT_LIST: 'connectEvent/FETCH_CONNECTEVENT_LIST',
  FETCH_CONNECTEVENT: 'connectEvent/FETCH_CONNECTEVENT',
  CREATE_CONNECTEVENT: 'connectEvent/CREATE_CONNECTEVENT',
  UPDATE_CONNECTEVENT: 'connectEvent/UPDATE_CONNECTEVENT',
  DELETE_CONNECTEVENT: 'connectEvent/DELETE_CONNECTEVENT',
  SET_BLOB: 'connectEvent/SET_BLOB',
  RESET: 'connectEvent/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IConnectEvent>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ConnectEventState = Readonly<typeof initialState>;

// Reducer

export default (state: ConnectEventState = initialState, action): ConnectEventState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CONNECTEVENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CONNECTEVENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CONNECTEVENT):
    case REQUEST(ACTION_TYPES.UPDATE_CONNECTEVENT):
    case REQUEST(ACTION_TYPES.DELETE_CONNECTEVENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CONNECTEVENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CONNECTEVENT):
    case FAILURE(ACTION_TYPES.CREATE_CONNECTEVENT):
    case FAILURE(ACTION_TYPES.UPDATE_CONNECTEVENT):
    case FAILURE(ACTION_TYPES.DELETE_CONNECTEVENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONNECTEVENT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONNECTEVENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CONNECTEVENT):
    case SUCCESS(ACTION_TYPES.UPDATE_CONNECTEVENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CONNECTEVENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.SET_BLOB: {
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType,
        },
      };
    }
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'services/connectservice/api/connect-events';

// Actions

export const getEntities: ICrudGetAllAction<IConnectEvent> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CONNECTEVENT_LIST,
    payload: axios.get<IConnectEvent>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IConnectEvent> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CONNECTEVENT,
    payload: axios.get<IConnectEvent>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IConnectEvent> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CONNECTEVENT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IConnectEvent> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CONNECTEVENT,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IConnectEvent> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CONNECTEVENT,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType,
  },
});

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
