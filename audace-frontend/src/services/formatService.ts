import { Session } from "../model/session";

export const formatDate = (date: Date) => {
  const newDate = new Date(date);
  const year = newDate.getFullYear();
  const month = newDate.getMonth() + 1;
  const day = newDate.getDate();

  return `${day}/${month}/${year}`;
};

export const determineSessionSeason = (chosenSession: Session): string => {
  const startDate = new Date(chosenSession.startDate);

  if (isFall(startDate)) return "sessionSelector.fall";
  if (isWinter(startDate)) return "sessionSelector.winter";
  if (isSpring(startDate)) return "sessionSelector.spring";

  return "sessionSelector.summer";
};

const isFall = (startDate: Date): boolean => {
  const month = startDate.getMonth();

  if (month < 8 || month > 11) return false;
  if (month === 11) return startDate.getDate() < 21;
  if (month === 8) return startDate.getDate() >= 23;

  return true;
};

const isWinter = (startDate: Date): boolean => {
  const month = startDate.getMonth();

  if (month > 2 && month < 11) return false;
  if (month === 11) return startDate.getDate() >= 21;
  if (month === 2) return startDate.getDate() <= 20;

  return true;
};

const isSpring = (startDate: Date): boolean => {
  const month = startDate.getMonth();

  if (month < 2 || month > 5) return false;
  if (month === 5) return startDate.getDate() < 21;
  if (month === 2) return startDate.getDate() >= 19;

  return true;
};

export const getEndDateYear = (chosenSession: Session): number => {
  const endDate = new Date(chosenSession.endDate);

  return endDate.getFullYear();
};
