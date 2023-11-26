import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import CustomToggle from "../../../components/SessionSelector/CustomToggle";
import "@testing-library/jest-dom/extend-expect";

it("should render a button", () => {
  render(<CustomToggle onClick={() => {}}>Click me</CustomToggle>);
  const button = screen.getByText(/Click me/i);
  expect(button).toBeInTheDocument();
});

it("should call onClick when clicked", async () => {
  const onClick = jest.fn();

  render(<CustomToggle onClick={onClick}>Click me</CustomToggle>);

  const button = screen.getByText(/Click me/i);
  fireEvent.click(button);

  await waitFor(() => expect(onClick).toHaveBeenCalledTimes(1));
});
