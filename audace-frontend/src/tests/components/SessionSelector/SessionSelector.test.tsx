import { fireEvent, render, screen } from "@testing-library/react";
import SessionSelector from "../../../components/SessionSelector";
import { Session } from "../../../model/session";

const sessions: Session[] = [
  {
    id: 1,
    startDate: new Date("2021-01-01"),
    endDate: new Date("2021-01-02"),
  },
  {
    id: 2,
    startDate: new Date("2021-01-03"),
    endDate: new Date("2021-01-04"),
  },
];

jest
  .spyOn(
    require("../../../contextsholders/providers/SessionContextHolder"),
    "useSessionContext"
  )
  .mockImplementation(() => {
    return {
      chosenSession: undefined,
      setChosenSession: () => {},
      sessions,
    };
  });

it("should display dropdown", () => {
  render(<SessionSelector />);
  const linkElement = screen.getByText(/sessionSelector.selectSession/i);
  expect(linkElement).not.toBeUndefined();
});
