import { render, screen } from "@testing-library/react";
import ApplicationsList from "../../../components/ApplicationsList";
import { application } from "../testUtils/testUtils";
import "@testing-library/jest-dom/extend-expect";

it("should display the table headers", () => {
  render(<ApplicationsList applications={[application]} error="" />);

  expect(screen.getByText(/applicationsList.offerTitle/i)).toBeInTheDocument();
  expect(screen.getByText(/applicationsList.cv/i)).toBeInTheDocument();
  expect(
    screen.getByText(/applicationsList.organization/i)
  ).toBeInTheDocument();
  expect(screen.getByText(/applicationsList.status/i)).toBeInTheDocument();
});
