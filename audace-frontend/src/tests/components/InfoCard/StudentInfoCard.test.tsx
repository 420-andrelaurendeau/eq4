import { render, screen } from "@testing-library/react";
import { StudentInfoCard } from "../../../components/InfoCard";
import { student } from "../testUtils/testUtils";
import "@testing-library/jest-dom/extend-expect";

it("should render student values", () => {
  render(<StudentInfoCard student={student} />);

  expect(screen.getByText(/infoCard.student.title/i)).toBeInTheDocument();
  expect(screen.getByText(/infoCard.student.email/i)).toBeInTheDocument();
  expect(screen.getByText(/infoCard.student.phone/i)).toBeInTheDocument();
  expect(screen.getByText(/infoCard.student.address/i)).toBeInTheDocument();
});

it("should not render student values when student is undefined", () => {
  render(<StudentInfoCard student={undefined} />);

  expect(screen.queryByText(/infoCard.student.title/i)).not.toBeInTheDocument();
  expect(screen.queryByText(/infoCard.student.email/i)).not.toBeInTheDocument();
  expect(screen.queryByText(/infoCard.student.phone/i)).not.toBeInTheDocument();
  expect(
    screen.queryByText(/infoCard.student.address/i)
  ).not.toBeInTheDocument();
});
