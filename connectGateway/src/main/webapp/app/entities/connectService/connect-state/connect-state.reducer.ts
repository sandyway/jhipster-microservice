import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IConnectState, defaultValue } from 'app/shared/model/connectService/connect-state.model';

export const ACTION_TYPES = {
  FETCH_CONNECTSTATE_LIST: 'connectState/FETCH_CONNECTSTATE_LIST',
  FETCH_CONNECTSTATE: 'connectState/FETCH_CONNECTSTATE',
  CREATE_CONNECTSTATE: 'connectState/CREATE_CONNECTSTATE',
  UPDATE_CONNECTSTATE: 'connectState/UPDATE_CONNECTSTATE',
  DELETE_CONNECTSTATE: 'connectState/DELETE_CONNECTSTATE',
  SET_BLOB: 'connectState/SET_BLOB',
  RESET: 'connectState/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IConnectState>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ConnectStateState = Readonly<typeof initialState>;

// Reducer

export default (state: ConnectStateState = initialState, action): ConnectStateState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CONNECTSTATE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CONNECTSTATE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CONNECTSTATE):
    case REQUEST(ACTION_TYPES.UPDATE_CONNECTSTATE):
    case REQUEST(ACTION_TYPES.DELETE_CONNECTSTATE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CONNECTSTATE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CONNECTSTATE):
    case FAILURE(ACTION_TYPES.CREATE_CONNECTSTATE):
    case FAILURE(ACTION_TYPES.UPDATE_CONNECTSTATE):
    case FAILURE(ACTION_TYPES.DELETE_CONNECTSTATE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONNECTSTATE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONNECTSTATE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CONNECTSTATE):
    case SUCCESS(ACTION_TYPES.UPDATE_CONNECTSTATE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CONNECTSTATE):
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

const apiUrl = 'services/connectservice/api/connect-states';

// Actions

export const getEntities: ICrudGetAllAction<IConnectState> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CONNECTSTATE_LIST,
    payload: axios.get<IConnectState>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IConnectState> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CONNECTSTATE,
    payload: axios.get<IConnectState>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IConnectState> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CONNECTSTATE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IConnectState> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CONNECTSTATE,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IConnectState> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CONNECTSTATE,
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
