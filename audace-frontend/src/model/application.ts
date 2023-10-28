import { CV } from "./cv";
import { Offer } from "./offer";
import { Student } from "./user";

export interface Application {
  id?: number;
  student?: Student;
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
