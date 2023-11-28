import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import SessionSelector from "../../../components/SessionSelector";
import "@testing-library/jest-dom/extend-expect";
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

describe("toggle button", () => {
  it("should render a placeholder", () => {
    render(<SessionSelector />);
    const unselectedSession = screen.getByText(
      /sessionSelector.selectSession/i
    );
    expect(unselectedSession).toBeInTheDocument();
  });

  it("should render the chosen session", () => {
    jest
      .spyOn(
        require("../../../contextsholders/providers/SessionContextHolder"),
        "useSessionContext"
      )
      .mockImplementation(() => ({
        chosenSession: sessions[0],
        setChosenSession: () => {},
        sessions,
      }));

    render(<SessionSelector />);

    const selectedSession = screen.getByText(/sessionSelector.fall 2021/i);
    expect(selectedSession).toBeInTheDocument();
  });
});

describe("dropdown menu", () => {
  beforeEach(() => {
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
  });

  it("should render a dropdown item", async () => {
    render(<SessionSelector />);
    const unselectedSession = screen.getByText(
      /sessionSelector.selectSession/i
    );
    fireEvent.click(unselectedSession);

    const dropdownItems = await screen.findAllByText(
      /sessionSelector.fall 2021/i
    );

    dropdownItems.forEach((item) => {
      expect(item).toBeInTheDocument();
    });
  });

  it("should select a session", async () => {
    const setChosenSession = jest.fn();
    jest
      .spyOn(
        require("../../../contextsholders/providers/SessionContextHolder"),
        "useSessionContext"
      )
      .mockImplementation(() => {
        return {
          chosenSession: undefined,
          setChosenSession,
          sessions,
        };
      });

    render(<SessionSelector />);
    const unselectedSession = screen.getByText(
      /sessionSelector.selectSession/i
    );
    fireEvent.click(unselectedSession);

    const dropdownItems = await screen.findAllByText(
      /sessionSelector.fall 2021/i
    );

    fireEvent.click(dropdownItems[0]);

    expect(setChosenSession).toHaveBeenCalledTimes(1);
  });
});

it("should call seeApplications", async () => {
  const seeApplications = jest.fn();

  render(<SessionSelector seeApplications={seeApplications} />);
  const unselectedSession = screen.getByText(/sessionSelector.selectSession/i);
  fireEvent.click(unselectedSession);

  const dropdownItems = await screen.findAllByText(
    /sessionSelector.fall 2021/i
  );

  fireEvent.click(dropdownItems[0]);

  await waitFor(() => {
    expect(seeApplications).toHaveBeenCalledTimes(1);
  });
});
