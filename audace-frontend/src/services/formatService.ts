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
  if (isSummer(startDate)) return "sessionSelector.summer";

  return "sessionSelector.summer";
};

const isFall = (startDate: Date): boolean => {
  const month = startDate.getMonth();

  if (month < 8) return false;
  if (month === 12) return startDate.getDate() < 22;
  if (month === 8) return startDate.getDate() >= 20;

  return true;
};

const isWinter = (startDate: Date): boolean => {
  const month = startDate.getMonth();

  if (month > 1 && month > 5) return false;
  if (month === 5) return startDate.getDate() <= 19;
  if (month === 1) return startDate.getDate() >= 20;

  return true;
};

const isSummer = (startDate: Date): boolean => {
  const month = startDate.getMonth();

  if (month < 5 || month > 8) return false;
  if (month === 8) return startDate.getDate() < 20;
  if (month === 5) return startDate.getDate() >= 20;

  return true;
};

export const getEndDateYear = (chosenSession: Session): number => {
  const endDate = new Date(chosenSession.endDate);

  return endDate.getFullYear();
};
