import { fireEvent, render, screen } from "@testing-library/react";
import StudentSignup from "../../../components/Signup/StudentSignup";
import { AxiosResponse } from "axios";

jest.mock("react-router", () => ({
  useParams: () => ({ depCode: "test" }),
}));

it("should display default form values", () => {
  render(<StudentSignup />);

  const studentIdInput = screen.getByLabelText(/signup.studentId/i);

  expect(studentIdInput).not.toBeUndefined();
});

it("should display errors on faulty submit", async () => {
  render(<StudentSignup />);

  const submitButton = screen.getByText(/signup.signup/i);

  fireEvent.click(submitButton);

  const studentIdError = await screen.findByText(/signup.errors.studentId/i);

  expect(studentIdError).not.toBeUndefined();
});

it("should properly submit a form", async () => {
  jest
    .spyOn(require("../../../services/signupService"), "studentSignup")
    .mockImplementation(() =>
      Promise.resolve({ status: 200 } as AxiosResponse)
    );

  render(<StudentSignup />);

  const studentIdInput = screen.getByLabelText(/signup.studentId/i);

  fireEvent.change(studentIdInput, { target: { value: "test" } });
});

it("should display an error on submit failure", async () => {
  jest
    .spyOn(require("../../../services/signupService"), "studentSignup")
    .mockImplementation(() => Promise.reject({ code: "ERR_NETWORK" }));

  render(<StudentSignup />);

  const studentIdInput = screen.getByLabelText(/signup.studentId/i);
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

  fireEvent.change(studentIdInput, { target: { value: "12345" } });
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

  const error = await screen.findByText(/signup.errors.network/i);

  expect(error).not.toBeUndefined();
});