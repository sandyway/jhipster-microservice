import { Moment } from 'moment';

export interface ICampaignFlow {
  id?: number;
  campaignId?: string;
  templateFlowId?: string;
  createdDate?: string;
  createdBy?: string;
}

export const defaultValue: Readonly<ICampaignFlow> = {};
