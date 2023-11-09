import { render, screen } from "@testing-library/react";
import CvsList from "../../../components/CVsList";
import { cv } from "./CvsListTestUtils";
import "@testing-library/jest-dom/extend-expect";

it("should display default values", () => {
  render(<CvsList cvs={[cv]} error={""} />);

  const studentName = screen.getByText(/john doe/i);
  expect(studentName).toBeInTheDocument();

  const cvName = screen.getByText(/The best CV/i);
  expect(cvName).toBeInTheDocument();
});
