import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import AppHeader from "../../components/AppHeader";

const mockedUseNavigate = jest.fn(() => {});

describe("employer authority", () => {
  beforeEach(() => {
    jest
      .spyOn(require("../../services/authService"), "getAuthorities")
      .mockImplementation(() => ["Employer"]);

    jest
      .spyOn(require("../../services/authService"), "isConnected")
      .mockImplementation(() => true);

    jest
      .spyOn(require("react-router-dom"), "useNavigate")
      .mockImplementation(() => mockedUseNavigate);
  });

  it("should display buttons", () => {
    render(
      <AppHeader setShowNotifications={() => {}} showNotifications={true} />
    );
    const linkElement = screen.getByText(/employer.addOfferButton/i);
    expect(linkElement).not.toBeUndefined();
  });

  it("should redirect to proper page on offer button click", async () => {
    render(
      <AppHeader setShowNotifications={() => {}} showNotifications={true} />
    );
    const linkElement = screen.getByText(/employer.addOfferButton/i);
    expect(linkElement).not.toBeUndefined();

    fireEvent.click(linkElement);

    await waitFor(() => {
      expect(mockedUseNavigate).toHaveBeenCalledWith("employer/offers/new");
    });
  });
});

describe("manager authority", () => {
  beforeEach(() => {
    jest
      .spyOn(require("../../services/authService"), "getAuthorities")
      .mockImplementation(() => ["Manager"]);

    jest
      .spyOn(require("../../services/authService"), "isConnected")
      .mockImplementation(() => true);

    jest
      .spyOn(require("react-router-dom"), "useNavigate")
      .mockImplementation(() => mockedUseNavigate);
  });

  it("should display buttons", () => {
    render(
      <AppHeader setShowNotifications={() => {}} showNotifications={true} />
    );
    const linkElement = screen.getByText(/manager.seeOffersButton/i);
    expect(linkElement).not.toBeUndefined();
  });

  it("should redirect to proper page on offer button click", async () => {
    render(
      <AppHeader setShowNotifications={() => {}} showNotifications={true} />
    );
    const linkElement = screen.getByText(/manager.seeOffersButton/i);
    expect(linkElement).not.toBeUndefined();

    fireEvent.click(linkElement);

    await waitFor(() => {
      expect(mockedUseNavigate).toHaveBeenCalledWith("manager/offers");
    });
  });
});

describe("unconnected user", () => {
  beforeEach(() => {
    jest
      .spyOn(require("../../services/authService"), "isConnected")
      .mockImplementation(() => false);

    jest
      .spyOn(require("react-router-dom"), "useNavigate")
      .mockImplementation(() => mockedUseNavigate);
  });

  it("should display proper buttons", () => {
    render(
      <AppHeader setShowNotifications={() => {}} showNotifications={true} />
    );
    const employerSignupButton = screen.getByText(/signup.signup/i);
    expect(employerSignupButton).not.toBeUndefined();

    const signinButton = screen.getByText(/signin/i);
    expect(signinButton).not.toBeUndefined();
  });

  it("should redirect to proper page on signup button click", async () => {
    render(
      <AppHeader setShowNotifications={() => {}} showNotifications={true} />
    );
    const employerSignupButton = screen.getByText(/signup.signup/i);
    expect(employerSignupButton).not.toBeUndefined();

    fireEvent.click(employerSignupButton);

    await waitFor(() => {
      expect(mockedUseNavigate).toHaveBeenCalledWith("/signup/employer");
    });
  });

  it("should redirect to proper page on signin button click", async () => {
    render(
      <AppHeader setShowNotifications={() => {}} showNotifications={true} />
    );
    const signinButton = screen.getByText(/signin/i);
    expect(signinButton).not.toBeUndefined();

    fireEvent.click(signinButton);

    await waitFor(() => {
      expect(mockedUseNavigate).toHaveBeenCalledWith("/login");
    });
  });
});

describe("connected user", () => {
  beforeEach(() => {
    jest
      .spyOn(require("../../services/authService"), "isConnected")
      .mockImplementation(() => true);

    jest
      .spyOn(require("react-router-dom"), "useNavigate")
      .mockImplementation(() => mockedUseNavigate);
  });

  it("should display proper buttons", () => {
    render(
      <AppHeader setShowNotifications={() => {}} showNotifications={true} />
    );
    const linkElement = screen.getByText(/logout/i);
    expect(linkElement).not.toBeUndefined();
  });

  it("should redirect to proper page on logout button click", async () => {
    render(
      <AppHeader setShowNotifications={() => {}} showNotifications={true} />
    );

    const linkElement = screen.getByText(/logout/i);
    fireEvent.click(linkElement);

    await waitFor(() => {
      expect(mockedUseNavigate).toHaveBeenCalledWith("/login");
    });
  });
});
