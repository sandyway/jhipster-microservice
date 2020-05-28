import { Moment } from 'moment';

export interface IConnectConfig {
  id?: number;
  userId?: string;
  facebook?: any;
  viber?: any;
  createdDate?: string;
}

export const defaultValue: Readonly<IConnectConfig> = {};
