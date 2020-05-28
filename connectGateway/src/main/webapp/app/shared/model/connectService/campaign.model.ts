import { Moment } from 'moment';
import { CampaignType } from 'app/shared/model/enumerations/campaign-type.model';

export interface ICampaign {
  id?: number;
  campaignDetails?: any;
  campaignType?: CampaignType;
  startDate?: string;
  endDate?: string;
  createdBy?: string;
  createdDate?: string;
}

export const defaultValue: Readonly<ICampaign> = {};
