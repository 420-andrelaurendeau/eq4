import "@testing-library/jest-dom/extend-expect";
import { render, screen } from "@testing-library/react";
import AuthorizedRoute from "../../components/AuthorizedRoute";
import { Authority } from "../../model/auth";

it("should display the component if the user is logged in and has the correct role", () => {
  jest
    .spyOn(require("../../services/authService"), "getAuthorities")
    .mockReturnValue([Authority.EMPLOYER, Authority.USER]);

  jest
    .spyOn(require("../../services/authService"), "isConnected")
    .mockReturnValue(true);

  render(
    <AuthorizedRoute requiredAuthority={Authority.EMPLOYER}>
      <div>children</div>
    </AuthorizedRoute>
  );

  const children = screen.getByText(/children/i);
  expect(children).toBeInTheDocument();
});

it("should not display the component if the user is logged in and has not the correct role", () => {
  jest
    .spyOn(require("../../services/authService"), "getAuthorities")
    .mockReturnValue([]);

  render(
    <AuthorizedRoute requiredAuthority={Authority.USER}>
      <div>children</div>
    </AuthorizedRoute>
  );

  const children = screen.queryByText(/children/i);
  expect(children).not.toBeInTheDocument();
});
