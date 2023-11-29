import { fireEvent, render, screen } from "@testing-library/react";
import ApplicationRow from "../../../components/ApplicationsList/ApplicationRow";
import { application, offer } from "../testUtils/testUtils";
import "@testing-library/jest-dom/extend-expect";
import { UserType } from "../../../model/user";

jest.mock("../../../components/PDFViewer", () => () => <div>We are here</div>);

it("should display the application values", () => {
  render(<ApplicationRow application={application} offer={offer} />);

  expect(screen.getByText(/The best cv/i)).toBeInTheDocument();
  expect(screen.getByText(/organisation/i)).toBeInTheDocument();
});

it("should display employer buttons if user is employer", () => {
  jest
    .spyOn(require("../../../services/authService"), "getUserType")
    .mockImplementation(() => UserType.Employer);

  render(<ApplicationRow application={application} offer={offer} />);

  expect(
    screen.getByText(/employerApplicationsList.acceptButton/i)
  ).toBeInTheDocument();
});

it("should not display employer buttons if user is not employer", () => {
  jest
    .spyOn(require("../../../services/authService"), "getUserType")
    .mockImplementation(() => UserType.Student);

  render(<ApplicationRow application={application} />);

  expect(
    screen.queryByText(/employerApplicationsList.acceptButton/i)
  ).not.toBeInTheDocument();
});

it("should open the cv modal on cv link click", async () => {
  render(<ApplicationRow application={application} />);

  const viewMoreLink = screen.getByText(/cvsList.viewMore/i);

  fireEvent.click(viewMoreLink);

  expect(await screen.findByText(/we are here/i)).toBeInTheDocument();
});

it("should close the cv modal on close button click", async () => {
  render(<ApplicationRow application={application} />);

  const viewMoreLink = screen.getByText(/cvsList.viewMore/i);

  fireEvent.click(viewMoreLink);

  const closeButton = screen.getByLabelText(/close/i);

  fireEvent.click(closeButton);

  expect(screen.queryByText(/we are here/i)).not.toBeInTheDocument();
});
