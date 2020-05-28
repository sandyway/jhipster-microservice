import { Moment } from 'moment';

export interface IConnectEvent {
  id?: number;
  analyticId?: string;
  connectEvent?: any;
  reference?: any;
  createdDate?: string;
}

export const defaultValue: Readonly<IConnectEvent> = {};
