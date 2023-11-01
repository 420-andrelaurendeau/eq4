import { fireEvent, render, screen } from "@testing-library/react";
import Signup from "../../../components/Signup";
import { User } from "../../../model/user";
import { AxiosResponse } from "axios";

const allErrors = [
  "signup.errors.email",
  "signup.errors.password",
  "signup.errors.passwordConfirmation",
  "signup.errors.firstName",
  "signup.errors.lastName",
  "signup.errors.address",
  "signup.errors.city",
  "signup.errors.postalCode",
  "signup.errors.phone",
];

it("should render default form values", () => {
  render(
    <Signup
      handleSubmit={(user: User) => new Promise((resolve) => {})}
      errors={[]}
      setErrors={(errors: string[]) => {}}
    />
  );
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

  expect(emailInput).not.toBeUndefined();
  expect(passwordInput).not.toBeUndefined();
  expect(passwordConfirmationInput).not.toBeUndefined();
  expect(firstNameInput).not.toBeUndefined();
  expect(lastNameInput).not.toBeUndefined();
  expect(addressInput).not.toBeUndefined();
  expect(cityInput).not.toBeUndefined();
  expect(postalCodeInput).not.toBeUndefined();
  expect(phoneInput).not.toBeUndefined();
});

it("should display errors on faulty submit assuming submit already done", async () => {
  const { rerender } = render(
    <Signup
      handleSubmit={(user: User) => new Promise((resolve) => {})}
      errors={[]}
      setErrors={(errors: string[]) => {}}
    />
  );

  const submitButton = screen.getByText(/signup.signup/i);

  fireEvent.click(submitButton);

  rerender(
    <Signup
      handleSubmit={(user: User) => new Promise((resolve) => {})}
      errors={allErrors}
      setErrors={(errors: string[]) => {}}
    />
  );

  await screen.findByText(/signup.errors.email/i);
  await screen.findByText(/signup.errors.password$/i);
  await screen.findByText(/signup.errors.passwordConfirmation/i);
  await screen.findByText(/signup.errors.firstName/i);
  await screen.findByText(/signup.errors.lastName/i);
  await screen.findByText(/signup.errors.address/i);
  await screen.findByText(/signup.errors.city/i);
  await screen.findByText(/signup.errors.postalCode/i);
  await screen.findByText(/signup.errors.phone/i);
});

it("should properly submit a form", async () => {
  render(
    <Signup
      handleSubmit={(user: User) => {
        return new Promise((resolve) => {
          resolve({} as unknown as AxiosResponse);
        });
      }}
      errors={[]}
      setErrors={(errors: string[]) => {}}
    />
  );

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
});

it("should display unexpected error on failed submit request", async () => {
  render(
    <Signup
      handleSubmit={(user: User) => {
        return new Promise((resolve, reject) => {
          reject({ code: "ERR_NETWORK" });
        });
      }}
      errors={[]}
      setErrors={(errors: string[]) => {}}
    />
  );

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

  await screen.findByText(/signup.errors.network/i);
});
