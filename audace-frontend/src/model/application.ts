import { CV } from "./cv";
import { Offer } from "./offer";
import { Student } from "./user";

export interface Application {
  id?: number;
  student?: Student;
  offer?: Offer;
  cv?: CV;
}

export default Application;
