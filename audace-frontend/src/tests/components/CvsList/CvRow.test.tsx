import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import CvRow from "../../../components/CVsList/CvRow";
import "@testing-library/jest-dom/extend-expect";
import { UserType } from "../../../model/user";
import { Table } from "react-bootstrap";
import { cv } from "../testUtils/testUtils";

it("should display default values", () => {
  render(
    <Table>
      <tbody>
        <CvRow cv={cv} />
      </tbody>
    </Table>
  );
  const viewMore = screen.getByText(/cvsList.viewMore/i);
  expect(viewMore).toBeInTheDocument();
});

jest.mock("../../../components/CvsList/CvRow/CvButtons", () => () => (
  <div>Butt</div>
));

jest.mock("../../../components/PDFViewer", () => () => <div>PDFViewer</div>);

it("should display cvButtons when user is not student", () => {
  jest
    .spyOn(require("../../../services/authService"), "getUserType")
    .mockImplementation(() => UserType.Employer);

  render(
    <Table>
      <tbody>
        <CvRow cv={cv} />
      </tbody>
    </Table>
  );

  const viewMore = screen.getByText(/cvsList.viewMore/i);
  expect(viewMore).toBeInTheDocument();

  const cvButtons = screen.getByText(/butt/i);
  expect(cvButtons).toBeInTheDocument();
});

it("should not display cvButtons when user is student", () => {
  jest
    .spyOn(require("../../../services/authService"), "getUserType")
    .mockImplementation(() => UserType.Student);

  render(
    <Table>
      <tbody>
        <CvRow cv={cv} />
      </tbody>
    </Table>
  );

  const viewMore = screen.getByText(/cvsList.viewMore/i);
  expect(viewMore).toBeInTheDocument();

  const cvButtons = screen.queryByText(/butt/i);
  expect(cvButtons).not.toBeInTheDocument();
});

it("should open modal when viewMore is clicked", async () => {
  render(
    <Table>
      <tbody>
        <CvRow cv={cv} />
      </tbody>
    </Table>
  );

  const viewMore = screen.getByText(/cvsList.viewMore/i);
  expect(viewMore).toBeInTheDocument();

  fireEvent.click(viewMore);

  const studentName = screen.getAllByText(/john doe/i)[1];
  await waitFor(() => expect(studentName).toBeInTheDocument());

  const cvName = screen.getByText(/john doe - The best CV/i);
  expect(cvName).toBeInTheDocument();
});

it("should close modal when close button is clicked", async () => {
  render(
    <Table>
      <tbody>
        <CvRow cv={cv} />
      </tbody>
    </Table>
  );

  const viewMore = screen.getByText(/cvsList.viewMore/i);
  expect(viewMore).toBeInTheDocument();

  fireEvent.click(viewMore);

  const studentName = screen.getAllByText(/john doe/i)[1];
  await waitFor(() => expect(studentName).toBeInTheDocument());

  const closeButton = screen.getByLabelText(/close/i);
  expect(closeButton).toBeInTheDocument();

  fireEvent.click(closeButton);

  await waitFor(() => expect(studentName).not.toBeInTheDocument());
});
