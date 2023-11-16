import { render, screen } from "@testing-library/react";
import CvsList from "../../../components/CVsList";
import "@testing-library/jest-dom/extend-expect";
import { cv } from "../testUtils/testUtils";

it("should display default values", () => {
  render(<CvsList cvs={[cv]} error={""} />);

  const studentName = screen.getByText(/john doe/i);
  expect(studentName).toBeInTheDocument();

  const cvName = screen.getByText(/The best CV/i);
  expect(cvName).toBeInTheDocument();
});
