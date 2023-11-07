import { fireEvent, render, screen } from "@testing-library/react";
import StudentButtons from "../../../../components/OffersList/OfferRow/OfferButtons/StudentButtons";
import { Offer, OfferStatus } from "../../../../model/offer";
import "@testing-library/jest-dom/extend-expect";

const offer: Offer = {
  id: 1,
  title: "title",
  description: "description",
  internshipStartDate: new Date(),
  internshipEndDate: new Date(),
  offerEndDate: new Date(),
  availablePlaces: 1,
  department: {
    id: 1,
    code: "code",
    name: "name",
  },
  employer: {
    id: 1,
    firstName: "firstName",
    lastName: "lastName",
    email: "email",
    phone: "phone",
    address: "address",
    password: "password",
    type: "employer",
    organisation: "organisation",
    position: "position",
    extension: "extension",
  },
  offerStatus: OfferStatus.PENDING,
};

it("should render apply button", () => {
  render(<StudentButtons disabled={false} offer={offer} />);
  const applyButton = screen.getByText("offersList.applyButton");
  expect(applyButton).toBeInTheDocument();
});

describe("button disabling", () => {
  it("should disable apply button when props is true", () => {
    render(<StudentButtons disabled={true} offer={offer} />);
    const applyButton = screen.getByText("offersList.applyButton");
    expect(applyButton).toBeDisabled();
  });

  it("should disable apply button when applications is undefined", () => {
    jest
      .spyOn(
        require("../../../../contextsholders/providers/ApplicationsContextHolder"),
        "useApplicationContext"
      )
      .mockImplementation(() => ({
        applications: undefined,
        setApplications: () => {},
      }));

    render(<StudentButtons disabled={false} offer={offer} />);
    const applyButton = screen.getByText("offersList.applyButton");
    expect(applyButton).toBeDisabled();
  });

  it("should disable apply button when cvs is undefined", () => {
    jest
      .spyOn(
        require("../../../../contextsholders/providers/CVContextHolder"),
        "useCVContext"
      )
      .mockImplementation(() => ({
        cvs: undefined,
        setCvs: () => {},
      }));

    render(<StudentButtons disabled={false} offer={offer} />);
    const applyButton = screen.getByText("offersList.applyButton");
    expect(applyButton).toBeDisabled();
  });

  it("should disable when cvs is empty", () => {
    jest
      .spyOn(
        require("../../../../contextsholders/providers/CVContextHolder"),
        "useCVContext"
      )
      .mockImplementation(() => ({
        cvs: [],
        setCvs: () => {},
      }));

    render(<StudentButtons disabled={false} offer={offer} />);
    const applyButton = screen.getByText("offersList.applyButton");
    expect(applyButton).toBeDisabled();
  });

  it("should disable when any application offers match", () => {
    jest
      .spyOn(
        require("../../../../contextsholders/providers/ApplicationsContextHolder"),
        "useApplicationContext"
      )
      .mockImplementation(() => ({
        applications: [{ id: 1, offer: offer, cv: {} }],
        setApplications: () => {},
      }));

    jest
      .spyOn(
        require("../../../../contextsholders/providers/CVContextHolder"),
        "useCVContext"
      )
      .mockImplementation(() => ({
        cvs: [
          { id: 1, title: "title", description: "description", student: {} },
        ],
        setCvs: () => {},
      }));

    render(<StudentButtons disabled={false} offer={offer} />);
    const applyButton = screen.getByText("offersList.applyButton");
    expect(applyButton).toBeDisabled();
  });

  it("should not disable when no application offers match", () => {
    jest
      .spyOn(
        require("../../../../contextsholders/providers/ApplicationsContextHolder"),
        "useApplicationContext"
      )
      .mockImplementation(() => ({
        applications: [],
        setApplications: () => {},
      }));

    jest
      .spyOn(
        require("../../../../contextsholders/providers/CVContextHolder"),
        "useCVContext"
      )
      .mockImplementation(() => ({
        cvs: [
          { id: 1, title: "title", description: "description", student: {} },
        ],
        setCvs: () => {},
      }));

    render(<StudentButtons disabled={false} offer={offer} />);
    const applyButton = screen.getByText("offersList.applyButton");
    expect(applyButton).not.toBeDisabled();
  });
});

describe("application handling", () => {
  it("should show success message when application is successful", async () => {
    jest
      .spyOn(require("../../../../services/authService"), "getUserId")
      .mockImplementation(() => "1");

    jest
      .spyOn(
        require("../../../../contextsholders/providers/ApplicationsContextHolder"),
        "useApplicationContext"
      )
      .mockImplementation(() => ({
        applications: [],
        setApplications: () => {},
      }));

    jest
      .spyOn(
        require("../../../../contextsholders/providers/CVContextHolder"),
        "useCVContext"
      )
      .mockImplementation(() => ({
        cvs: [
          { id: 1, title: "title", description: "description", student: {} },
        ],
        setCvs: () => {},
      }));

    jest
      .spyOn(
        require("../../../../services/applicationService"),
        "studentApplyToOffer"
      )
      .mockImplementation(() => Promise.resolve());

    render(<StudentButtons disabled={false} offer={offer} />);
    const applyButton = screen.getByText("offersList.applyButton");
    fireEvent.click(applyButton);
    const message = await screen.findByText(
      /offersList.applicationMessageSuccess/i
    );
    expect(message).toBeInTheDocument();
  });

  it("should show error message when application is unsuccessful", async () => {
    jest
      .spyOn(
        require("../../../../contextsholders/providers/ApplicationsContextHolder"),
        "useApplicationContext"
      )
      .mockImplementation(() => ({
        applications: [],
        setApplications: () => {},
      }));

    jest
      .spyOn(
        require("../../../../contextsholders/providers/CVContextHolder"),
        "useCVContext"
      )
      .mockImplementation(() => ({
        cvs: [
          { id: 1, title: "title", description: "description", student: {} },
        ],
        setCvs: () => {},
      }));

    jest
      .spyOn(
        require("../../../../services/applicationService"),
        "studentApplyToOffer"
      )
      .mockImplementation(() => Promise.reject());

    render(<StudentButtons disabled={false} offer={offer} />);

    const applyButton = screen.getByText("offersList.applyButton");
    fireEvent.click(applyButton);

    const message = await screen.findByText(
      /offersList.applicationMessageFailure/i
    );

    expect(message).toBeInTheDocument();
  });
});
