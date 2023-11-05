import Application from "./application";
import { CV } from "./cv";
import { Offer } from "./offer";
import { User } from "./user";

export interface Notification<T> {
  id?: number;
  user : User;
  seen : boolean;
  content : T; //TODO : Still a disgrace
  type : String;
}
export interface NotificationOffer extends Notification<Offer> {
}
export interface NotificationCv extends Notification<CV> {
}
export interface NotificationApplication extends Notification<Application> {
    
}
export default Notification;
