import "@testing-library/jest-dom/extend-expect";
import { fireEvent, render, screen } from "@testing-library/react";
import ManagerApplicationsList from "../../../components/ManagerApplicationsList";
import { application } from "../testUtils/testUtils";

it("should render table", () => {
  render(<ManagerApplicationsList applications={[application]} />);

  expect(screen.getByText(application.offer!.title)).toBeInTheDocument();
  expect(
    screen.getByText(application.offer!.employer.organisation)
  ).toBeInTheDocument();

  expect(
    screen.getByText(
      `${application.cv!.student!.firstName} ${
        application.cv!.student!.lastName
      }`
    )
  ).toBeInTheDocument();

  expect(screen.getByText(/manager.createContractButton/i)).toBeInTheDocument();
});

it("should render table with empty applications list", () => {
  render(<ManagerApplicationsList applications={[]} />);

  expect(
    screen.getByText(/applicationsList.noAcceptedApplications/i)
  ).toBeInTheDocument();
});

it("should display no applications when search missmatch", () => {
  render(<ManagerApplicationsList applications={[application]} />);

  const searchInput = screen.getByPlaceholderText(
    /applicationsList.SearchPlaceholder/i
  );

  expect(searchInput).toBeInTheDocument();

  fireEvent.change(searchInput, { target: { value: "test" } });

  expect(
    screen.getByText(/applicationsList.noAcceptedApplications/i)
  ).toBeInTheDocument();
});
