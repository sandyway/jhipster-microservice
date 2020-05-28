import { Moment } from 'moment';
import { Channel } from 'app/shared/model/enumerations/channel.model';
import { CampaignType } from 'app/shared/model/enumerations/campaign-type.model';

export interface IConnectIntent {
  id?: number;
  intentId?: string;
  flowId?: string;
  connectChannel?: Channel;
  description?: string;
  connectType?: CampaignType;
  messages?: any;
  reminder?: any;
  createdDate?: string;
  updatedDate?: string;
  createdBy?: string;
}

export const defaultValue: Readonly<IConnectIntent> = {};
