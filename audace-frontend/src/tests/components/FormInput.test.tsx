import { render, screen } from "@testing-library/react";
import FormInput from "../../components/FormInput";

it("should take our label as a value", () => {
  render(
    <FormInput
      label={"test"}
      value={""}
      onChange={jest.fn()}
      errors={[]}
      formError={""}
      controlId={"yaint"}
    />
  );
  const linkElement = screen.getByLabelText(/test/i);
  expect(linkElement).not.toBeUndefined();
});
