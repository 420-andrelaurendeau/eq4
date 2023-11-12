import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import LoginForm from "../../components/Login";
import "@testing-library/jest-dom/extend-expect";

const mockedUseNavigate = jest.fn(() => {});

it("should render the inputs", () => {
  render(<LoginForm />);

  expect(screen.getByLabelText(/login.identification/i)).toBeInTheDocument();
  expect(screen.getByLabelText(/login.password/i)).toBeInTheDocument();

  expect(screen.getByRole("button", { name: /signin/i })).toBeInTheDocument();
});

it("should show an error when the identification is empty", async () => {
  render(<LoginForm />);

  const submitButton = screen.getByRole("button", { name: /signin/i });

  fireEvent.click(submitButton);

  expect(
    await screen.findByText(/login.errors.emptyIdentification/i)
  ).toBeInTheDocument();
});

it("should show an error when the password is empty", async () => {
  render(<LoginForm />);

  const submitButton = screen.getByRole("button", { name: /signin/i });

  fireEvent.click(submitButton);

  expect(
    await screen.findByText(/login.errors.emptyPassword/i)
  ).toBeInTheDocument();
});

describe("invalid credentials", () => {
  beforeEach(() => {
    jest
      .spyOn(require("../../services/authService"), "login")
      .mockResolvedValue({});

    jest
      .spyOn(require("../../services/authService"), "authenticate")
      .mockImplementation(() => {});

    jest
      .spyOn(require("../../services/authService"), "getUserId")
      .mockImplementation(() => "1");

    jest
      .spyOn(require("react-router-dom"), "useNavigate")
      .mockImplementation(() => mockedUseNavigate);
  });

  it("should display an error", async () => {
    jest
      .spyOn(require("../../services/authService"), "login")
      .mockImplementation(() =>
        Promise.reject({
          response: { status: 401 },
        })
      );

    render(<LoginForm />);

    const identificationInput = screen.getByLabelText(/login.identification/i);

    fireEvent.change(identificationInput, {
      target: { value: "test" },
    });

    const passwordInput = screen.getByLabelText(/login.password/i);

    fireEvent.change(passwordInput, {
      target: { value: "test" },
    });

    const submitButton = screen.getByRole("button", { name: /signin/i });

    fireEvent.click(submitButton);

    expect(
      await screen.findByText(/login.errors.invalidCredentials/i)
    ).toBeInTheDocument();
  });

  it("should navigate to pageNotFound when there is an error fetching the user", async () => {
    jest
      .spyOn(require("../../services/userService"), "getUserById")
      .mockRejectedValue({
        response: { status: 401 },
      });

    render(<LoginForm />);

    const identificationInput = screen.getByLabelText(/login.identification/i);

    fireEvent.change(identificationInput, {
      target: { value: "test" },
    });

    const passwordInput = screen.getByLabelText(/login.password/i);

    fireEvent.change(passwordInput, {
      target: { value: "test" },
    });

    const submitButton = screen.getByRole("button", { name: /signin/i });

    fireEvent.click(submitButton);

    await waitFor(() =>
      expect(mockedUseNavigate).toHaveBeenCalledWith("/pageNotFound")
    );
  });

  it("should navigate to pageNotFound when there is an error fetching the user's id", async () => {
    jest
      .spyOn(require("../../services/authService"), "login")
      .mockResolvedValue({});

    jest
      .spyOn(require("../../services/authService"), "authenticate")
      .mockImplementation(() => {});

    jest
      .spyOn(require("../../services/authService"), "getUserId")
      .mockImplementation(() => null);

    jest
      .spyOn(require("react-router-dom"), "useNavigate")
      .mockImplementation(() => mockedUseNavigate);

    render(<LoginForm />);

    const identificationInput = screen.getByLabelText(/login.identification/i);

    fireEvent.change(identificationInput, {
      target: { value: "test" },
    });

    const passwordInput = screen.getByLabelText(/login.password/i);

    fireEvent.change(passwordInput, {
      target: { value: "test" },
    });

    const submitButton = screen.getByRole("button", { name: /signin/i });

    fireEvent.click(submitButton);

    await waitFor(() =>
      expect(mockedUseNavigate).toHaveBeenCalledWith("/pageNotFound")
    );
  });
});

describe("valid credentials", () => {
  beforeEach(() => {
    jest
      .spyOn(require("../../services/authService"), "login")
      .mockResolvedValue({});

    jest
      .spyOn(require("../../services/authService"), "getUserId")
      .mockImplementation(() => "1");

    jest
      .spyOn(require("react-router-dom"), "useNavigate")
      .mockImplementation(() => mockedUseNavigate);
  });

  it("should navigate to the student home page", async () => {
    jest
      .spyOn(require("../../services/userService"), "getUserById")
      .mockResolvedValue({ data: { type: "student" } });

    render(<LoginForm />);

    const identificationInput = screen.getByLabelText(/login.identification/i);

    fireEvent.change(identificationInput, {
      target: { value: "test" },
    });

    const passwordInput = screen.getByLabelText(/login.password/i);

    fireEvent.change(passwordInput, {
      target: { value: "test" },
    });

    const submitButton = screen.getByRole("button", { name: /signin/i });

    fireEvent.click(submitButton);

    await waitFor(() =>
      expect(mockedUseNavigate).toHaveBeenCalledWith("/student")
    );
  });

  it("should navigate to the employer home page", async () => {
    jest
      .spyOn(require("../../services/userService"), "getUserById")
      .mockResolvedValue({ data: { type: "employer" } });

    render(<LoginForm />);

    const identificationInput = screen.getByLabelText(/login.identification/i);

    fireEvent.change(identificationInput, {
      target: { value: "test" },
    });

    const passwordInput = screen.getByLabelText(/login.password/i);

    fireEvent.change(passwordInput, {
      target: { value: "test" },
    });

    const submitButton = screen.getByRole("button", { name: /signin/i });

    fireEvent.click(submitButton);

    await waitFor(() =>
      expect(mockedUseNavigate).toHaveBeenCalledWith("/employer")
    );
  });

  it("should navigate to the manager home page", async () => {
    jest
      .spyOn(require("../../services/userService"), "getUserById")
      .mockResolvedValue({ data: { type: "manager" } });

    render(<LoginForm />);

    const identificationInput = screen.getByLabelText(/login.identification/i);

    fireEvent.change(identificationInput, {
      target: { value: "test" },
    });

    const passwordInput = screen.getByLabelText(/login.password/i);

    fireEvent.change(passwordInput, {
      target: { value: "test" },
    });

    const submitButton = screen.getByRole("button", { name: /signin/i });

    fireEvent.click(submitButton);

    await waitFor(() =>
      expect(mockedUseNavigate).toHaveBeenCalledWith("/manager")
    );
  });

  it("should navigate to pageNotFound when the user type is unknown", async () => {
    jest
      .spyOn(require("../../services/userService"), "getUserById")
      .mockResolvedValue({ data: { type: "unknown" } });

    render(<LoginForm />);

    const identificationInput = screen.getByLabelText(/login.identification/i);

    fireEvent.change(identificationInput, {
      target: { value: "test" },
    });

    const passwordInput = screen.getByLabelText(/login.password/i);

    fireEvent.change(passwordInput, {
      target: { value: "test" },
    });

    const submitButton = screen.getByRole("button", { name: /signin/i });

    fireEvent.click(submitButton);

    await waitFor(() =>
      expect(mockedUseNavigate).toHaveBeenCalledWith("/pageNotFound")
    );
  });
});

it("should display userCreatedNotif when the user is created", () => {
  jest
    .spyOn(require("react-router-dom"), "useLocation")
    .mockImplementation(() => ({
      pathname: "/login/createdUser",
    }));

  render(<LoginForm />);

  expect(screen.getByText(/login.userCreated/i)).toBeInTheDocument();
});

it("should display expiredSessionNotif when the session is expired", () => {
  jest
    .spyOn(require("react-router-dom"), "useLocation")
    .mockImplementation(() => ({
      pathname: "/login/disconnected",
    }));

  jest
    .spyOn(require("../../services/authService"), "isConnected")
    .mockReturnValue(false);

  jest
    .spyOn(require("../../services/authService"), "hasSessionExpiredRecently")
    .mockReturnValue(true);

  render(<LoginForm />);

  expect(screen.getByText(/login.errors.sessionExpired/i)).toBeInTheDocument();
});
