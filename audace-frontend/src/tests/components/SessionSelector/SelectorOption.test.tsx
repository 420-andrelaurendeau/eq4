import { render, screen } from "@testing-library/react";
import SelectorOption from "../../../components/SessionSelector/SelectorOption";
import { Session } from "../../../model/session";
import "@testing-library/jest-dom/extend-expect";

const session: Session = {
  id: 1,
  startDate: new Date(),
  endDate: new Date(),
};

it("should display a fall session", () => {
  jest
    .spyOn(require("../../../services/formatService"), "determineSessionSeason")
    .mockImplementation(() => "Fall");

  jest
    .spyOn(require("../../../services/formatService"), "getEndDateYear")
    .mockImplementation(() => "2021");

  render(<SelectorOption session={session} />);

  const linkElement = screen.getByText(/Fall 2021/i);
  expect(linkElement).not.toBeUndefined();
});

it("should display a winter session", () => {
  jest
    .spyOn(require("../../../services/formatService"), "determineSessionSeason")
    .mockImplementation(() => "Winter");

  jest
    .spyOn(require("../../../services/formatService"), "getEndDateYear")
    .mockImplementation(() => "2021");

  render(<SelectorOption session={session} />);

  const linkElement = screen.getByText(/Winter 2021/i);
  expect(linkElement).not.toBeUndefined();
});

it("should display a summer session", () => {
  jest
    .spyOn(require("../../../services/formatService"), "determineSessionSeason")
    .mockImplementation(() => "Summer");

  jest
    .spyOn(require("../../../services/formatService"), "getEndDateYear")
    .mockImplementation(() => "2021");

  render(<SelectorOption session={session} />);

  const linkElement = screen.getByText(/Summer 2021/i);
  expect(linkElement).not.toBeUndefined();
});

it("should be active", () => {
  jest
    .spyOn(require("../../../services/formatService"), "determineSessionSeason")
    .mockImplementation(() => "Summer");

  jest
    .spyOn(require("../../../services/formatService"), "getEndDateYear")
    .mockImplementation(() => "2021");

  jest
    .spyOn(
      require("../../../contextsholders/providers/SessionContextHolder"),
      "useSessionContext"
    )
    .mockImplementation(() => {
      return {
        chosenSession: session,
        setChosenSession: () => {},
        sessions: [],
      };
    });

  render(<SelectorOption session={session} />);

  const linkElement = screen.getByText(/Summer 2021/i);
  expect(linkElement).not.toBeUndefined();
  expect(linkElement).toHaveClass("dropdown-item active");
});
