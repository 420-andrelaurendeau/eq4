import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import EmployerSignup from "../../../components/Signup/EmployerSignup";
import { AxiosResponse } from "axios";

it("should display default form values", () => {
  render(<EmployerSignup />);

  const organisationInput = screen.getByLabelText(/signup.companyNameEntry/i);
  const positionInput = screen.getByLabelText(/signup.positionEntry/i);

  expect(organisationInput).not.toBeUndefined();
  expect(positionInput).not.toBeUndefined();
});

it("should display errors on faulty submit", async () => {
  render(<EmployerSignup />);

  const submitButton = screen.getByText(/signup.signup/i);

  fireEvent.click(submitButton);

  const organisationError = await screen.findByText(
    /signup.errors.organisation/i
  );
  const positionError = await screen.findByText(/signup.errors.position/i);

  expect(organisationError).not.toBeUndefined();
  expect(positionError).not.toBeUndefined();
});

it("should properly submit a form", async () => {
  jest
    .spyOn(require("../../../services/signupService"), "employerSignup")
    .mockImplementation(() =>
      Promise.resolve({ status: 200 } as AxiosResponse)
    );

  render(<EmployerSignup />);

  const organisationInput = screen.getByLabelText(/signup.companyNameEntry/i);
  const positionInput = screen.getByLabelText(/signup.positionEntry/i);
  const emailInput = screen.getByLabelText(/signup.emailEntry/i);

  fireEvent.change(organisationInput, { target: { value: "test" } });
  fireEvent.change(positionInput, { target: { value: "test" } });
  fireEvent.change(emailInput, { target: { value: "" } });
});

it("should display an error on submit failure", async () => {
  jest
    .spyOn(require("../../../services/signupService"), "employerSignup")
    .mockImplementation(() => Promise.reject({ code: "ERR_NETWORK" }));

  render(<EmployerSignup />);

  const organisationInput = screen.getByLabelText(/signup.companyNameEntry/i);
  const positionInput = screen.getByLabelText(/signup.positionEntry/i);
  const emailInput = screen.getByLabelText(/signup.emailEntry/i);
  const passwordInput = screen.getByLabelText(/signup.password$/i);
  const passwordConfirmationInput = screen.getByLabelText(
    /signup.passwordConfirmation/i
  );
  const firstNameInput = screen.getByLabelText(/signup.firstNameEntry/i);
  const lastNameInput = screen.getByLabelText(/signup.lastNameEntry/i);
  const addressInput = screen.getByLabelText(/signup.addressEntry/i);
  const cityInput = screen.getByLabelText(/signup.cityEntry/i);
  const postalCodeInput = screen.getByLabelText(/signup.postalCodeEntry/i);
  const phoneInput = screen.getByLabelText(/signup.phoneEntry/i);
  const submitButton = screen.getByText(/signup.signup/i);

  fireEvent.change(organisationInput, { target: { value: "test" } });
  fireEvent.change(positionInput, { target: { value: "test" } });
  fireEvent.change(emailInput, { target: { value: "asd@hotmail.com" } });
  fireEvent.change(passwordInput, { target: { value: "Aa12345!" } });
  fireEvent.change(passwordConfirmationInput, {
    target: { value: "Aa12345!" },
  });
  fireEvent.change(firstNameInput, { target: { value: "John" } });
  fireEvent.change(lastNameInput, { target: { value: "Doe" } });
  fireEvent.change(addressInput, { target: { value: "1234 Main St" } });
  fireEvent.change(cityInput, { target: { value: "Montreal" } });
  fireEvent.change(postalCodeInput, { target: { value: "h1h 1h1" } });
  fireEvent.change(phoneInput, { target: { value: "1234567890" } });

  fireEvent.click(submitButton);

  await waitFor(() => screen.findByText(/signup.errors.network/i));
});
