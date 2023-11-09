import { render, screen } from "@testing-library/react";
import ConnectedRoute from "../../components/ConnectedRoute";
import "@testing-library/jest-dom/extend-expect";

it("should display the children when the user is connected and is connected route", () => {
  jest
    .spyOn(require("../../services/authService"), "isConnected")
    .mockReturnValue(true);

  render(
    <ConnectedRoute isConnectedRoute={true}>
      <div>children</div>
    </ConnectedRoute>
  );

  const children = screen.getByText(/children/i);
  expect(children).toBeInTheDocument();
});

it("should display the children when the user is not connected and is not connected route", () => {
  jest
    .spyOn(require("../../services/authService"), "isConnected")
    .mockReturnValue(false);

  render(
    <ConnectedRoute isConnectedRoute={false}>
      <div>children</div>
    </ConnectedRoute>
  );

  const children = screen.getByText(/children/i);
  expect(children).toBeInTheDocument();
});

it("should not display the children when the user is not connected and is connected route", () => {
  jest
    .spyOn(require("../../services/authService"), "isConnected")
    .mockReturnValue(false);

  render(
    <ConnectedRoute isConnectedRoute={true}>
      <div>children</div>
    </ConnectedRoute>
  );

  const children = screen.queryByText(/children/i);
  expect(children).not.toBeInTheDocument();
});
