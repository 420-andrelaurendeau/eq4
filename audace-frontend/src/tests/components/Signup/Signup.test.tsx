import { fireEvent, render, screen } from "@testing-library/react";
import Signup from "../../../components/Signup";
import { User } from "../../../model/user";

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
