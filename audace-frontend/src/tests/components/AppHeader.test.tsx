import { render, screen } from "@testing-library/react";
import AppHeader from "../../components/AppHeader";

it("should say Audace in the header", () => {
  render(<AppHeader />);
  const linkElement = screen.getByText(/Audace/i);
  expect(linkElement).not.toBeUndefined();
});

it("should have an I18N button", () => {
  render(<AppHeader />);
  const linkElement = screen.getByText(/langcode/i);
  expect(linkElement).not.toBeUndefined();
});

it("should have a login button", () => {
  render(<AppHeader />);
  const linkElement = screen.getByText(/signin/i);
  expect(linkElement).not.toBeUndefined();
});

it("should have a signup button", () => {
  render(<AppHeader />);
  const linkElement = screen.getByText(/signup.signup/i);
  expect(linkElement).not.toBeUndefined();
});

it("Should display student buttons when student authority", () => {
  jest
    .spyOn(require("../../services/authService"), "getAuthorities")
    .mockImplementation(() => ["Student"]);

  jest
    .spyOn(require("../../services/authService"), "isConnected")
    .mockImplementation(() => true);

  render(<AppHeader />);
  const linkElement = screen.getByText(/student.seeOffersButton/i);
  expect(linkElement).not.toBeUndefined();

  const linkElement2 = screen.getByText(/upload.CvFormTitle/i);
  expect(linkElement2).not.toBeUndefined();
});

it("should display employer buttons when employer authority", () => {
  jest
    .spyOn(require("../../services/authService"), "getAuthorities")
    .mockImplementation(() => ["Employer"]);

  jest
    .spyOn(require("../../services/authService"), "isConnected")
    .mockImplementation(() => true);

  render(<AppHeader />);
  const linkElement = screen.getByText(/employer.addOfferButton/i);
  expect(linkElement).not.toBeUndefined();

  const linkElement2 = screen.getByText(/employer.seeOffersButton/i);
  expect(linkElement2).not.toBeUndefined();
});

it("should display manager buttons when manager authority", () => {
  jest
    .spyOn(require("../../services/authService"), "getAuthorities")
    .mockImplementation(() => ["Manager"]);

  jest
    .spyOn(require("../../services/authService"), "isConnected")
    .mockImplementation(() => true);

  render(<AppHeader />);
  const linkElement = screen.getByText(/manager.seeOffersButton/i);
  expect(linkElement).not.toBeUndefined();
});

it("should display proper buttons when connected", () => {
  jest
    .spyOn(require("../../services/authService"), "isConnected")
    .mockImplementation(() => true);

  render(<AppHeader />);
  const linkElement = screen.getByText(/logout/i);
  expect(linkElement).not.toBeUndefined();
});
