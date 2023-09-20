export interface User {
  id?: number;
  firstName?: string;
  lastName?: string;
  email: string;
  phone?: string;
  address?: string;
  password: string;
}

export interface Employer extends User {
  organization: string;
  position: string;
  extension: string;
}
