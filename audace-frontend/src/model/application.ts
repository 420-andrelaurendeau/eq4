import { CV } from "./cv";
import { Offer } from "./offer";

export interface Application {
  id?: number;
  offer?: Offer;
  cv?: CV;
  applicationStatus?: ApplicationStatus;
}

export enum ApplicationStatus {
  ACCEPTED = "ACCEPTED",
  PENDING = "PENDING",
  REFUSED = "REFUSED",
}

export default Application;
