import { CV } from "./cv";
import { Offer } from "./offer";

export interface Application {
  id?: number;
  offer?: Offer;
  cv?: CV;
  applicationStatus?: ApplicationStatus;
}
export enum ApplicationStatus {
  PENDING = "PENDING",
  ACCEPTED = "ACCEPTED",
  REFUSED = "REFUSED"
}

export default Application;
