import { render, screen } from "@testing-library/react";
import CvModal from "../../../components/CVsList/CvRow/CvModal";
import "@testing-library/jest-dom/extend-expect";
import { cv } from "../testUtils/testUtils";

jest.mock("../../../components/PDFViewer", () => () => <div>PDFViewer</div>);

it("should display student name and cv name", () => {
  render(<CvModal cv={cv} show={true} handleClose={() => {}} />);

  const studentName = screen.getByText(/john doe/i);
  expect(studentName).toBeInTheDocument();

  const cvName = screen.getByText(/The best CV/i);
  expect(cvName).toBeInTheDocument();
});
