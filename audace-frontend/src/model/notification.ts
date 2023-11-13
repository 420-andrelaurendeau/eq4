import Application from "./application";
import { CV } from "./cv";
import { Offer } from "./offer";
import { User } from "./user";

export interface Notification {
  id?: number;
  user : User;
  seen : boolean;
  offer? : Offer;
  application? : Application;
  cv? : CV;
  type : String;
  cause : NotificationCause;
}

enum NotificationCause {
  CREATED = "CREATED",
  UPDATED = "UPDATED",
  DELETED = "DELETED",
  EXPIRED = "EXPIRED"
}

export default Notification;
