import { render, screen } from "@testing-library/react";
import AppHeader from "../../components/AppHeader";

jest.mock("axios", () => {
  return {
    create: jest.fn(() => ({
      get: jest.fn(),
      interceptors: {
        request: { use: jest.fn(), eject: jest.fn() },
        response: { use: jest.fn(), eject: jest.fn() },
      },
    })),
  };
});
const mockedUsedNavigate = jest.fn();

jest.mock("react-router-dom", () => ({
  ...(jest.requireActual("react-router-dom") as any),
  useNavigate: () => mockedUsedNavigate,
}));

it("should say Audace in the header", () => {
  render(<AppHeader />);
  const linkElement = screen.getByText(/Audace/i);
  expect(linkElement).not.toBeUndefined();
});

it("should have an I18N button", () => {
  render(<AppHeader />);
  const linkElement = screen.getByText(/FR/i);
  expect(linkElement).not.toBeUndefined();
});

it("should have a login button", () => {
  render(<AppHeader />);
  const linkElement = screen.getByText(/Se connecter/i);
  expect(linkElement).not.toBeUndefined();
});

it("should have a signup button", () => {
  render(<AppHeader />);
  const linkElement = screen.getByText(/S'inscrire/i);
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
  const linkElement = screen.getByText(/Voir les offres/i);
  expect(linkElement).not.toBeUndefined();

  const linkElement2 = screen.getByText(/Téléverser votre CV/i);
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
  const linkElement = screen.getByText(/Créer une offre/i);
  expect(linkElement).not.toBeUndefined();

  const linkElement2 = screen.getByText(/Voir les offres/i);
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
  const linkElement = screen.getByText(/Voir les offres/i);
  expect(linkElement).not.toBeUndefined();
});

it("should display proper buttons when connected", () => {
  jest
    .spyOn(require("../../services/authService"), "isConnected")
    .mockImplementation(() => true);

  render(<AppHeader />);
  const linkElement = screen.getByText(/Se déconnecter/i);
  expect(linkElement).not.toBeUndefined();
});
