import { render, screen } from "@testing-library/react";
import ManagerStudentByInternshipStatusRow from "../../../components/ManagerStudentByInternshipStatusList/ManagerStudentByInternshipStatusRow";
import { student } from "../testUtils/testUtils";
import "@testing-library/jest-dom/extend-expect";
import { Table } from "react-bootstrap";

it("should render student values", () => {
  render(
    <Table>
      <tbody>
        <ManagerStudentByInternshipStatusRow
          student={student}
          status="pending"
        />
      </tbody>
    </Table>
  );

  expect(screen.getByText(/Doe, John/i)).toBeInTheDocument();
  expect(screen.getByText(/123456789/i)).toBeInTheDocument();
  expect(screen.getByText(/Computer Science/i)).toBeInTheDocument();
  expect(
    screen.getByText(/studentsByInternship.row.statusValues.pending/i)
  ).toBeInTheDocument();
});
