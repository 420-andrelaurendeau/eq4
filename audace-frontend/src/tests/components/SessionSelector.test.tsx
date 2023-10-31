import { fireEvent, render, screen } from "@testing-library/react";
import SessionSelector from "../../components/SessionSelector";
import { Session } from "../../model/session";

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
    require("../../contextsholders/providers/SessionContextHolder"),
    "useSessionContext"
  )
  .mockImplementation(() => {
    return {
      chosenSession: undefined,
      setChosenSession: () => {},
      sessions,
    };
  });

it("should contain two sessions", async () => {
  render(<SessionSelector />);

  const toggle = screen.getByText(/Select a session/i);

  fireEvent.click(toggle);

  const option1 = await screen.findByText(/31\/12\/2020/i);
  const option2 = await screen.findByText(/2\/1\/2021/i);

  expect(option1).not.toBeUndefined();
  expect(option2).not.toBeUndefined();
});

it("should set the chosen session", async () => {
  render(<SessionSelector />);

  const toggle = screen.getByText(/Select a session/i);

  fireEvent.click(toggle);

  const option1 = await screen.findByText(/31\/12\/2020/i);

  fireEvent.click(option1);

  const chosen = await screen.findByText(/31\/12\/2020/i);

  expect(chosen).not.toBeUndefined();
});

it("should change the chosen session", async () => {
  render(<SessionSelector />);

  const toggle = screen.getByText(/Select a session/i);

  fireEvent.click(toggle);

  const option1 = await screen.findByText(/31\/12\/2020/i);

  fireEvent.click(option1);

  const chosen = await screen.findByText(/31\/12\/2020/i);

  expect(chosen).not.toBeUndefined();

  fireEvent.click(toggle);

  const option2 = await screen.findByText(/2\/1\/2021/i);

  fireEvent.click(option2);

  const chosen2 = await screen.findByText(/2\/1\/2021/i);

  expect(chosen2).not.toBeUndefined();
});
