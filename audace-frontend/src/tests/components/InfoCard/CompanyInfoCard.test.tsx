import { render, screen } from "@testing-library/react";
import { CompanyInfoCard } from "../../../components/InfoCard";
import { employer } from "../testUtils/testUtils";
import "@testing-library/jest-dom/extend-expect";

it("should display employer values", () => {
  render(<CompanyInfoCard employer={employer} />);

  expect(screen.getByText(/infoCard.employer.title/i)).toBeInTheDocument();
  expect(screen.getByText(/infoCard.employer.name/i)).toBeInTheDocument();
  expect(screen.getByText(/infoCard.employer.email/i)).toBeInTheDocument();
  expect(screen.getByText(/infoCard.employer.phone/i)).toBeInTheDocument();
  expect(screen.getByText(/infoCard.employer.address/i)).toBeInTheDocument();
});

it("should not display employer values when employer is undefined", () => {
  render(<CompanyInfoCard employer={undefined} />);

  expect(
    screen.queryByText(/infoCard.employer.title/i)
  ).not.toBeInTheDocument();
  expect(screen.queryByText(/infoCard.employer.name/i)).not.toBeInTheDocument();
  expect(
    screen.queryByText(/infoCard.employer.email/i)
  ).not.toBeInTheDocument();
  expect(
    screen.queryByText(/infoCard.employer.phone/i)
  ).not.toBeInTheDocument();
  expect(
    screen.queryByText(/infoCard.employer.address/i)
  ).not.toBeInTheDocument();
});
