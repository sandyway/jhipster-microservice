import { Moment } from 'moment';
import { Channel } from 'app/shared/model/enumerations/channel.model';
import { IntentTypes } from 'app/shared/model/enumerations/intent-types.model';

export interface IConnectState {
  id?: number;
  campaignId?: string;
  recipientId?: string;
  connectDetails?: any;
  channel?: Channel;
  intentId?: string;
  intentType?: IntentTypes;
  connectEvent?: any;
  createdDate?: string;
  updatedDate?: string;
}

export const defaultValue: Readonly<IConnectState> = {};
