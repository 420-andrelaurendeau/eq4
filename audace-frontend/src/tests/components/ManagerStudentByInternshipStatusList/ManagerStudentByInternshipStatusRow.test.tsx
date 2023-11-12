import { render, screen } from "@testing-library/react";
import ManagerStudentByInternshipStatusRow from "../../../components/ManagerStudentByInternshipStatusList/ManagerStudentByInternshipStatusRow";
import { student } from "../testUtils/testUtils";
import "@testing-library/jest-dom/extend-expect";

it("should render student values", () => {
  render(
    <ManagerStudentByInternshipStatusRow student={student} status="pending" />
  );

  expect(screen.getByText(/Doe, John/i)).toBeInTheDocument();
  expect(screen.getByText(/123456789/i)).toBeInTheDocument();
  expect(screen.getByText(/Computer Science/i)).toBeInTheDocument();
  expect(screen.getByText(/studentsByInternship.row.statusValues.pending/i)).toBeInTheDocument();
});
