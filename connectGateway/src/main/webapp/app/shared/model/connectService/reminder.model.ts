import { Moment } from 'moment';
import { Channel } from 'app/shared/model/enumerations/channel.model';

export interface IReminder {
  id?: number;
  recipientId?: string;
  campaignId?: string;
  intentId?: string;
  channel?: Channel;
  connectDetails?: any;
  done?: boolean;
  executions?: number;
  createdDate?: string;
  updatedDate?: string;
}

export const defaultValue: Readonly<IReminder> = {
  done: false,
};
