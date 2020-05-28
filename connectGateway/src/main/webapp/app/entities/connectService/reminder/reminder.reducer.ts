import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IReminder, defaultValue } from 'app/shared/model/connectService/reminder.model';

export const ACTION_TYPES = {
  FETCH_REMINDER_LIST: 'reminder/FETCH_REMINDER_LIST',
  FETCH_REMINDER: 'reminder/FETCH_REMINDER',
  CREATE_REMINDER: 'reminder/CREATE_REMINDER',
  UPDATE_REMINDER: 'reminder/UPDATE_REMINDER',
  DELETE_REMINDER: 'reminder/DELETE_REMINDER',
  SET_BLOB: 'reminder/SET_BLOB',
  RESET: 'reminder/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IReminder>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ReminderState = Readonly<typeof initialState>;

// Reducer

export default (state: ReminderState = initialState, action): ReminderState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_REMINDER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_REMINDER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_REMINDER):
    case REQUEST(ACTION_TYPES.UPDATE_REMINDER):
    case REQUEST(ACTION_TYPES.DELETE_REMINDER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_REMINDER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_REMINDER):
    case FAILURE(ACTION_TYPES.CREATE_REMINDER):
    case FAILURE(ACTION_TYPES.UPDATE_REMINDER):
    case FAILURE(ACTION_TYPES.DELETE_REMINDER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_REMINDER_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_REMINDER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_REMINDER):
    case SUCCESS(ACTION_TYPES.UPDATE_REMINDER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_REMINDER):
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

const apiUrl = 'services/connectservice/api/reminders';

// Actions

export const getEntities: ICrudGetAllAction<IReminder> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_REMINDER_LIST,
    payload: axios.get<IReminder>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IReminder> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_REMINDER,
    payload: axios.get<IReminder>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IReminder> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_REMINDER,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IReminder> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_REMINDER,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IReminder> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_REMINDER,
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
